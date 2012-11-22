package com.raitongorganicsfarm.app.mb.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.raitongorganicsfarm.app.mb.entity.Subscriber;
import com.raitongorganicsfarm.app.mb.entity.Subscription;
import com.raitongorganicsfarm.app.mb.repository.SubscriberRepository;

public class SubscriptionServiceImpl implements SubscriptionService {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SubscriberRepository subscriberRepository;

	@Override
	public void delete(String customerNo, String subscriptionId) throws Exception {
		boolean isCustomerNull = (customerNo == null);
		boolean isSubscriptionIdNull = (subscriptionId == null);
		if (isCustomerNull) {
			if (isSubscriptionIdNull) {
				throw new Exception("Invalid Customer No and Subscription ID");
			}
			throw new Exception("Invalid Customer No");
		} else {
			if (isSubscriptionIdNull) {
				throw new Exception("Invalid Subscription ID");
			}
		}
		Subscriber subscriber = findSubscriberByCustomerNo(customerNo);
		List<Subscription> subscriptions = subscriber.getSubscriptions();
		if (subscriptions == null) {
			throw new Exception("Missing Customer No for this Subscription ID");
		}
		boolean subscriptionFound = false;
		for (Subscription sp : subscriptions) {
			if (sp.getId().equals(subscriptionId)) {
				subscriptionFound = true;
			}
		}
		if (!subscriptionFound) {
			throw new Exception("Missing Customer No for this Subscription ID");
		}
		Query query = Query.query(Criteria.where("id").is(subscriptionId));
		mongoTemplate.remove(query, Subscription.class);
		subscriber = findSubscriberByCustomerNo(customerNo);
		for (Iterator<Subscription> iter = subscriber.getSubscriptions().iterator(); iter.hasNext();) {
			Subscription s = iter.next();
			if (s == null) {
				iter.remove();
			}
		}
		mongoTemplate.save(subscriber);
	}

	private Subscriber findSubscriberByCustomerNo(String customerNo) {
		return subscriberRepository.findByCustomerNo(customerNo);
	}
}
