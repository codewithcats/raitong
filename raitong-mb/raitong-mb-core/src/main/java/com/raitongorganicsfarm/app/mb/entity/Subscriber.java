package com.raitongorganicsfarm.app.mb.entity;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooMongoEntity(identifierType = String.class)
public class Subscriber {
	private String customerNo;
	private String name;
	private SubscriberGender gender;
	private String nationality;
	private String phone;
	private String email;
	private String address;
	private String note;
	private Date birthday;
	private String referee;
}
