package com.raitongorganicsfarm.app.mb.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.raitongorganicsfarm.app.mb.entity.Subscriber;
import com.raitongorganicsfarm.app.mb.entity.Subscription;
import com.raitongorganicsfarm.app.mb.repository.SubscriberRepository;
import com.raitongorganicsfarm.app.mb.repository.SubscriptionRepository;
import com.raitongorganicsfarm.app.mb.web.util.JsonUtil;

public class SubscriberController_UnitTest {

	private SubscriberController controller;

	@Before
	public void before() {
		this.controller = new SubscriberController();
	}

	@Test
	public void testSaveSubscriberSuccess() {
		Subscriber input = generateSubscriber(false, true);
		String id = input.getId();
		String body = input.toJson();

		JsonUtil jsonUtil = mock(JsonUtil.class);
		when(jsonUtil.fromJson(body, Subscriber.class)).thenReturn(input);

		Subscriber result = new Subscriber();
		result.setId(id);
		SubscriberRepository repository = mock(SubscriberRepository.class);
		when(repository.save(input)).thenReturn(result);

		this.controller.setSubscriberRepository(repository);
		this.controller.setJsonUtil(jsonUtil);
		ResponseEntity<String> response = this.controller.create(body);

		assertEquals(result.toJson(), response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void testSaveSubscriberRuntimeError() {
		Subscriber subscriber = new Subscriber();
		SubscriberRepository repository = mock(SubscriberRepository.class);
		when(repository.save(subscriber)).thenThrow(new RuntimeException());

		this.controller.setSubscriberRepository(repository);
		ResponseEntity<String> response = this.controller.create(subscriber.toJson());

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testSaveSubscriberWithSubscriptionSuccess() {
		SubscriberRepository subscriberRepo = mock(SubscriberRepository.class);
		SubscriptionRepository subscriptionRepo = mock(SubscriptionRepository.class);
		
		Subscriber input = generateSubscriber(true, false);
		
		String body = input.toJson();

		JsonUtil jsonUtil = mock(JsonUtil.class);
		when(jsonUtil.fromJson(body, Subscriber.class)).thenReturn(input);
		Subscriber result = generateSubscriber(true, true);
		when(subscriberRepo.save(input)).thenReturn(result);
		when(subscriptionRepo.save(input.getSubscriptions())).thenReturn(result.getSubscriptions());
		
		this.controller.setSubscriberRepository(subscriberRepo);
		this.controller.setSubscriptionRepository(subscriptionRepo);
		this.controller.setJsonUtil(jsonUtil);
		
		ResponseEntity<String> resp = this.controller.create(body);
		assertEquals(HttpStatus.CREATED, resp.getStatusCode());
		assertEquals(result.toJson(), resp.getBody());
	}

	private Subscriber generateSubscriber(boolean haveOneSubscription, boolean setAllId) {
		Subscriber input = new Subscriber();
		if(setAllId) {
			input.setId(getRandomId());
		}
		if(haveOneSubscription) {
			List<Subscription> subscripts = new LinkedList<Subscription>();
			Subscription subscript = new Subscription();
			if(setAllId) {
				subscript.setId(getRandomId());
			}
			subscripts.add(subscript);
			input.setSubscriptions(subscripts);
			
		}
		return input;
	}

	private String getRandomId() {
		return String.valueOf(new Random().nextLong());
	}
	
	@Test
	public void testDeleteSubscriberSuccess() {
		String customerNo = "999";

		SubscriberRepository repo = mock(SubscriberRepository.class);
		Mockito.doNothing().when(repo).removeByCustomerNo(customerNo);
		repo.removeByCustomerNo(customerNo);

		this.controller.setSubscriberRepository(repo);
		ResponseEntity<String> resp = this.controller.delete(customerNo);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}

	@Test
	public void testDeleteSubscriberWithNullCustomerNo() {
		String customerNo = null;
		ResponseEntity<String> resp = this.controller.delete(customerNo);
		assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
	}

	@Test
	public void testDeleteSubscriberWithBlankCustomerNo() {
		String customerNo = "";
		ResponseEntity<String> resp = this.controller.delete(customerNo);
		assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
	}
	
	@Test
	public void testDeleteRuntimeError() {
		SubscriberRepository repo = mock(SubscriberRepository.class);
		String customerNo = "t";
		Mockito.doThrow(new RuntimeException()).when(repo).removeByCustomerNo(customerNo);
		
		this.controller.setSubscriberRepository(repo);
		ResponseEntity<String> resp = this.controller.delete(customerNo);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
	}

	@Test
	public void testListSubscriberSuccess() {
		SubscriberRepository repo = mock(SubscriberRepository.class);
		List<Subscriber> mockSubscribers = new LinkedList<Subscriber>();
		Subscriber s = new Subscriber();
		s.setAddress("A1");
		s.setCustomerNo("CN1");
		mockSubscribers.add(s);
		when(repo.findAllOrderByCustomerNo()).thenReturn(mockSubscribers);

		String jsonArray = Subscriber.toJsonArray(mockSubscribers);

		this.controller.setSubscriberRepository(repo);
		ResponseEntity<String> resp = this.controller.list();

		assertEquals(HttpStatus.OK, resp.getStatusCode());
		assertEquals(jsonArray, resp.getBody());
	}
	
	@Test
	public void testListSubscriberRuntimeError() {
		SubscriberRepository repo = mock(SubscriberRepository.class);
		when(repo.findAllOrderByCustomerNo()).thenThrow(new RuntimeException());
		
		this.controller.setSubscriberRepository(repo);
		ResponseEntity<String> resp = this.controller.list();
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
	}

	@Test
	public void testInfoSubscriberSuccess() {
		SubscriberRepository repo = mock(SubscriberRepository.class);
		Subscriber sub = new Subscriber();
		String customerNoCriteria = "NO123";
		String name = "testName";
		sub.setCustomerNo(customerNoCriteria);
		sub.setName(name);
		when(repo.findByCustomerNo(customerNoCriteria)).thenReturn(sub);

		this.controller.setSubscriberRepository(repo);
		ResponseEntity<String> resp = this.controller.info(customerNoCriteria);

		assertEquals(HttpStatus.OK, resp.getStatusCode());
		assertEquals(sub.toJson(), resp.getBody());
	}
	
	@Test
	public void testInfoSubscriberWithNullCriteria() {
		ResponseEntity<String> resp = this.controller.info(null);
		assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
	}
	
	@Test
	public void testInfoSubscriberWithBlankCriteria() {
		ResponseEntity<String> resp = this.controller.info("");
		assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
	}
	
	@Test
	public void testInfoSubscriberWithNullResult() {
		SubscriberRepository repo = mock(SubscriberRepository.class);
		String anyCustomerNo = "any";
		when(repo.findByCustomerNo(anyCustomerNo)).thenReturn(null);
		
		this.controller.setSubscriberRepository(repo);
		ResponseEntity<String> resp = this.controller.info(anyCustomerNo);
		
		assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
	}
	
	@Test
	public void testInfoSubscriberRuntimeError() {
		SubscriberRepository repo = mock(SubscriberRepository.class);
		String any = "any";
		when(repo.findByCustomerNo(any)).thenThrow(new RuntimeException());
		
		this.controller.setSubscriberRepository(repo);
		ResponseEntity<String> resp = this.controller.info(any);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
	}

	@Test
	public void testEditSubscriberWithSubscription() {
		Subscriber subscriber = generateSubscriber(true, false);
		Subscriber result = generateSubscriber(true, true);
		
		JsonUtil<Subscriber> jsonUtil = mock(JsonUtil.class);
		when(jsonUtil.fromJson(subscriber.toJson(), Subscriber.class)).thenReturn(subscriber);
		this.controller.setJsonUtil(jsonUtil);
		
		SubscriberRepository repo = mock(SubscriberRepository.class);
		when(repo.save(subscriber)).thenReturn(result);
		this.controller.setSubscriberRepository(repo);
		
		SubscriptionRepository subscriptionRepo = mock(SubscriptionRepository.class);
		when(subscriptionRepo.save(subscriber.getSubscriptions())).thenReturn(result.getSubscriptions());
		this.controller.setSubscriptionRepository(subscriptionRepo);
		
		String inputBody = subscriber.toJson();
		ResponseEntity<String> resp = this.controller.edit(inputBody);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		assertEquals(result.toJson(), resp.getBody());
	}
	
	@Test
	public void testEditSubscriberWithoutSubscription() {
		Subscriber subscriber = generateSubscriber(false, false);
		Subscriber result = generateSubscriber(false, true);
		
		JsonUtil<Subscriber> jsonUtil = mock(JsonUtil.class);
		when(jsonUtil.fromJson(subscriber.toJson(), Subscriber.class)).thenReturn(subscriber);
		this.controller.setJsonUtil(jsonUtil);
		
		SubscriberRepository repo = mock(SubscriberRepository.class);
		when(repo.save(subscriber)).thenReturn(result);
		this.controller.setSubscriberRepository(repo);
		
		SubscriptionRepository subscriptionRepo = mock(SubscriptionRepository.class);
		when(subscriptionRepo.save(subscriber.getSubscriptions())).thenReturn(result.getSubscriptions());
		this.controller.setSubscriptionRepository(subscriptionRepo);
		
		String inputBody = subscriber.toJson();
		ResponseEntity<String> resp = this.controller.edit(inputBody);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		assertEquals(result.toJson(), resp.getBody());
	}
	
	@Test
	public void testEditSubscriberRuntimeErrorAtSubscriber() {
		Subscriber s = generateSubscriber(false, true);
		
		JsonUtil<Subscriber> jsonUtil = mock(JsonUtil.class);
		String json = s.toJson();
		when(jsonUtil.fromJson(json, Subscriber.class)).thenReturn(s);
		this.controller.setJsonUtil(jsonUtil);
		
		SubscriberRepository srepo = mock(SubscriberRepository.class);
		when(srepo.save(s)).thenThrow(new RuntimeException());
		this.controller.setSubscriberRepository(srepo);
		
		ResponseEntity<String> resp = this.controller.edit(json);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
	}
	
	@Test
	public void testEditSubscriberRuntimeErrorAtSubscription() {
		Subscriber s = generateSubscriber(true, true);
		
		JsonUtil<Subscriber> jsonUtil = mock(JsonUtil.class);
		String json = s.toJson();
		when(jsonUtil.fromJson(json, Subscriber.class)).thenReturn(s);
		this.controller.setJsonUtil(jsonUtil);
		
		SubscriptionRepository srepo = mock(SubscriptionRepository.class);
		when(srepo.save(s.getSubscriptions())).thenThrow(new RuntimeException());
		this.controller.setSubscriptionRepository(srepo);
		
		ResponseEntity<String> resp = this.controller.edit(json);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
	}
}
