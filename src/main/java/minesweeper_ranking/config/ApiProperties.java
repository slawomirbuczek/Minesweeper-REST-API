package minesweeper_ranking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("minesweeper.api")
public class ApiProperties {

    /**
     * JWT secret key.
     */
    private String secret;

    /**
     * JWT expiration time.
     */
    private long expiration_time;

    /**
     * JWT token prefix.
     */
    private String token_prefix;

    /**
     * Authorization header name.
     */
    private String header_name;

    /**
     * Login endpoint.
     */
    private String login_endpoint;

    /**
     * Registration endpoint.
     */
    private String registration_endpoint;

}
