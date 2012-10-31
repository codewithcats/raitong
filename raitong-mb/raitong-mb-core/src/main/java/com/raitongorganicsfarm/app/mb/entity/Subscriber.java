package com.raitongorganicsfarm.app.mb.entity;

import java.util.Date;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONDeserializer;
import flexjson.transformer.DateTransformer;

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
	private List<Subscription> subscriptions;
	private Date createdDate;

	public static Subscriber fromJsonToSubscriber(String json) {
		return new JSONDeserializer<Subscriber>()
				.use(Date.class, new DateTransformer("ddMMyyyy"))
				.use(null, Subscriber.class).deserialize(json);
	}
}
