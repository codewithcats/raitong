// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.raitongorganicsfarm.app.mb.entity;

import com.raitongorganicsfarm.app.mb.entity.Subscriber;
import com.raitongorganicsfarm.app.mb.entity.SubscriberDataOnDemand;
import com.raitongorganicsfarm.app.mb.entity.SubscriberGender;
import com.raitongorganicsfarm.app.mb.repository.SubscriberRepository;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect SubscriberDataOnDemand_Roo_DataOnDemand {
    
    declare @type: SubscriberDataOnDemand: @Component;
    
    private Random SubscriberDataOnDemand.rnd = new SecureRandom();
    
    private List<Subscriber> SubscriberDataOnDemand.data;
    
    @Autowired
    SubscriberRepository SubscriberDataOnDemand.subscriberRepository;
    
    public Subscriber SubscriberDataOnDemand.getNewTransientSubscriber(int index) {
        Subscriber obj = new Subscriber();
        setAddress(obj, index);
        setBirthday(obj, index);
        setCreatedDate(obj, index);
        setCustomerNo(obj, index);
        setEmail(obj, index);
        setGender(obj, index);
        setName(obj, index);
        setNationality(obj, index);
        setNote(obj, index);
        setPhone(obj, index);
        setReferee(obj, index);
        return obj;
    }
    
    public void SubscriberDataOnDemand.setAddress(Subscriber obj, int index) {
        String address = "address_" + index;
        obj.setAddress(address);
    }
    
    public void SubscriberDataOnDemand.setBirthday(Subscriber obj, int index) {
        Date birthday = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setBirthday(birthday);
    }
    
    public void SubscriberDataOnDemand.setCreatedDate(Subscriber obj, int index) {
        Date createdDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setCreatedDate(createdDate);
    }
    
    public void SubscriberDataOnDemand.setCustomerNo(Subscriber obj, int index) {
        String customerNo = "customerNo_" + index;
        obj.setCustomerNo(customerNo);
    }
    
    public void SubscriberDataOnDemand.setEmail(Subscriber obj, int index) {
        String email = "foo" + index + "@bar.com";
        obj.setEmail(email);
    }
    
    public void SubscriberDataOnDemand.setGender(Subscriber obj, int index) {
        SubscriberGender gender = null;
        obj.setGender(gender);
    }
    
    public void SubscriberDataOnDemand.setName(Subscriber obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }
    
    public void SubscriberDataOnDemand.setNationality(Subscriber obj, int index) {
        String nationality = "nationality_" + index;
        obj.setNationality(nationality);
    }
    
    public void SubscriberDataOnDemand.setNote(Subscriber obj, int index) {
        String note = "note_" + index;
        obj.setNote(note);
    }
    
    public void SubscriberDataOnDemand.setPhone(Subscriber obj, int index) {
        String phone = "phone_" + index;
        obj.setPhone(phone);
    }
    
    public void SubscriberDataOnDemand.setReferee(Subscriber obj, int index) {
        String referee = "referee_" + index;
        obj.setReferee(referee);
    }
    
    public Subscriber SubscriberDataOnDemand.getSpecificSubscriber(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Subscriber obj = data.get(index);
        String id = obj.getId();
        return subscriberRepository.findOne(id);
    }
    
    public Subscriber SubscriberDataOnDemand.getRandomSubscriber() {
        init();
        Subscriber obj = data.get(rnd.nextInt(data.size()));
        String id = obj.getId();
        return subscriberRepository.findOne(id);
    }
    
    public boolean SubscriberDataOnDemand.modifySubscriber(Subscriber obj) {
        return false;
    }
    
    public void SubscriberDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = subscriberRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Subscriber' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Subscriber>();
        for (int i = 0; i < 10; i++) {
            Subscriber obj = getNewTransientSubscriber(i);
            try {
                subscriberRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            data.add(obj);
        }
    }
    
}
