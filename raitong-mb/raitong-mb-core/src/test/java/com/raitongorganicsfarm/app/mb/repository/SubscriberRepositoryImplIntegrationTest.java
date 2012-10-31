package com.raitongorganicsfarm.app.mb.repository;

import static org.junit.Assert.*;

import java.util.List;
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
		subscriberRepository.deleteAll();
		Subscriber s = dod.getNewTransientSubscriber(new Random().nextInt());
		subscriberRepository.save(s);
		Subscriber result = subscriberRepository.findByCustomerNo(s
				.getCustomerNo());
		assertNotNull(result);
		subscriberRepository.removeByCustomerNo(s.getCustomerNo());
		result = subscriberRepository.findByCustomerNo(s.getCustomerNo());
		assertNull(result);
	}
	
	@Test
	public void testFindAllOrderByCustomerNo() {
		subscriberRepository.deleteAll();
		for(int i = 0; i < 10; i++)
			subscriberRepository.save(dod.getRandomSubscriber());
		List<Subscriber> list = subscriberRepository.findAllOrderByCustomerNo();
		for(int i = 0; i < list.size()-1; i++) {
			Subscriber x = list.get(i);
			Subscriber y = list.get(i+1);
			assertTrue(x.getCustomerNo().compareToIgnoreCase(y.getCustomerNo()) < 0);
		}
	}
	
}
