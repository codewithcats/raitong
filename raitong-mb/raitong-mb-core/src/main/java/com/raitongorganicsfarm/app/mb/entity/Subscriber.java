package com.raitongorganicsfarm.app.mb.entity;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJson
@RooMongoEntity(identifierType = String.class)
public class Subscriber {
	@NotNull
	private String customerNo;
	@NotNull
	private String name;
	private SubscriberGender gender;
	private String nationality;
	@NotNull
	private String address;
	@NotNull
	private String phone;
	private String email;
	private String note;
	private Date birthday;
	private String referee;
	@DBRef
	private List<Subscription> subscriptions;
	private Date createdDate;
	private String doesNotEat;
	
	public boolean addSubscriptions(Subscription subscription) {
		if(this.subscriptions == null) this.subscriptions = new LinkedList<Subscription>();
		return this.subscriptions.add(subscription);
	}

	public static Subscriber fromJsonToSubscriber(String json) {
		return new JSONDeserializer<Subscriber>()
				.use(null, Subscriber.class).deserialize(json);
	}

	public String toJson() {
        return new JSONSerializer().include("subscriptions").exclude("*.class").serialize(this);
    }

	public static String toJsonArray(Collection<Subscriber> collection) {
        return new JSONSerializer().include("subscriptions").exclude("*.class").serialize(collection);
    }
}
