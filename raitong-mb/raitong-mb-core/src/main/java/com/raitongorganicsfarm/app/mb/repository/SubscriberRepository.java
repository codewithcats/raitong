package com.raitongorganicsfarm.app.mb.repository;

import com.raitongorganicsfarm.app.mb.entity.Subscriber;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Subscriber.class)
public interface SubscriberRepository extends SubscriberRepositoryCustom {
	
	List<com.raitongorganicsfarm.app.mb.entity.Subscriber> findAll();

	Subscriber findByCustomerNo(String customerNo);
}
