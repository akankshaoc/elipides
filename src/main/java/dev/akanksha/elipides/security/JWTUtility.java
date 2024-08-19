package dev.akanksha.elipides.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.akanksha.elipides.exceptions.AuthenticationException;
import dev.akanksha.elipides.models.User;
import dev.akanksha.elipides.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class JWTUtility {

    private final UserRepository userRepository;

    @Value("${auth.jwt.security-key}")
    private String SECRET_KEY;

    public String issueToken(String username) {
        User user = userRepository.findById(username).orElseThrow(NoSuchElementException::new);

        return JWT.create()
                .withSubject(username)
                .withExpiresAt(Instant.now().plus(Duration.of(3, ChronoUnit.HOURS)))
                .withIssuer("elipides")
                .withClaim("r", user.getRole().toString())
                .withClaim("e", user.getEmail())
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }


    public UserPrincipal getPrincipalFromToken(String token) throws AuthenticationException {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("elipides")
                    .build();

            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException exception){
            throw new AuthenticationException("unable to verify token");
        }

        User user = userRepository
                .findByEmail(decodedJWT.getClaim("e").asString())
                .orElseThrow(() -> new AuthenticationException("unable to verify token"));

        return UserPrincipal
                .builder()
                .username(decodedJWT.getSubject())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole().toString())))
                .build();
    }
}
