// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.raitongorganicsfarm.app.mb.entity;

import com.raitongorganicsfarm.app.mb.entity.Subscription;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;

privileged aspect Subscription_Roo_Mongo_Entity {
    
    declare @type: Subscription: @Persistent;
    
    @Id
    private String Subscription.id;
    
    public String Subscription.getId() {
        return this.id;
    }
    
    public void Subscription.setId(String id) {
        this.id = id;
    }
    
}
