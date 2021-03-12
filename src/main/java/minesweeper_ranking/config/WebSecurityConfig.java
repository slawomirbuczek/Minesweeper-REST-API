package minesweeper_ranking.config;

import minesweeper_ranking.authentication.JWTAuthenticationEntryPoint;
import minesweeper_ranking.authentication.JwtAuthenticationFilter;
import minesweeper_ranking.authentication.JwtAuthorizationFilter;
import minesweeper_ranking.authentication.UserDetailsServiceImpl;
import org.modelmapper.ModelMapper;
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
    private final String secret;
    private final long expiration_time;

    public WebSecurityConfig(
            UserDetailsServiceImpl userDetailsService,
            @Value("${api.jwt.secret}") String secret,
            @Value("${api.jwt.expiration-time}") long expiration_time) {
        this.userDetailsService = userDetailsService;
        this.secret = secret;
        this.expiration_time = expiration_time;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/register").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .anyRequest().authenticated();

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint());

        http
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), secret, expiration_time))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, secret));

        http
                .headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

}
