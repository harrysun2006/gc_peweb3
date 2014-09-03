package com.gc.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.exception.ExceptionUtils;

public class RestartTransactionInterceptor implements MethodInterceptor {
	
	// private static Logger log = Logger.getLogger(RestartTransactionAdviser.class);

	public Object invoke(MethodInvocation invocation) throws Throwable {
		return restart(invocation, 1);
	}

	private Object restart(MethodInvocation invocation, int attempt)
			throws Throwable {
		Object rval = null;
		try {
			rval = invocation.proceed();
		} catch (Exception e) {
			Throwable thr = ExceptionUtils.getRootCause(e);
			if (thr == null) {
				throw e;
			}

			if (StringUtils.contains(thr.getMessage(), "deadlock")
					|| StringUtils.contains(thr.getMessage(),
							"try restarting transaction")
					|| StringUtils.contains(thr.getMessage(),
							"failed to resume the transaction")) {
				if (attempt > 10) {
					throw e;
				}
				int timeout = RandomUtils.nextInt(2000);
				/*
				log.warn("Transaction rolled back. Restarting transaction.");
				log.debug("Spleep for " + timeout);
				log.debug("Restarting transaction: invocation=[" + invocation
						+ "], attempt=[" + attempt + "]");
				*/
				Thread.sleep(timeout);
				attempt++;
				return restart(invocation, attempt);
			} else {
				throw e;
			}
		}
		return rval;
	}
}
