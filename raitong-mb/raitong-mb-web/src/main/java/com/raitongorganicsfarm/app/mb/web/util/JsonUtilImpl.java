package com.raitongorganicsfarm.app.mb.web.util;

import java.lang.reflect.Method;

public class JsonUtilImpl<T> implements JsonUtil<T> {

	@SuppressWarnings("unchecked")
	@Override
	public T fromJson(String json, Class<T> cls) {
		try {
			Method method = cls.getMethod("fromJsonTo" + cls.getSimpleName(),
					String.class);
			Object object = method.invoke(null, json);
			return (T) object;
		} catch (Exception e) {
			throw new RuntimeException("Cannot transform to Object", e);
		}
	}
}
