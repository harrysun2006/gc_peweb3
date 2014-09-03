package com.gc.spring;

import org.springframework.flex.core.ExceptionTranslator;

import com.gc.exception.CommonRuntimeException;
import com.gc.util.CommonUtil;

import flex.messaging.MessageException;
import flex.messaging.security.SecurityException;

public class FlexExceptionTranslator implements ExceptionTranslator {

	// private static final Log logger = LogFactory.getLog(FlexExceptionTranslator.class);

	public boolean handles(Class<?> clazz) {
		return true;
	}

	public MessageException translate(Throwable t) {
		// logger.error("Exception translated to flex: ", t);
		if (t instanceof CommonRuntimeException) {
			SecurityException se = new SecurityException();
			se.setCode(SecurityException.CLIENT_AUTHENTICATION_CODE);
			se.setMessage(t.getLocalizedMessage());
			se.setRootCause(t);
			se.setDetails(t.getClass().getName());
			return se;
		} else {
			MessageException me = new MessageException(t.getMessage());
			me.setRootCause(CommonUtil.getRootCause(t));
			return me;
		}
	}

}
