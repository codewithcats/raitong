package com.raitongorganicsfarm.app.mb.web.util;


public interface JsonUtil<T> {
	T fromJson(String json, Class<T> cls);
}
