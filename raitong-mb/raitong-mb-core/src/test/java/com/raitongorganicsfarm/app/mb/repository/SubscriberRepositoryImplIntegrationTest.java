package com.raitongorganicsfarm.app.mb.repository;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.raitongorganicsfarm.app.mb.entity.Subscriber;
import com.raitongorganicsfarm.app.mb.entity.SubscriberDataOnDemand;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class SubscriberRepositoryImplIntegrationTest {
	@Autowired
	private SubscriberDataOnDemand dod;
	@Autowired
	private SubscriberRepository subscriberRepository;

	@Test
	public void testRemoveByCustomerNo() {
		Subscriber s = dod.getNewTransientSubscriber(new Random().nextInt());
		subscriberRepository.save(s);
		Subscriber result = subscriberRepository.findByCustomerNo(s
				.getCustomerNo());
		assertNotNull(result);
		subscriberRepository.removeByCustomerNo(s.getCustomerNo());
		result = subscriberRepository.findByCustomerNo(s.getCustomerNo());
		assertNull(result);
	}
}
