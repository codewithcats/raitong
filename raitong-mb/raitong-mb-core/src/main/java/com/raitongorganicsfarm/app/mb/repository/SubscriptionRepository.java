package com.raitongorganicsfarm.app.mb.repository;

import com.raitongorganicsfarm.app.mb.entity.Subscription;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Subscription.class)
public interface SubscriptionRepository {

    List<com.raitongorganicsfarm.app.mb.entity.Subscription> findAll();
}
