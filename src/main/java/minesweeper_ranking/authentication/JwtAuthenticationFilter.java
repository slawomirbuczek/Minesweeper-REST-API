package minesweeper_ranking.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import minesweeper_ranking.model.Player;
import minesweeper_ranking.model.ResponseMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final String secret;
    private final long expiration_time;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   String secret,
                                   long expiration_time) {
        this.authenticationManager = authenticationManager;
        this.secret = secret;
        this.expiration_time = expiration_time;

        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            Player details = new ObjectMapper().readValue(req.getInputStream(), Player.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            details.getUsername(),
                            details.getPassword(),
                            details.getAuthorities())
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException {
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + createJwt(auth));
        response.setContentType("application/json");
        response.getOutputStream().print(new ResponseMessage("Logged in successfully").toString());
    }

    private String createJwt(Authentication auth) {
        return JWT.create()
                .withSubject(((Player) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration_time))
                .sign(Algorithm.HMAC512(secret.getBytes(StandardCharsets.UTF_8)));
    }

}
