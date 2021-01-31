package minesweeper_ranking.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsService userDetailsService;
    private final String secret;

    public JwtAuthorizationFilter(
            AuthenticationManager authManager,
            UserDetailsService userDetailsService,
            String secret) {
        super(authManager);
        this.userDetailsService = userDetailsService;
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Objects.nonNull(bearerToken) && isValid(bearerToken)) {
            String username = getUsernameFromJWT(bearerToken);
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new IOException();
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isValid(String bearerToken) {
        String jwt = bearerToken.replace("Bearer ", "");

        try {
            JWT.require(Algorithm.HMAC512(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .verify(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getUsernameFromJWT(String bearerToken) {
        String jwt = bearerToken.replace("Bearer ", "");

        return JWT.require(Algorithm.HMAC512(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .verify(jwt)
                .getSubject();
    }

}
