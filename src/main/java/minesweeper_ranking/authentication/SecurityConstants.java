package minesweeper_ranking.authentication;

public class SecurityConstants {

    public static final String SECRET = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 900_000; // 15 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String LOGIN_ENDPOINT = "/api/login";
    public static final String REGISTRATION_ENDPOINT = "/api/register";
}
