package com.raitongorganicsfarm.app.mb.entity;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
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
	
	@Test
	public void testAddSubscription() {
		Subscriber s = new Subscriber();
		Subscription subscription = new Subscription();
		s.addSubscriptions(subscription);
		List<Subscription> list = s.getSubscriptions();
		assertTrue(list.contains(subscription));
	}
	
	@Test
	public void if_subscription_list_is_null_it_should_be_created_one() {
		Subscriber s = new Subscriber();
		Subscription subscription = new Subscription();
		s.addSubscriptions(subscription);
		List<Subscription> list = s.getSubscriptions();
		assertNotNull(list);
	}
}
