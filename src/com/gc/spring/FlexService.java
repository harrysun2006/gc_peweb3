package com.gc.spring;

import java.util.ArrayList;
import java.util.List;

import com.gc.util.FlexUtil;

import flex.messaging.io.amf.ASObject;

public class FlexService {

	public static final String BEAN_NAME = "flexService";

	public static Object exec(Object obj) throws Exception {
		ASObject ao = (ASObject) obj;
		String s = (String) ao.get("s");
		String m = (String) ao.get("m");
		List params = new ArrayList();
		byte[] b;
		Object[] ps;
		if (ao.containsKey("p")) {
			b = (byte[]) ao.get("p");
			ps = (Object[]) FlexUtil.readObject(b, true);
			for (Object p: ps) params.add(p);
		}
		Object r = FlexUtil.invoke(s, m, params);
		b = FlexUtil.writeObject(r, true);
		ao = new ASObject();
		ao.put("r", b);
		return ao;
	}
}
