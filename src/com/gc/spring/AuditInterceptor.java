package com.gc.spring;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

public class AuditInterceptor extends EmptyInterceptor {

	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		// do nothing
	}

	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState, String[] propertyNames,
			Type[] types) {
		return false;
	}

	public boolean onLoad(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		// System.out.println(entity.getClass().getName() + "(" + id + ") load!");
		return false;
	}

	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		System.out.println(entity.getClass().getName() + "(" + id + ") saved!");
		return false;
	}

	public void afterTransactionCompletion(Transaction tx) {
	}

}
