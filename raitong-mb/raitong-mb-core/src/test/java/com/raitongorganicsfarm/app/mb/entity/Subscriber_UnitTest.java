package com.raitongorganicsfarm.app.mb.entity;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Random;

import org.junit.Test;

public class Subscriber_UnitTest {

	@Test
	public void testDeserializeDate() {
		long ms = new Random().nextLong();
		Date date = new Date(ms);
		Subscriber subscriber = new Subscriber();
		subscriber.setCreatedDate(date);
		String in = subscriber.toJson();
		Subscriber actual = Subscriber.fromJsonToSubscriber(in);
		assertEquals(subscriber.getCreatedDate(), actual.getCreatedDate());
	}
}
