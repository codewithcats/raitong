package com.raitongorganicsfarm.app.mb.repository;

import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

public class SubscriberQueryHelper {
	
	private static Query lastestSubscriberQuery;

	public static Query lastestSubscriberQuery() {
		if(lastestSubscriberQuery == null) {
			lastestSubscriberQuery = new Query();
			lastestSubscriberQuery.limit(1).sort().on("createdDate", Order.DESCENDING);
		}
		return lastestSubscriberQuery;
	}
}
