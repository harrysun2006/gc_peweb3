package com.gc;

import java.lang.reflect.Field;
import java.util.Comparator;

import com.gc.common.service.BaseServiceUtil;

class IdentityComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		if ((o1 == null && o2 == null) || o1 == o2) return 0;
		if (o1 == null && o2 != null) return -1;
		if (o1 != null && o2 == null) return 1;
		if (o1.getClass() != o2.getClass()) return o1.hashCode() - o2.hashCode();
		if (o1.getClass().isArray() && o2.getClass().isArray()) {
			Object[] o1s = (Object[]) o1;
			Object[] o2s = (Object[]) o2;
			int r;
			for (int i = 0; i < o1s.length && i < o2s.length; i++) {
				r = compare(o1s[i], o2s[i]);
				if (r != 0) return r;
			}
			return (o1s.length < o2s.length) ? -1 : (o1s.length > o2s.length) ? 1 : 0;
		}
		if (Comparable.class.isAssignableFrom(o1.getClass()) && Comparable.class.isAssignableFrom(o2.getClass())) {
			return ((Comparable) o1).compareTo((Comparable) o2);
		} else if (Number.class.isAssignableFrom(o1.getClass()) && Number.class.isAssignableFrom(o2.getClass())) {
			double d = ((Number) o1).doubleValue() - ((Number) o2).doubleValue();
			return (d < 0) ? -1 : (d > 0) ? 1 : 0;
		} else {
			String id = BaseServiceUtil.getIdentifierName(o1.getClass().getName());
			try {
				Field[] fds;
				if (id != null) {
					fds = new Field[]{o1.getClass().getDeclaredField(id)};
				} else {
					fds = o1.getClass().getDeclaredFields();
				}
				int r;
				for (int i = 0; i < fds.length; i++) {
					fds[i].setAccessible(true);
					r = compare(fds[i].get(o1), fds[i].get(o2));
					if (r != 0) return r;
				}
				return 0;
			} catch (Exception e) {
				return -1;
			}
		}
	}

}
