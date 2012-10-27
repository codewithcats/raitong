package com.raitongorganicsfarm.app.mb.web;

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
public class SubscriptionController {

	private SubscriptionRepository subscriptionRepository;
	private SubscriberRepository subscriberRepository;
	private JsonUtil<Subscription> jsonUtil = new JsonUtilImpl<Subscription>();
	private Subscriber subscriber;

	@RequestMapping(method = RequestMethod.POST, value = "/subscriber/{customerNo}/subscriptions/")
	public ResponseEntity<String> create(@PathVariable String customerNo, @RequestBody String body) {
		if(body == null) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		try {
			subscriber = subscriberRepository.findByCustomerNo(customerNo);
			if(subscriber == null) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			Subscription subscription = jsonUtil.fromJson(body, Subscription.class);
			subscription = this.subscriptionRepository.save(subscription);
			
			
			return new ResponseEntity<String>(subscription.toJson(), HttpStatus.CREATED);
		} catch (RuntimeException r) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/subscriptions/{id}")
	public ResponseEntity<String> info(@PathVariable String id) {
		try {
			Subscription subscription = this.subscriptionRepository.findOne(id);
			return new ResponseEntity<String>(subscription.toJson(), HttpStatus.OK);
		} catch (RuntimeException r) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
}
