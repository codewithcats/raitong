package com.raitongorganicsfarm.app.mb.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.management.RuntimeErrorException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

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
		when(repo.findAll()).thenReturn(mockSubscribers);

		String jsonArray = Subscriber.toJsonArray(mockSubscribers);

		this.controller.setSubscriberRepository(repo);
		ResponseEntity<String> resp = this.controller.list();

		assertEquals(HttpStatus.OK, resp.getStatusCode());
		assertEquals(jsonArray, resp.getBody());
	}
	
	@Test
	public void testListSubscriberRuntimeError() {
		SubscriberRepository repo = mock(SubscriberRepository.class);
		when(repo.findAll()).thenThrow(new RuntimeException());
		
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
}
