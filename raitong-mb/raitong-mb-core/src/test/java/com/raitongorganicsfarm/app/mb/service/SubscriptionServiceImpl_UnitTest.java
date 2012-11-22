package com.raitongorganicsfarm.app.mb.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.raitongorganicsfarm.app.mb.entity.Subscriber;
import com.raitongorganicsfarm.app.mb.entity.Subscription;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class SubscriptionServiceImpl_UnitTest {
	@Autowired
	private SubscriptionService subscriptionService;
	@Autowired
	private MongoTemplate mongoTemplate;
	private Subscriber subscriber;
	private Subscription subscription;
	private int initSubscriptionSize;

	@Before
	public void before() {
		subscriber = new Subscriber();
		subscriber.setCustomerNo(Integer.toString(new Random().nextInt()));
		Subscription spDummy = new Subscription();
		subscriber.addSubscriptions(spDummy);
		subscription = new Subscription();
		subscriber.addSubscriptions(subscription);
		mongoTemplate.insert(spDummy);
		mongoTemplate.insert(subscription);
		mongoTemplate.insert(subscriber);
		initSubscriptionSize = subscriber.getSubscriptions().size();
	}

	@Test
	public void deleteSubscriptionSuccess() throws Exception {
		String subscriptionid = subscription.getId();
		subscriptionService.delete(subscriber.getCustomerNo(), subscriptionid);

		Query query = Query.query(Criteria.where("id").is(subscriptionid));
		Subscription actual = mongoTemplate.findOne(query, Subscription.class);
		assertNull(actual);
		Query subscriberquery = Query.query(Criteria.where("customerNo").is(subscriber.getCustomerNo()));
		Subscriber actualSubscriber = mongoTemplate.findOne(subscriberquery, Subscriber.class);
		assertNotNull(actualSubscriber);
		List<Subscription> list = actualSubscriber.getSubscriptions();
		assertEquals(initSubscriptionSize - 1, list.size());
	}

	@Test
	public void deleteOtherCustomerSubscription() {
		String subscriptionId = subscription.getId();
		Subscriber otherSubscriber = new Subscriber();
		otherSubscriber.setCustomerNo(Integer.toString(new Random().nextInt()));
		mongoTemplate.insert(otherSubscriber);
		try {
			subscriptionService.delete(otherSubscriber.getCustomerNo(), subscriptionId);
			fail("Assert failed, No exception thrown.");
		} catch (Exception e) {
			assertEquals("Missing Customer No for this Subscription ID", e.getMessage());
		}
	}

	@Test
	public void deleteWithMissingCustomerNo() {
		try {
			subscriptionService.delete(null, subscription.getId());
			fail("Assert failed, No exception thrown.");
		} catch (Exception e) {
			assertEquals("Invalid Customer No", e.getMessage());
		}
	}

	@Test
	public void deleteWithMissingSubscriptionId() {
		try {
			subscriptionService.delete(subscriber.getCustomerNo(), null);
			fail("Assert failed, No exception thrown.");
		} catch (Exception e) {
			assertEquals("Invalid Subscription ID", e.getMessage());
		}
	}

	@Test
	public void deleteWithBothCustomerNoAndSubscriptionIsNull() {
		try {
			subscriptionService.delete(null, null);
			fail("Assert failed, No exception thrown.");
		} catch (Exception e) {
			assertEquals("Invalid Customer No and Subscription ID", e.getMessage());
		}
	}

	@After
	public void after() {
		Query query = new Query();
		mongoTemplate.remove(query, Subscriber.class);
		mongoTemplate.remove(query, Subscription.class);
	}
}
