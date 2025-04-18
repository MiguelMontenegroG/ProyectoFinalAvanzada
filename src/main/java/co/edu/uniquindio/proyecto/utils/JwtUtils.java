package co.edu.uniquindio.proyecto.utils;

import co.edu.uniquindio.proyecto.model.Usuario;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expirationMs;

    public String generarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getId())
                .addClaims(Map.of(
                        "rol", usuario.getRol(),
                        "nombre", usuario.getNombre()
                ))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String obtenerExpiracion(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.toString(); // Ejemplo: "Fri Apr 18 20:00:00 GMT 2025"
    }
}
