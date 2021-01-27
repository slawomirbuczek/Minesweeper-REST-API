package minesweeper_ranking.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import minesweeper_ranking.model.LoginCredentials;
import minesweeper_ranking.model.entity.Player;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final String secret;
    private final long expiration_time;
    private final String token_prefix;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String login_endpoint, String secret, long expiration_time, String token_prefix) {
        this.authenticationManager = authenticationManager;
        this.secret = secret;
        this.expiration_time = expiration_time;
        this.token_prefix = token_prefix;

        setFilterProcessesUrl(login_endpoint);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            LoginCredentials creds = new ObjectMapper()
                    .readValue(req.getInputStream(), LoginCredentials.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        String token = JWT.create()
                .withSubject(((Player) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration_time))
                .sign(Algorithm.HMAC512(secret.getBytes()));

        res.addHeader("Authorization", token_prefix + token);
        res.setContentType("application/json");
        res.getWriter().print("{\"message\": \"Logged in successfully\"}");
        res.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.getWriter().print("{\"message\": \"" + failed.getMessage() + "\"}");
        response.getWriter().flush();
    }
}
