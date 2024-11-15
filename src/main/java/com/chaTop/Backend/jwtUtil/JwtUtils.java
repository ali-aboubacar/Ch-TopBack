package com.chaTop.Backend.jwtUtil;

import com.chaTop.Backend.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${chaTop.app.jwtSecret}")
    private String jwtSecret;

    @Value("${chaTop.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        try{
            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

            logger.info("generating token", Jwts.builder()
                    .setSubject((userPrincipal.getEmail()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(key(), SignatureAlgorithm.HS256)
                    .compact());

            return Jwts.builder()
                    .setSubject((userPrincipal.getEmail()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(key(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (JwtException | IllegalArgumentException e){
            logger.error("Error generating token", e.getMessage());
            throw new RuntimeException("Jwt failed ", e);
        }
//        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
////
//        return Jwts.builder()
//                .setSubject((userPrincipal.getEmail()))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//                .signWith(key(), SignatureAlgorithm.HS256)
//                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            logger.info("token jwt",Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken));
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
