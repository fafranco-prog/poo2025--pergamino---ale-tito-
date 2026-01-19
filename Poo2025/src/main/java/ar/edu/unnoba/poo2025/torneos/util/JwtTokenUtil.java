package ar.edu.unnoba.poo2025.torneos.util;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import ar.edu.unnoba.poo2025.torneos.exception.JwtAuthenticationException;
import ar.edu.unnoba.poo2025.torneos.model.ParticipanteModel;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    // 10 dias * 24 horas * 60 minutos * 60 segundos * 1000 milisegundos
    private static final long TIEMPO_DE_EXPIRACION = 10 * 24 * 60 * 60 * 1000;
    private static final String PREFIJO = "Bearer ";

    public String generarToken(String subject) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("El 'subject' para el token JWT no puede ser nulo o vacío.");
        }
        Algorithm algorithm = Algorithm.HMAC512(secret);
        String token = JWT.create()
                .withSubject(subject)
                .withClaim("role", "ADMIN")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TIEMPO_DE_EXPIRACION))
                .sign(algorithm);
        return PREFIJO + token;
    }

    public String generarToken(String subject, ParticipanteModel participant) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("El 'subject' para el token JWT no puede ser nulo o vacío.");
        }
        Algorithm algorithm = Algorithm.HMAC512(secret);
        String token = JWT.create()
                .withSubject(subject)
                .withClaim("role", "PARTICIPANT")
                .withClaim("user", Map.of(
                        "nombre", participant.getNombre(),
                        "apellido", participant.getApellido()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TIEMPO_DE_EXPIRACION))
                .sign(algorithm);
        return PREFIJO + token;
    }

    private String limpiarToken(String token) {
        if (token != null && token.startsWith(PREFIJO)) {
            return token.substring(PREFIJO.length());
        }
        throw new JwtAuthenticationException("Token ausente o con formato incorrecto.");
    }

    public void validarToken(String token) {
        String jwtToken = limpiarToken(token);
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(jwtToken);
        } catch (JWTVerificationException exception) {
            throw new JwtAuthenticationException("Token inválido o expirado");
        }
    }

    public String getSubject(String token) {
        validarToken(token);
        try {
            String jwtToken = token.substring(PREFIJO.length());
            DecodedJWT decodedJWT = JWT.decode(jwtToken);
            return decodedJWT.getSubject();
        } catch (JWTDecodeException e) {
            throw new JwtAuthenticationException("No se pudo decodificar el token.");
        }
    }
}
