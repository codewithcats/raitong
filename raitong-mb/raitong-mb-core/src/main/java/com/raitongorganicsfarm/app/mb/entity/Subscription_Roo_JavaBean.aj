// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.raitongorganicsfarm.app.mb.entity;

import com.raitongorganicsfarm.app.mb.entity.Subscription;
import java.math.BigDecimal;
import java.util.Date;

privileged aspect Subscription_Roo_JavaBean {
    
    public int Subscription.getMonths() {
        return this.months;
    }
    
    public void Subscription.setMonths(int months) {
        this.months = months;
    }
    
    public BigDecimal Subscription.getExpectedRevenue() {
        return this.expectedRevenue;
    }
    
    public void Subscription.setExpectedRevenue(BigDecimal expectedRevenue) {
        this.expectedRevenue = expectedRevenue;
    }
    
    public BigDecimal Subscription.getDeliveryFee() {
        return this.deliveryFee;
    }
    
    public void Subscription.setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
    
    public Date Subscription.getPaidDate() {
        return this.paidDate;
    }
    
    public void Subscription.setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }
    
    public Date Subscription.getStartingDate() {
        return this.startingDate;
    }
    
    public void Subscription.setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }
    
    public String Subscription.getNote() {
        return this.note;
    }
    
    public void Subscription.setNote(String note) {
        this.note = note;
    }
    
}
