package com.example.homework.repository.jpa;

import com.example.homework.model.entity.jpa.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByReference(String reference);

    List<Image> findAllByReferenceIn(Collection<String> reference);

}
