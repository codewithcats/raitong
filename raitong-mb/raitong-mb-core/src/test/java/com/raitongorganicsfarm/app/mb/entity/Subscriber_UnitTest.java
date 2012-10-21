package com.raitongorganicsfarm.app.mb.entity;

import java.lang.reflect.Method;

import org.junit.Test;

public class Subscriber_UnitTest {

	@Test
	public void testGetMethod() {
		Class<Subscriber> cls = Subscriber.class;
		System.out.println(cls.getSimpleName());
		Method[] methods = cls.getMethods();
		for(Method m: methods) {
			System.out.println(m);
		}
	}
}
