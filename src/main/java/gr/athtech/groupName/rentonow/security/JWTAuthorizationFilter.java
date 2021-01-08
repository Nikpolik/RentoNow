package gr.athtech.groupName.rentonow.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import gr.athtech.groupName.rentonow.constants.SecurityConstants;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  private final UserDetailsService userDetailsService;
  private final String secret;

  public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, String secret) {
    super(authenticationManager);
    this.secret = secret;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    Authentication authentication = getAuth(request);
    if (authentication == null) {
      chain.doFilter(request, response);
      return;
    }
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuth(HttpServletRequest request) {
    String authHeader = request.getHeader(SecurityConstants.HEADER_STRING);
    if (authHeader == null || authHeader.equals("") || !authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      return null;
    }
    try {
      String token = authHeader.split(" ")[1];
      Algorithm algorithm = Algorithm.HMAC512(secret);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);

      String username = jwt.getSubject();

      List<GrantedAuthority> authorities = jwt.getClaim("rol").asList(String.class).stream()
          .map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

      if (username == null || username.equals("")) {
        return null;
      }

      UserDetails user = userDetailsService.loadUserByUsername(username);

      return new UsernamePasswordAuthenticationToken(user, null, authorities);
    } catch (Exception e) {
      return null;
    }
  }

}
