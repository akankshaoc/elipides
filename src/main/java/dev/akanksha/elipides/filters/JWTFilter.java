package dev.akanksha.elipides.filters;

import dev.akanksha.elipides.exceptions.AuthenticationException;
import dev.akanksha.elipides.security.JWTUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor @Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtility utility;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token != null
                && token.startsWith("Bearer")
                && SecurityContextHolder.getContext().getAuthentication() == null
        ) {
            try {
                var userPrincipal = utility.getPrincipalFromToken(token.substring(7));
                var auth = UsernamePasswordAuthenticationToken.authenticated(userPrincipal, userPrincipal.getPassword(), userPrincipal.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (AuthenticationException e) {
                //todo: have a logger service in place to handle this
            }
        }
        //forward to the next filter
        doFilter(request, response, filterChain);
    }
}
