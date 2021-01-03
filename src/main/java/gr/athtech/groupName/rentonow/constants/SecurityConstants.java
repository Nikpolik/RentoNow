package gr.athtech.groupName.rentonow.constants;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {

  private SecurityConstants() {}

  @Value("${security.secret}")
  public static final String SECRET = "SECRET_KEY";
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/auth/register";  
  public static final String AUTH_URL = "/auth/login";
  public static final String ISSUER = "rentonow";
  public static final int TOKEN_DURATION = 1000 * 60 * 60 * 24; // mili sec min hour
}
