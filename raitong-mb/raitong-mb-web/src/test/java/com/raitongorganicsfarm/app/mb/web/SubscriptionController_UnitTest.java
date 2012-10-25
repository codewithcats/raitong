package com.raitongorganicsfarm.app.mb.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.raitongorganicsfarm.app.mb.entity.Subscription;
import com.raitongorganicsfarm.app.mb.repository.SubscriptionRepository;
import com.raitongorganicsfarm.app.mb.web.util.JsonUtil;

public class SubscriptionController_UnitTest {

	private SubscriptionController subscriptionController;

	@Before
	public void setUp() {
		subscriptionController = new SubscriptionController();
	}

	@Test
	public void testCreateSuccess() {
		Subscription s = new Subscription();
		String reqBody = s.toString();

		Subscription expected = new Subscription();
		String id = String.valueOf(new Random().nextLong());
		expected.setId(id);
		
		JsonUtil<Subscription> jsonUtil = mock(JsonUtil.class);
		when(jsonUtil.fromJson(reqBody, Subscription.class)).thenReturn(s);
		this.subscriptionController.setJsonUtil(jsonUtil);

		SubscriptionRepository repo = mock(SubscriptionRepository.class);
		when(repo.save(s)).thenReturn(expected);
		this.subscriptionController.setSubscriptionRepository(repo);
		
		ResponseEntity<String> actual = this.subscriptionController.create(reqBody);
		assertEquals(HttpStatus.CREATED, actual.getStatusCode());
		assertEquals(expected.toJson(), actual.getBody());
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
		
		ResponseEntity<String> req = this.subscriptionController.create(reqBody);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, req.getStatusCode());
	}
	
	
	@Test
	public void testInfoSuccess() {
		SubscriptionRepository repo = mock(SubscriptionRepository.class);
		String id = "12323";
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
		String id = "exception";
		when(repo.findOne(id)).thenThrow(new RuntimeException());
		this.subscriptionController.setSubscriptionRepository(repo);
		
		ResponseEntity<String> req = this.subscriptionController.info(id);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, req.getStatusCode());
	}
}
