package com.raitongorganicsfarm.app.mb.web;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
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
		Subscription subscription = new Subscription();
		String reqBody = prepareMockingJsonUtilOf(this.subscriptionController, subscription);

		Subscription expected = createSubscriptionWithRandomId();

		SubscriptionRepository repo = mock(SubscriptionRepository.class);
		when(repo.save(subscription)).thenReturn(expected);
		this.subscriptionController.setSubscriptionRepository(repo);

		Subscriber subscriber = createSubscriberWithRandomId(2);
		
		String customerNo = subscriber.getCustomerNo();
		SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);
		when(subscriberRepository.findByCustomerNo(customerNo)).thenReturn(subscriber);
		when(subscriberRepository.save(subscriber)).thenReturn(subscriber);
		this.subscriptionController.setSubscriberRepository(subscriberRepository);
		// test
		ResponseEntity<String> actual = this.subscriptionController.create(customerNo, reqBody);
		assertEquals(HttpStatus.CREATED, actual.getStatusCode());
		assertEquals(expected.toJson(), actual.getBody());
		Subscriber actualSubscriber = this.subscriptionController.getSubscriber();
		assertNotNull(actualSubscriber);
		assertEquals(subscriber, actualSubscriber);
		verify(subscriberRepository).save(subscriber);
	}

	@Test
	public void testCreateSubscriptionButNotFoundSubscriber() {
		// mock subscriber
		SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);
		String customerNo = generateLongStringId();
		when(subscriberRepository.findByCustomerNo(customerNo)).thenReturn(null);
		this.subscriptionController.setSubscriberRepository(subscriberRepository);

		Subscription subscription = createSubscriptionWithRandomId();
		String body = subscription.toJson();
		ResponseEntity<String> req = this.subscriptionController.create(customerNo, body);
		assertEquals(HttpStatus.BAD_REQUEST, req.getStatusCode());
	}

	@Test
	public void testCreateNullSubscription() {
		Subscriber subscriber = createSubscriberWithRandomId(2);
		String customerNo = subscriber.getCustomerNo();
		SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);
		when(subscriberRepository.findByCustomerNo(customerNo)).thenReturn(subscriber);
		when(subscriberRepository.save(subscriber)).thenReturn(subscriber);
		this.subscriptionController.setSubscriberRepository(subscriberRepository);

		ResponseEntity<String> req = subscriptionController.create(subscriber.getCustomerNo(), null);
		assertEquals(HttpStatus.BAD_REQUEST, req.getStatusCode());
	}

	@Test
	public void testCreateRuntimeExceptionAtSubscrptionRepository() {
		SubscriptionRepository repo = mock(SubscriptionRepository.class);
		Subscription s = createSubscriptionWithRandomId();
		when(repo.save(s)).thenThrow(new RuntimeException());
		this.subscriptionController.setSubscriptionRepository(repo);

		String requestSubscriptionBody = prepareMockingJsonUtilOf(this.subscriptionController, s);

		ResponseEntity<String> req = this.subscriptionController.create("error", requestSubscriptionBody);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, req.getStatusCode());
	}

	@Test
	public void testCreateRunetimeExceptionAtSubscriberRepository() {
		SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);
		String customerNo = generateLongStringId();
		when(subscriberRepository.findByCustomerNo(customerNo)).thenThrow(new RuntimeException());
		this.subscriptionController.setSubscriberRepository(subscriberRepository);

		String body = "dont care";
		ResponseEntity<String> req = this.subscriptionController.create(customerNo, body);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, req.getStatusCode());
	}

	@Test
	public void testInfoSuccess() {
		SubscriptionRepository repo = mock(SubscriptionRepository.class);
		Subscription expected = createSubscriptionWithRandomId();
		String id = expected.getId();
		when(repo.findOne(id)).thenReturn(expected);
		this.subscriptionController.setSubscriptionRepository(repo);

		ResponseEntity<String> req = this.subscriptionController.info(id);
		assertEquals(HttpStatus.OK, req.getStatusCode());
		assertEquals(id, Subscription.fromJsonToSubscription(req.getBody()).getId());
	}

	@Test
	public void testInfoNotFound() {
		String subscriptionId = generateLongStringId();
		SubscriptionRepository repo = mock(SubscriptionRepository.class);
		when(repo.findOne(subscriptionId)).thenReturn(null);
		this.subscriptionController.setSubscriptionRepository(repo);

		ResponseEntity<String> req = subscriptionController.info(subscriptionId);
		assertEquals(HttpStatus.BAD_REQUEST, req.getStatusCode());
	}

	@Test
	public void testInfoByNullId() {
		ResponseEntity<String> req = this.subscriptionController.info(null);
		assertEquals(HttpStatus.BAD_REQUEST, req.getStatusCode());
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

	@Test
	public void testEditSuccess() {
		int expectedSize = (new Random().nextInt(10)) + 1;
		Subscriber subscriber = createSubscriberWithRandomId(expectedSize);

		int expectedEditIndex = new Random().nextInt(expectedSize);
		List<Subscription> subscriptions = subscriber.getSubscriptions();
		Subscription expectedSubscription = new Subscription();
		expectedSubscription.setId("expected");
		subscriptions.set(expectedEditIndex, expectedSubscription);

		SubscriberRepository subscriberRepo = mock(SubscriberRepository.class);
		String customerNo = subscriber.getCustomerNo();
		when(subscriberRepo.findByCustomerNo(customerNo)).thenReturn(subscriber);
		when(subscriberRepo.save(subscriber)).thenReturn(subscriber);
		this.subscriptionController.setSubscriberRepository(subscriberRepo);

		String subscriptionBody = prepareMockingJsonUtilOf(this.subscriptionController, expectedSubscription);

		SubscriptionRepository subscriptionRepository = mock(SubscriptionRepository.class);
		when(subscriptionRepository.save(expectedSubscription)).thenReturn(expectedSubscription);
		this.subscriptionController.setSubscriptionRepository(subscriptionRepository);

		ResponseEntity<String> req = this.subscriptionController.edit(customerNo, subscriptionBody);

		assertEquals(HttpStatus.OK, req.getStatusCode());
		assertEquals(subscriptionBody, req.getBody());
		Subscriber actualSubscriber = this.subscriptionController.getSubscriber();
		assertEquals(subscriber, actualSubscriber);
		assertEquals(expectedSize, actualSubscriber.getSubscriptions().size());
		assertEquals(expectedEditIndex, this.subscriptionController.getEditIndex());
	}

	@Test
	public void testEditWithNotFoundCustomerNo() {
		String customerNo = "notFound";

		SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);
		when(subscriberRepository.findByCustomerNo(customerNo)).thenReturn(null);
		this.subscriptionController.setSubscriberRepository(subscriberRepository);

		Subscription subscription = createSubscriptionWithRandomId();
		JsonUtil<Subscription> jsonUtil = mock(JsonUtil.class);
		String subscriptionBody = subscription.toJson();
		when(jsonUtil.fromJson(subscriptionBody, Subscription.class)).thenReturn(subscription);

		ResponseEntity<String> res = this.subscriptionController.edit(customerNo, subscriptionBody);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
	}

	@Test
	public void testEditWithSubscriptionThatDoesntOwnBySubscriber() {
		Subscriber preparedSubscriber = createSubscriberWithRandomId(10);
		String customerNo = preparedSubscriber.getCustomerNo();
		SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);
		when(subscriberRepository.findByCustomerNo(customerNo)).thenReturn(preparedSubscriber);
		when(subscriberRepository.save(preparedSubscriber)).thenReturn(preparedSubscriber);
		this.subscriptionController.setSubscriberRepository(subscriberRepository);

		Subscription otherSubscription = new Subscription();
		otherSubscription.setId("others");
		String requestSubscriptionBody = prepareMockingJsonUtilOf(this.subscriptionController, otherSubscription);
		ResponseEntity<String> res = this.subscriptionController.edit(preparedSubscriber.getCustomerNo(), requestSubscriptionBody);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
	}

	@Test
	public void testEditRuntimeExceptionAtSubscriptionRepository() {
		Subscriber subscriber = createSubscriberWithRandomId(1);
		String customerNo = subscriber.getCustomerNo();
		SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);
		when(subscriberRepository.findByCustomerNo(customerNo)).thenReturn(subscriber);
		this.subscriptionController.setSubscriberRepository(subscriberRepository);

		SubscriptionRepository subscriptionRepo = mock(SubscriptionRepository.class);
		Subscription subscription = subscriber.getSubscriptions().get(0);
		when(subscriptionRepo.save(subscription)).thenThrow(new RuntimeException());
		this.subscriptionController.setSubscriptionRepository(subscriptionRepo);

		String body = prepareMockingJsonUtilOf(this.subscriptionController, subscription);

		ResponseEntity<String> res = this.subscriptionController.edit(customerNo, body);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
	}

	@Test
	public void testEditRuntimeExceptionAtSubscriberRepository() {
		Subscriber subscriber = createSubscriberWithRandomId(1);
		String customerNo = subscriber.getCustomerNo();
		SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);
		when(subscriberRepository.findByCustomerNo(customerNo)).thenThrow(new RuntimeException());
		this.subscriptionController.setSubscriberRepository(subscriberRepository);

		ResponseEntity<String> res = this.subscriptionController.edit(customerNo, "whatever");

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
	}

	private Subscriber createSubscriberWithRandomId(int expectedSubscriptiosSize) {
		Subscriber s = new Subscriber();
		s.setSubscriptions(createSubscriptionList(expectedSubscriptiosSize));
		return s;
	}

	private List<Subscription> createSubscriptionList(int expectedSize) {
		List<Subscription> subscriptions = new LinkedList<Subscription>();
		for (int i = 0; i < expectedSize; i++) {
			subscriptions.add(createSubscriptionWithRandomId());
		}
		return subscriptions;
	}

	private String prepareMockingJsonUtilOf(SubscriptionController controller, Subscription subscription) {
		String otherSubscriptionBody = subscription.toJson();
		JsonUtil<Subscription> jsonUtil = mock(JsonUtil.class);
		when(jsonUtil.fromJson(otherSubscriptionBody, Subscription.class)).thenReturn(subscription);
		controller.setJsonUtil(jsonUtil);
		return otherSubscriptionBody;
	}

	private Subscription createSubscriptionWithRandomId() {
		Subscription s = new Subscription();
		s.setId(generateLongStringId());
		return s;
	}

	private String generateLongStringId() {
		return String.valueOf(new Random().nextLong());
	}
}
