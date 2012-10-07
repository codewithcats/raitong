package com.raitongorganicsfarm.app.mb.entity;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooMongoEntity(identifierType = String.class)
public class Subscriber {
	private String customerNo;
	private String name;
	private String address;
	private String phone;
}
