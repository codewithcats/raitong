package com.raitongorganicsfarm.app.mb.entity;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = Subscriber.class)
public class SubscriberDataOnDemand {

	public void setCustomerNo(Subscriber obj, int index) {
        String customerNo = String.format("CSA%1$04d", index);
        obj.setCustomerNo(customerNo);
    }
}
