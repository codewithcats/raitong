package com.raitongorganicsfarm.app.mb.repository;

import java.util.List;

import com.raitongorganicsfarm.app.mb.entity.Subscriber;

public interface SubscriberRepositoryCustom {
	void removeByCustomerNo(String customerNo);
	List<Subscriber> findAllOrderByCustomerNo();
	String nextCustomerNumber();
}
