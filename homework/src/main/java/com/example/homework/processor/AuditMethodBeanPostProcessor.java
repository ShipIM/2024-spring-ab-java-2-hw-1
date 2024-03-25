package com.example.homework.processor;

import com.example.homework.annotation.Audit;
import com.example.homework.model.entity.mongo.Operation;
import com.example.homework.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuditMethodBeanPostProcessor implements BeanPostProcessor {

    private final OperationService operationService;
    private final Map<String, Class<?>> originalClasses = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, @NotNull String beanName) throws BeansException {
        Class<?> originalClass = bean.getClass();

        for (Method method : originalClass.getMethods()) {
            if (method.isAnnotationPresent(Audit.class)) {
                originalClasses.put(beanName, originalClass);

                break;
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean,
                                                 @NotNull String beanName) throws BeansException {
        Class<?> originalBeanClass = originalClasses.get(beanName);

        if (Objects.isNull(originalBeanClass)) {
            return bean;
        }

        return Proxy.newProxyInstance(originalBeanClass.getClassLoader(),
                ClassUtils.getAllInterfacesForClass(originalBeanClass), (proxy, method, args) -> {
                    try {
                        Method originalMethod = originalBeanClass
                                .getMethod(method.getName(), method.getParameterTypes());
                        if (originalMethod.isAnnotationPresent(Audit.class)) {
                            Audit annotation = originalMethod.getAnnotation(Audit.class);

                            Object result = method.invoke(bean, args);

                            operationService.logOperation(Operation.builder()
                                    .message(annotation.message())
                                    .time(LocalDateTime.now())
                                    .type(annotation.type())
                                    .build());

                            return result;
                        }

                        return method.invoke(bean, args);
                    } catch (Exception e) {
                        throw e.getCause();
                    }
                });
    }

}
