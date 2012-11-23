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
import com.raitongorganicsfarm.app.mb.service.SubscriptionService;
import com.raitongorganicsfarm.app.mb.web.util.JsonUtil;
import com.raitongorganicsfarm.app.mb.web.util.JsonUtilImpl;

@Controller
public class SubscriptionController {

	private SubscriptionRepository subscriptionRepository;
	private SubscriberRepository subscriberRepository;
	@Autowired
	private SubscriptionService subscriptionService;
	private JsonUtil<Subscription> jsonUtil = new JsonUtilImpl<Subscription>();
	private Subscriber subscriber;
	private int editIndex = 0;

	@RequestMapping(method = RequestMethod.POST, value = "/subscribers/{customerNo}/subscriptions/**")
	public ResponseEntity<String> create(@PathVariable String customerNo, @RequestBody String body) {
		if (body == null) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		try {
			subscriber = subscriberRepository.findByCustomerNo(customerNo);
			if (subscriber == null) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			Subscription subscription = jsonUtil.fromJson(body, Subscription.class);
			subscription = this.subscriptionRepository.save(subscription);
			subscriber.addSubscriptions(subscription);
			this.subscriberRepository.save(subscriber);

			return new ResponseEntity<String>(subscription.toJson(), HttpStatus.CREATED);
		} catch (RuntimeException r) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/subscribers/{customerNo}/subscriptions/{id}")
	public ResponseEntity<String> info(@PathVariable String customerNo, @PathVariable String id) {
		if (id == null) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		try {
			Subscription subscription = this.subscriptionRepository.findOne(id);
			if (subscription == null) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<String>(subscription.toJson(), HttpStatus.OK);
		} catch (RuntimeException r) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/subscribers/{customerNo}/subscriptions/**")
	public ResponseEntity<String> edit(@PathVariable String customerNo, @RequestBody String body) {
		try {
			subscriber = this.subscriberRepository.findByCustomerNo(customerNo);
		} catch (RuntimeException r) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (subscriber == null) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		Subscription subscription = jsonUtil.fromJson(body, Subscription.class);
		List<Subscription> subscriptions = subscriber.getSubscriptions();
		for (Subscription s : subscriptions) {
			if (s.getId().equals(subscription.getId())) {
				try {
					subscription = this.subscriptionRepository.save(subscription);
				} catch (RuntimeException r) {
					return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
				break;
			}
			editIndex++;
		}
		if (editIndex == subscriptions.size()) {
			// subscription is not found in this list
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		subscriptions.set(editIndex, subscription);
		subscriber = this.subscriberRepository.save(subscriber);
		return new ResponseEntity<String>(subscription.toJson(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/subscribers/{customerNo}/subscriptions/{supscriptionId}")
	public ResponseEntity<String> delete(@PathVariable String customerNo, @PathVariable String supscriptionId) {
		try {
			this.subscriptionService.delete(customerNo, supscriptionId);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Autowired
	public void setSubscriptionRepository(SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	@Autowired
	public void setSubscriberRepository(SubscriberRepository subscriberRepository) {
		this.subscriberRepository = subscriberRepository;
	}

	public void setJsonUtil(JsonUtil<Subscription> jsonUtil) {
		this.jsonUtil = jsonUtil;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public int getEditIndex() {
		return editIndex;
	}
}