package com.example.homework.utils.jwt;

import com.example.homework.model.entity.jpa.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@NoArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret.access.key}")
    private String access;
    @Value("${jwt.secret.refresh.key}")
    private String refresh;
    @Value("${jwt.secret.access.expiration}")
    private Long accessExpiration;
    @Value("${jwt.secret.refresh.expiration}")
    private Long refreshExpiration;

    public String generateAccessToken(Map<String, Object> extraClaims, User user) {
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusSeconds(accessExpiration)
                .atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpirationDate = Date.from(accessExpirationInstant);

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setExpiration(accessExpirationDate)
                .signWith(getSignInKey(access), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        Instant refreshExpirationInstant = now.plusSeconds(refreshExpiration)
                .atZone(ZoneId.systemDefault()).toInstant();
        Date refreshExpirationDate = Date.from(refreshExpirationInstant);

        return Jwts
                .builder()
                .setSubject(user.getUsername())
                .setExpiration(refreshExpirationDate)
                .signWith(getSignInKey(refresh), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key getSignInKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(access);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractAccessUsername(String token) {
        return extractUsername(token, access);
    }

    public String extractRefreshUsername(String token) {
        return extractUsername(token, refresh);
    }

    private String extractUsername(String token, String key) {
        return extractClaim(token, key, Claims::getSubject);
    }

    public <T> T extractClaim(String token, String key, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, key);

        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token, String key) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isAccessTokenValid(String token) {
        return isTokenValid(token, access);
    }

    public boolean isRefreshTokenValid(String token) {
        return isTokenValid(token, refresh);
    }

    private boolean isTokenValid(String token, String key) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey(key))
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (Exception e) {
            log.error("Invalid token", e);
        }

        return false;
    }

}
