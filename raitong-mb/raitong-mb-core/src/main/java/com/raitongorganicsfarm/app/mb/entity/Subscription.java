package com.raitongorganicsfarm.app.mb.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooMongoEntity(identifierType = String.class)
public class Subscription {
	private int months;
	private BigDecimal expectedRevenue;
	private BigDecimal deliveryFee;
	private Date paidDate;
	private Date startingDate;
	private String note;

}
