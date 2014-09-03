package com.gc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.gc.exception.CommonRuntimeException;

import flex.messaging.MessageException;
import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Input;
import flex.messaging.io.amf.Amf3Output;
import flex.messaging.security.SecurityException;
import flex.messaging.util.MethodMatcher;
import flex.messaging.util.MethodMatcher.Match;

public class FlexUtil {

	private static final int BUFFER_SIZE = 1024000;

	private FlexUtil() {}

	public static Object readObject(byte[] b) throws IOException, ClassNotFoundException, DataFormatException {
		return readObject(b, false);
	}

	public static Object readObject(byte[] b, boolean compressed) throws IOException, ClassNotFoundException, DataFormatException {
	 	SerializationContext context = SerializationContext.getSerializationContext();
	 	Amf3Input ai = new Amf3Input(context);
	 	byte[] data = compressed ? decompress(b) : b;
	 	ai.setInputStream(new ByteArrayInputStream(data));
		return ai.readObject();
	}

	public static byte[] writeObject(Object obj) throws IOException {
		return writeObject(obj, false);
	}

	public static byte[] writeObject(Object obj, boolean compressed) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		SerializationContext context = SerializationContext.getSerializationContext();
		Amf3Output ao = new Amf3Output(context);
		ao.setOutputStream(baos);
		ao.writeObject(obj);
		byte[] b = baos.toByteArray();
		return compressed ? compress(b) : b;
	}

	public static Object invoke(String s, String m, List params) throws IllegalAccessException, IllegalArgumentException,	InvocationTargetException {
		/**
		 * 从destination-id取service实例比较困难, 暂时直接根据hrPersonalService==>hrPersonalServiceUtil
		 * RemotingDestinationExporter rde = (RemotingDestinationExporter) SpringUtil.getBean("org.springframework.flex.remoting.RemotingDestinationExporter#0");
		 * flex.messaging.MessageBroker broker = (flex.messaging.MessageBroker) SpringUtil.getBean("_messageBroker");
		 * RemotingService rs = (RemotingService) broker.getServiceByType(RemotingService.class.getName());
		 * Destination d = rs.getDestination(s);
		 * System.out.println(d);
		 **/
		Object service;
		Method method;
		service = SpringUtil.getBean(s + "Util");
		method = findMethod(service, m, params);
		if (method == null) {
			service = SpringUtil.getBean(s);
			method = findMethod(service, m, params);
		}
		FlexUtil.convertParams(params, method);
		return method.invoke(service, params.toArray());
	}

	public static Method findMethod(Object obj, String name, List params) {
		return obj == null ? null : findMethod(obj.getClass(), name, params);
	}

	public static Method findMethod(Class clazz, String name, List params) {
		MethodMatcher mm = new MethodMatcher();
		return mm.getMethod(clazz, name, params);
	}

	public static void convertParams(List params, Method method) {
		MethodMatcher.convertParams(params, method.getParameterTypes(), new Match(method.getName()));
	}

	public static MessageException translate(Throwable t) {
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

	public static byte[] compress(byte input[]) {
		byte output[] = new byte[0];
		byte[] buf = new byte[BUFFER_SIZE];
		Deflater compresser = new Deflater(); 
		compresser.reset();
		compresser.setInput(input);
		compresser.finish();
		ByteArrayOutputStream o = new ByteArrayOutputStream(input.length);
		int got;
		try {
			while (!compresser.finished()) {
				got = compresser.deflate(buf);
				o.write(buf, 0, got);
			}
			output = o.toByteArray();
		} finally {
			try {o.close();} catch(IOException e1) {}
		}
		return output;
	}

	public static byte[] decompress(byte input[]) throws DataFormatException {
		byte output[] = new byte[0];
		byte[] buf = new byte[BUFFER_SIZE];
		Inflater decompresser = new Inflater(); 
		decompresser.reset();
		decompresser.setInput(input);
		ByteArrayOutputStream o = new ByteArrayOutputStream(input.length);
		int got;
		try {
			while (!decompresser.finished()) {
				got = decompresser.inflate(buf);
				o.write(buf, 0, got);
			}
			output = o.toByteArray();
		} catch (DataFormatException e1) {
			throw e1;
		} finally {
			try {o.close();} catch(IOException e2) {}
		}
		return output;
	}

}
