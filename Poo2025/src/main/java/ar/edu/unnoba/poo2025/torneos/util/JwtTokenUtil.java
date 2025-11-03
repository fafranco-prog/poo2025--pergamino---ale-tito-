package ar.edu.unnoba.poo2025.torneos.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    // 10 dias * 24 horas * 60 minutos * 60 segundos * 1000 milisegundos
    private static final long TIEMPO_DE_EXPIRACION = 10 * 24 * 60 * 60 * 1000;
    private static final String PREFIJO = "Bearer";

    public String generarToken(String subject) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        String token = JWT.create()
                .withSubject(subject)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TIEMPO_DE_EXPIRACION))
                .sign(algorithm);
        return PREFIJO + token;
    }

    public boolean validarToken(String token) {
        if (token == null || !token.startsWith(PREFIJO)) {
            return false;
        }
        try {
            String jwtToken = token.substring(PREFIJO.length());
            Algorithm algorithm = Algorithm.HMAC512(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(jwtToken);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    public String getSubject(String token) {
        if (token == null || !token.startsWith(PREFIJO)) {
            return null;
        }
        String jwtToken = token.substring(PREFIJO.length());
        DecodedJWT decodedJWT = JWT.decode(jwtToken);
        return decodedJWT.getSubject();
    }
}
