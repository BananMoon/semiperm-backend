package com.project.semipermbackend.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Component
public class JwtTokenProvider {

    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


    private final SecretKey secretKey;

    private final long accessTokenValidityInMillis;
    private final long refreshTokenValidityInMillis;
    private final JwtParser jwtParser;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretBaseKey,
            @Value("${jwt.access-token-validity-sec}") long accessTokenValidityInSec,
            @Value("${jwt.refresh-token-validity-sec}") long refreshTokenValidityInSec) {
        this.secretKey = createKey(secretBaseKey);
        this.accessTokenValidityInMillis = accessTokenValidityInSec * 60000;
        this.refreshTokenValidityInMillis = refreshTokenValidityInSec * 60000;
        this.jwtParser = Jwts.parserBuilder().setSigningKey(this.secretKey).build();
    }

    private SecretKey createKey(String secretBaseKey) {
        byte[] baseKeyBytes = DatatypeConverter.parseBase64Binary(secretBaseKey);
        SecretKeySpec signingKey = new SecretKeySpec(baseKeyBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    public String createAccessToken(String email) {
        return createToken(email, ACCESS_TOKEN);
    }

    public String createRefreshToken(String email) {
        return createToken(email, REFRESH_TOKEN);
    }

    private String createToken(String email, String tokenType) {
        long expiredTimeInMillis = ACCESS_TOKEN.equals(tokenType) ? accessTokenValidityInMillis
                : refreshTokenValidityInMillis;
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", signatureAlgorithm.getValue());

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + expiredTimeInMillis);

        String jwt = Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .setExpiration(expireTime)
                .signWith(secretKey, signatureAlgorithm)
                .compact();
        return jwt;
    }

    // parseClaimsJwt() : 서명되지 않은 일반 텍스트 JWT 인스턴스를 반환
    // parseClaimsJws() : 결과 Claims JWS 인스턴스를 반환
    public boolean validateToken(String jwt) {
        try{
            Claims claims = jwtParser.parseClaimsJws(jwt)
                    .getBody();
            log.info("email from Jwt : {}", claims.get("email"));
            return true;
        } catch (ExpiredJwtException e) {
            log.info("JWT token is expired.");
            log.trace("ExpiredJwtException Trace : ", e);
        } catch (UnsupportedJwtException e) {
            log.info("JWT token is Unsupported.");
            log.trace("UnsupportedJwtException Trace : ", e);
        } catch (JwtException e) {
            log.info("Jwt Exception.");
            log.trace("JwtException Trace : ", e);
        }
        return false;
    }

    public String getSubject(String jwt) {
        return jwtParser.parseClaimsJws(jwt)
                .getBody().getSubject();
    }

    // TODO 구현 해야함.
    public Authentication getAuthentication(String jwt) {
        String subject = getSubject(jwt);
        return null;
    }
}
