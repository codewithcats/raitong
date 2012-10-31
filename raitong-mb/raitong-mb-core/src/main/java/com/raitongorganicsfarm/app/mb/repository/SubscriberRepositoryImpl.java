package com.raitongorganicsfarm.app.mb.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import com.raitongorganicsfarm.app.mb.entity.Subscriber;

public class SubscriberRepositoryImpl implements SubscriberRepositoryCustom {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void removeByCustomerNo(String customerNo) {
		Query query = Query.query(Criteria.where("customerNo").is(customerNo));
		mongoTemplate.remove(query, Subscriber.class);
	}

	@Override
	public List<Subscriber> findAllOrderByCustomerNo() {
		Query query = new Query();
		query.sort().on("customerNo", Order.ASCENDING);
		return mongoTemplate.find(query, Subscriber.class);
	}

}
