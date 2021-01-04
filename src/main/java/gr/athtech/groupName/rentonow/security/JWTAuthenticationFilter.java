package gr.athtech.groupName.rentonow.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import gr.athtech.groupName.rentonow.constants.SecurityConstants;
import gr.athtech.groupName.rentonow.models.User;

// this code is inspired by https://dev.to/kubadlo/spring-security-with-jwt-3j76
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authManager;

  public JWTAuthenticationFilter(AuthenticationManager manager) {
    this.authManager = manager;
    setFilterProcessesUrl(SecurityConstants.AUTH_URL);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse res) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    var token = new UsernamePasswordAuthenticationToken(username, password);
    return authManager.authenticate(token);
  }

  @Override
  public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain, Authentication authentication) throws IOException, ServletException {
      User user = ((User) authentication.getPrincipal());
      List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
          .collect(Collectors.toList());
      String key = SecurityConstants.SECRET;
      Algorithm algorithm = Algorithm.HMAC512(key);
      Date expiresAt = new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_DURATION);
      String token = JWT.create().withSubject(user.getUsername()).withIssuer(SecurityConstants.ISSUER)
          .withClaim("rol", roles).withExpiresAt(expiresAt).sign(algorithm);
      response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
      filterChain.doFilter(request, response);
  }

}
