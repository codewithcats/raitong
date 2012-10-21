package com.raitongorganicsfarm.app.mb.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.raitongorganicsfarm.app.mb.entity.Subscriber;
import com.raitongorganicsfarm.app.mb.repository.SubscriberRepository;
import com.raitongorganicsfarm.app.mb.web.util.JsonUtil;

public class SubscriberController_UnitTest {

	private SubscriberController controller;
	
	@Before
	public void before() {
		this.controller = new SubscriberController();
	}
	
	@Test
	public void testSaveSubscriberSuccess() {
		Subscriber input = new Subscriber();
		String id = (String.valueOf(new Random().nextLong()));
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
}
