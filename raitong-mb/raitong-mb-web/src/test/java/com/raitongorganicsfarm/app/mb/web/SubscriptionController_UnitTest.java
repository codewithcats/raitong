package com.raitongorganicsfarm.app.mb.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.raitongorganicsfarm.app.mb.entity.Subscriber;
import com.raitongorganicsfarm.app.mb.entity.Subscription;
import com.raitongorganicsfarm.app.mb.repository.SubscriberRepository;
import com.raitongorganicsfarm.app.mb.repository.SubscriptionRepository;
import com.raitongorganicsfarm.app.mb.web.util.JsonUtil;

public class SubscriptionController_UnitTest {

	private SubscriptionController subscriptionController;

	@Before
	public void setUp() {
		subscriptionController = new SubscriptionController();
	}

	@Test
	public void testCreateNewSubscriptionSuccess() {
		// mock subsription
		Subscription s = new Subscription();
		String reqBody = s.toString();

		Subscription expected = new Subscription();
		String id = generateLongStringId();
		expected.setId(id);

		JsonUtil<Subscription> jsonUtil = mock(JsonUtil.class);
		when(jsonUtil.fromJson(reqBody, Subscription.class)).thenReturn(s);
		this.subscriptionController.setJsonUtil(jsonUtil);

		SubscriptionRepository repo = mock(SubscriptionRepository.class);
		when(repo.save(s)).thenReturn(expected);
		this.subscriptionController.setSubscriptionRepository(repo);

		Subscriber subscriber = prepareMockingCompleteSubscriberWithBlankSubscriptions(this.subscriptionController);
		String customerNo = subscriber.getCustomerNo();
		// test
		ResponseEntity<String> actual = this.subscriptionController.create(customerNo, reqBody);
		assertEquals(HttpStatus.CREATED, actual.getStatusCode());
		assertEquals(expected.toJson(), actual.getBody());
		Subscriber actualSubscriber = this.subscriptionController.getSubscriber();
		assertNotNull(actualSubscriber);
		assertEquals(subscriber, actualSubscriber);
	}

	@Test
	public void testCreateSubscriptionButNotFoundSubscriber() {
		// mock subscriber
		SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);
		String customerNo = generateLongStringId();
		when(subscriberRepository.findByCustomerNo(customerNo)).thenReturn(null);
		this.subscriptionController.setSubscriberRepository(subscriberRepository);

		Subscription subscription = new Subscription();
		String body = subscription.toJson();
		ResponseEntity<String> req = this.subscriptionController.create(customerNo, body);
		assertEquals(HttpStatus.BAD_REQUEST, req.getStatusCode());
	}

	@Test
	public void testCreateNullSubscription() {
		Subscriber subscriber = prepareMockingCompleteSubscriberWithBlankSubscriptions(this.subscriptionController);

		ResponseEntity<String> req = subscriptionController.create(subscriber.getCustomerNo(), null);
		assertEquals(HttpStatus.BAD_REQUEST, req.getStatusCode());
	}

	private Subscriber prepareMockingCompleteSubscriberWithBlankSubscriptions(SubscriptionController controller) {
		Subscriber subscriber = new Subscriber();
		subscriber.setId(generateLongStringId());
		subscriber.setCustomerNo(generateLongStringId());
		subscriber.setSubscriptions(new LinkedList<Subscription>());
		String customerNo = subscriber.getCustomerNo();

		SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);
		when(subscriberRepository.findByCustomerNo(customerNo)).thenReturn(subscriber);
		when(subscriberRepository.save(subscriber)).thenReturn(subscriber);
		controller.setSubscriberRepository(subscriberRepository);
		return subscriber;
	}

	private String generateLongStringId() {
		return String.valueOf(new Random().nextLong());
	}

	@Test
	public void testCreateRuntimeException() {
		SubscriptionRepository repo = mock(SubscriptionRepository.class);
		Subscription s = new Subscription();
		when(repo.save(s)).thenThrow(new RuntimeException());
		this.subscriptionController.setSubscriptionRepository(repo);

		String reqBody = s.toJson();

		JsonUtil<Subscription> jsonUtil = mock(JsonUtil.class);
		when(jsonUtil.fromJson(reqBody, Subscription.class)).thenReturn(s);
		this.subscriptionController.setJsonUtil(jsonUtil);

		ResponseEntity<String> req = this.subscriptionController.create("error", reqBody);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, req.getStatusCode());
	}

	@Test
	public void testInfoSuccess() {
		SubscriptionRepository repo = mock(SubscriptionRepository.class);
		String id = generateLongStringId();
		Subscription expected = new Subscription();
		expected.setId(id);
		when(repo.findOne(id)).thenReturn(expected);
		this.subscriptionController.setSubscriptionRepository(repo);

		ResponseEntity<String> req = this.subscriptionController.info(id);
		assertEquals(HttpStatus.OK, req.getStatusCode());
		assertEquals(id, Subscription.fromJsonToSubscription(req.getBody()).getId());
	}

	@Test
	public void testInfoRuntimeException() {
		SubscriptionRepository repo = mock(SubscriptionRepository.class);
		String id = generateLongStringId();
		when(repo.findOne(id)).thenThrow(new RuntimeException());
		this.subscriptionController.setSubscriptionRepository(repo);

		ResponseEntity<String> req = this.subscriptionController.info(id);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, req.getStatusCode());
	}
}
