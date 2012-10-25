package com.raitongorganicsfarm.app.mb.entity;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooMongoEntity(identifierType = String.class)
public class Subscription {
	private int months;
	private Money expectedRevenue;
	private Money deliveryFee;
	private Date paidDate;
	private Date startingDate;
	private String note;
	private String doesNotEat;
}
