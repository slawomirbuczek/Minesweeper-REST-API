package minesweeper_ranking.config;

import minesweeper_ranking.authentication.JwtAuthenticationFilter;
import minesweeper_ranking.authentication.JwtAuthorizationFilter;
import minesweeper_ranking.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@ConfigurationPropertiesScan("minesweeper_ranking.config")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final String login_endpoint;
    private final String registration_endpoint;
    private final String header_name;
    private final String token_prefix;
    private final String secret;
    private final long expiration_time;

    public WebSecurityConfig(
            UserDetailsServiceImpl userDetailsService,
            @Value("${api.login-endpoint}") String login_endpoint,
            @Value("${api.registration-endpoint}") String registration_endpoint,
            @Value("${api.jwt.header-name}") String header_name,
            @Value("${api.jwt.token-prefix}") String token_prefix,
            @Value("${api.jwt.secret}") String secret,
            @Value("${api.jwt.expiration-time}") long expiration_time) {
        this.userDetailsService = userDetailsService;
        this.login_endpoint = login_endpoint;
        this.registration_endpoint = registration_endpoint;
        this.header_name = header_name;
        this.token_prefix = token_prefix;
        this.secret = secret;
        this.expiration_time = expiration_time;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, registration_endpoint).permitAll()
                .antMatchers(HttpMethod.POST, login_endpoint).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), login_endpoint, secret, expiration_time, token_prefix))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, header_name, token_prefix, secret))
                .headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
