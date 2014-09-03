package com.gc.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.concurrent.SessionRegistry;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.event.authentication.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.ui.AuthenticationDetailsSource;
import org.springframework.security.ui.WebAuthenticationDetailsSource;
import org.springframework.security.ui.rememberme.RememberMeServices;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.security.util.SessionUtils;
import org.springframework.security.userdetails.UserDetailsService;

import com.gc.common.dao.BaseDAOHibernate;
import com.gc.common.po.Branch;
import com.gc.common.po.SecurityLimit;
import com.gc.common.po.SecurityLimitPK;
import com.gc.common.po.SecurityUser;
import com.gc.exception.CommonRuntimeException;
import com.gc.util.CommonUtil;

import flex.messaging.FlexContext;

public class UserService implements UserDetailsService {

	private final static GrantedAuthority[] DEFAULT_GRANTED_AUTHORITIES = {new GrantedAuthorityImpl("ROLE_USER")};

	private BaseDAOHibernate baseDAO;

	private AuthenticationManager authenticationManager;

	protected AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();

	protected ApplicationEventPublisher eventPublisher;

  private RememberMeServices rememberMeServices = null;

  private boolean invalidateSessionOnSuccessfulAuthentication = false;

  private boolean migrateInvalidatedSessionAttributes = true;

  private boolean allowSessionCreation = true;
  
  private SessionRegistry sessionRegistry;

	public void setBaseDAO(BaseDAOHibernate baseDAO) {
		this.baseDAO = baseDAO;
	}

	protected AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public AuthenticationDetailsSource getAuthenticationDetailsSource() {
		// Required due to SEC-310
		return authenticationDetailsSource;
	}

	public void setAuthenticationDetailsSource(AuthenticationDetailsSource authenticationDetailsSource) {
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	public RememberMeServices getRememberMeServices() {
		return rememberMeServices;
	}

	public void setRememberMeServices(RememberMeServices rememberMeServices) {
		this.rememberMeServices = rememberMeServices;
	}

  public void setAllowSessionCreation(boolean allowSessionCreation) {
  	this.allowSessionCreation = allowSessionCreation;
  }

  public void setInvalidateSessionOnSuccessfulAuthentication(boolean invalidateSessionOnSuccessfulAuthentication) {
  	this.invalidateSessionOnSuccessfulAuthentication = invalidateSessionOnSuccessfulAuthentication;
  }

  public void setMigrateInvalidatedSessionAttributes(boolean migrateInvalidatedSessionAttributes) {
  	this.migrateInvalidatedSessionAttributes = migrateInvalidatedSessionAttributes;
  }

  public void setSessionRegistry(SessionRegistry sessionRegistry) {
  	this.sessionRegistry = sessionRegistry;
  }

	public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
//==================================== SecurityUser ====================================

	public List<SecurityUser> getSecurityUsers(SecurityUser su) {
		return baseDAO.getSecurityUsers(su);
	}

	public SecurityUser getSecurityUser(SecurityUser su) {
		return baseDAO.getSecurityUser(su);
	}

	public List<Branch> getLimitBranches(SecurityUser su) {
		return baseDAO.getLimitBranches(su);
	}

	protected void setSecurityUserLimit(SecurityUser su) {
		SecurityLimit limit;
		String branchUseId = su.getLimitBranchUseId();
		if (su.getLimits().size() < 1) {
			throw new CommonRuntimeException(CommonUtil.getString("login.error.no.branch", new Object[]{su.getUseId()}));
		}	else if (su.getLimits().size() == 1) {
			su.setLimit(su.getLimits().get(0));
		}	else {
			su.setLimit(su.getLimits().get(0));
			for (Iterator<SecurityLimit> it = su.getLimits().iterator(); it.hasNext(); ) {
				limit = it.next();
				if (limit.getBranchUseId().equals(branchUseId)) {
					su.setLimit(limit);
				}
			}
		}
	}

	protected void setRequestInfo(HttpServletRequest request, SecurityUser su) {
		su.setRemoteAddr(request.getRemoteAddr());
		su.setRemoteHost(request.getRemoteHost());
		su.setRemotePort(request.getRemotePort());
		su.setRemoteUser(request.getRemoteUser());
		su.setSystemTime(Calendar.getInstance());
	}

	public SecurityUser authenticate(SecurityUser su) throws IOException, ServletException {
    HttpServletRequest request = FlexContext.getHttpRequest();
    HttpServletResponse response = FlexContext.getHttpResponse();
    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(su.getUsername(), su.getPassword());
    Authentication authResult = null;
    SecurityUser suResult = null;
    try {
  		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
  		authResult = getAuthenticationManager().authenticate(authRequest);
      suResult = (SecurityUser) authResult.getPrincipal();
      suResult.setLimit(new SecurityLimit());
      suResult.getLimit().setId(new SecurityLimitPK());
      suResult.getLimit().getId().setBranch(new Branch());
      suResult.getLimit().getId().getBranch().setUseId(su.getLimitBranchUseId());
      setSecurityUserLimit(suResult);
      setRequestInfo(request, suResult);
      successfulAuthentication(request, response, authResult);
    } catch (AuthenticationException ae) {
    	String msg = ae.getLocalizedMessage();
    	if (ae instanceof UsernameNotFoundException) {
    		msg = CommonUtil.getString("login.error.no.user", new Object[]{su.getUsername()});
    	} else if (ae instanceof BadCredentialsException) {
				msg = CommonUtil.getString("login.error.incorrect.password", new Object[]{su.getUsername()});
    	}
    	unsuccessfulAuthentication(request, response, ae);
    	throw new CommonRuntimeException(msg, ae);
    }
    return suResult;
	}

	/**
	 * sendRedirect and request.getRequestDispatcher has no effects to flex client request
	 * @param request
	 * @param response
	 * @param authResult
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authResult);
		if (invalidateSessionOnSuccessfulAuthentication) {
			SessionUtils.startNewSessionIfRequired(request, migrateInvalidatedSessionAttributes, sessionRegistry);
		}
		if (rememberMeServices != null) rememberMeServices.loginSuccess(request, response, authResult);
		// Fire event
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
		}
	}

	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(null);
		try {
			HttpSession session = request.getSession(false);
			if (session != null || allowSessionCreation) {
				request.getSession().setAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY, failed);
			}
		} catch (Exception ignored) {
		}
		if (rememberMeServices != null) rememberMeServices.loginFail(request, response);
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SecurityUser su = new SecurityUser();
		su.setUseId(username);
		su = baseDAO.getSecurityUser(su);
		if (su == null) throw new UsernameNotFoundException("");
		su.setAuthorities(DEFAULT_GRANTED_AUTHORITIES);
		return su;
		// RequestContextHolder.
		// LocaleContextHolder.
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// return (auth.getPrincipal() instanceof SecurityUser) ? (SecurityUser) auth.getPrincipal() : null;
	}

}
