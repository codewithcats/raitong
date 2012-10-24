package com.raitongorganicsfarm.app.mb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.raitongorganicsfarm.app.mb.entity.Subscriber;
import com.raitongorganicsfarm.app.mb.entity.Subscription;
import com.raitongorganicsfarm.app.mb.repository.SubscriberRepository;
import com.raitongorganicsfarm.app.mb.repository.SubscriptionRepository;
import com.raitongorganicsfarm.app.mb.web.util.JsonUtil;
import com.raitongorganicsfarm.app.mb.web.util.JsonUtilImpl;

@Controller
public class SubscriberController {

	private SubscriberRepository subscriberRepository;
	private SubscriptionRepository subscriptionRepository;
	private JsonUtil<Subscriber> jsonUtil = new JsonUtilImpl<Subscriber>();

	@RequestMapping(method = RequestMethod.POST, value = "/subscribers/**")
	public ResponseEntity<String> create(@RequestBody String body) {
		try {
			Subscriber subscriber = jsonUtil.fromJson(body, Subscriber.class);
			List<Subscription> subscriptions = subscriber.getSubscriptions();
			if(subscriptions != null && !subscriptions.isEmpty()) {
				subscriptions = (List<Subscription>) subscriptionRepository.save(subscriptions);
				subscriber.setSubscriptions(subscriptions);
			}
			subscriber = this.subscriberRepository.save(subscriber);
			String j = subscriber.toJson();
			return new ResponseEntity<String>(j, HttpStatus.CREATED);
		} catch (RuntimeException r) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/subscribers")
	public ResponseEntity<String> list() {
		try {
			List<Subscriber> subscribers = this.subscriberRepository.findAll();
			String s = Subscriber.toJsonArray(subscribers);
			ResponseEntity<String> response = new ResponseEntity<String>(s, HttpStatus.OK);
			return response;
		} catch (RuntimeException r) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/subscribers/{customerNo}")
	public ResponseEntity<String> info(@PathVariable String customerNo) {
		try {
			if(customerNo == null || customerNo.trim().length() == 0) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			Subscriber subscriber = this.subscriberRepository.findByCustomerNo(customerNo);
			if(subscriber == null) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			String s = subscriber.toJson();
			ResponseEntity<String> resp = new ResponseEntity<String>(s, HttpStatus.OK);
			return resp;
		} catch(RuntimeException r) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/subscribers/{customerNo}")
	public ResponseEntity<String> delete(@PathVariable String customerNo) {
		try {
			if (customerNo != null && customerNo.trim().length() > 0) {
				this.subscriberRepository.removeByCustomerNo(customerNo);
				ResponseEntity<String> res = new ResponseEntity<String>(HttpStatus.OK);
				return res;
			}
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		} catch(RuntimeException r) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Autowired
	public void setSubscriberRepository(SubscriberRepository subscriberRepository) {
		this.subscriberRepository = subscriberRepository;
	}

	@Autowired
	public void setSubscriptionRepository(SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}
	
	public void setJsonUtil(JsonUtil<Subscriber> jsonUtil) {
		this.jsonUtil = jsonUtil;
	}
}
