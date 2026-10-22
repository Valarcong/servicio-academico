package pe.edu.cibertec.servicioacademico.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String claveSecreta;

    public String obtenerNombreUsuario(String token) {
        return obtenerClaim(
                token,
                Claims::getSubject
        );
    }

    public Integer obtenerIdUsuario(String token) {

        Number idUsuario = obtenerTodosLosClaims(token)
                .get("idUsuario", Number.class);

        return idUsuario.intValue();
    }

    @SuppressWarnings("unchecked")
    public List<String> obtenerRoles(String token) {

        return obtenerTodosLosClaims(token)
                .get("roles", List.class);
    }

    public boolean validarToken(String token) {
        return !tokenExpirado(token);
    }

    private boolean tokenExpirado(String token) {
        return obtenerExpiracion(token)
                .before(new Date());
    }

    private Date obtenerExpiracion(String token) {
        return obtenerClaim(
                token,
                Claims::getExpiration
        );
    }

    private <T> T obtenerClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {

        Claims claims = obtenerTodosLosClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims obtenerTodosLosClaims(String token) {

        return Jwts.parser()
                .verifyWith(obtenerClave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey obtenerClave() {

        byte[] keyBytes =
                Decoders.BASE64.decode(claveSecreta);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}