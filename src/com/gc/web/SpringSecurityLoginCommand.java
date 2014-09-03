package com.gc.web;

import java.security.Principal;
import java.util.List;

import javax.servlet.ServletConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import flex.messaging.FlexContext;
import flex.messaging.security.LoginCommand;
import flex.messaging.security.SecurityException;

/**
* Custom implementation of BlazeDS LoginCommand which utilizes
* Spring Security 2.0 framework underneath.
* <p/>
*
* Date: Oct 14, 2008
* Time: 12:33:46 PM
*/
public class SpringSecurityLoginCommand implements LoginCommand {
  protected final Log log = LogFactory.getLog(getClass());

  public static final String AUTHENTICATION_PROVIDER_BEAN_NAME = "authenticationProvider";
  public static final String SPRING_SECURITY_CONTEXT_KEY = "SPRING_SECURITY_CONTEXT";

  private AuthenticationProvider provider = null;
  private final boolean isPerClientAuthentication;

  public SpringSecurityLoginCommand() {
    isPerClientAuthentication = FlexContext.isPerClientAuthentication();
  }

  public Principal doAuthentication(String username, Object credentials) {
    SecurityContext securityContext;

    if (isPerClientAuthentication) {
      securityContext = (SecurityContext) FlexContext.getFlexClient()
          .getAttribute(SPRING_SECURITY_CONTEXT_KEY);

    } else {
      securityContext = (SecurityContext) FlexContext.getFlexSession()
          .getAttribute(SPRING_SECURITY_CONTEXT_KEY);
    }

    Authentication principal;

    if (securityContext == null) {
      UsernamePasswordAuthenticationToken token =
          new UsernamePasswordAuthenticationToken(username, credentials);

      try {
      	if (provider == null) {
      		WebApplicationContext wac = WebApplicationContextUtils
      		.getWebApplicationContext(FlexContext.getServletContext());

      		provider = (AuthenticationProvider) wac.getBean(AUTHENTICATION_PROVIDER_BEAN_NAME);
      		log.info("Spring Security Authentication Provider initialized - " + provider);
      	}
        principal = provider.authenticate(token);

        securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(principal);

        if (isPerClientAuthentication) {
          FlexContext.getFlexClient().setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
        } else {
          FlexContext.getFlexSession().setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
        }

        if (log.isDebugEnabled()) {
          log.debug("[Login] Security Context was created.");
        }

      } catch (AuthenticationException e) {

        SecurityException se = new SecurityException();
        se.setMessage(SecurityException.SERVER_AUTHENTICATION_CODE);
        se.setRootCause(e);
        throw se;
      }

    } else {

      principal = securityContext.getAuthentication();

      log.info("[" + username + "] was already authenticated previously.");
    }

    SecurityContextHolder.setContext(securityContext);

    return principal;
  }

  public boolean logout(Principal principal) {
    if (isPerClientAuthentication) {
      FlexContext.getFlexClient().removeAttribute(SPRING_SECURITY_CONTEXT_KEY);
    } else {
      FlexContext.getFlexSession().removeAttribute(SPRING_SECURITY_CONTEXT_KEY);
    }

    SecurityContextHolder.getContext().setAuthentication(null);

    if (log.isDebugEnabled()) {
      log.debug("[Logout] Security Context was removed.");
    }

    return true;
  }

  public boolean doAuthorization(Principal principal, List list) {
    // always return TRUE as the authorization is delegated to Spring Security.
    return true;
  }

  public void start(ServletConfig config) {
    // noop
  }

  public void stop() {
    // noop
  }
} 