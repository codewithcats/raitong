package com.raitongorganicsfarm.app.mb.entity;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = Subscriber.class, transactional = false)
public class SubscriberIntegrationTest {

    @Test
    public void testFindByCustomerNo() {
    	Subscriber subscriber = dod.getNewTransientSubscriber(new Random().nextInt());
    	String customerNo = subscriber.getCustomerNo();
    	subscriberRepository.save(subscriber);
    	subscriber = subscriberRepository.findByCustomerNo(customerNo);
    	assertNotNull(subscriber);
    	assertEquals(customerNo, subscriber.getCustomerNo());
    }
    

	@Autowired
    private SubscriberDataOnDemand dod;
}
