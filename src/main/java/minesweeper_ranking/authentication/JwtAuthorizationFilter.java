package minesweeper_ranking.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsService userDetailsService;
    private final String header_name;
    private final String token_prefix;
    private final String secret;

    public JwtAuthorizationFilter(
            AuthenticationManager authManager,
            UserDetailsService userDetailsService,
            String header_name,
            String token_prefix,
            String secret) {
        super(authManager);
        this.userDetailsService = userDetailsService;
        this.header_name = header_name;
        this.token_prefix = token_prefix;
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        String header = req.getHeader(header_name);

        if (header == null || !header.startsWith(token_prefix)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(header_name);

        if (token != null) {
            String username = JWT.require(Algorithm.HMAC512(secret.getBytes()))
                    .build()
                    .verify(token.replace(token_prefix, ""))
                    .getSubject();

            if (username != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
