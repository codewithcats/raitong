package com.raitongorganicsfarm.app.mb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.raitongorganicsfarm.app.mb.entity.Subscriber;
import com.raitongorganicsfarm.app.mb.repository.SubscriberRepository;

@Controller
public class SubscriberController {

	private SubscriberRepository subscriberRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/subscribers/**")
	public ResponseEntity<String> create(@ModelAttribute Subscriber subscriber) {
		subscriber = this.subscriberRepository.save(subscriber);
		String j = subscriber.toJson();
		ResponseEntity<String> response = new ResponseEntity<String>(j, HttpStatus.CREATED);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/subscribers")
	public ResponseEntity<String> list() {
		List<Subscriber> subscribers = this.subscriberRepository.findAll();
		String s = Subscriber.toJsonArray(subscribers);
		ResponseEntity<String> response = new ResponseEntity<String>(s, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/subscribers/{customerNo}")
	public ModelAndView info(@PathVariable String customerNo) {
		Subscriber subscriber = this.subscriberRepository.findByCustomerNo(customerNo);
		ModelAndView mv = new ModelAndView("subscribers-info");
		mv.addObject("subscriber", subscriber);
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/subscribers/{customerNo}")
	public ResponseEntity<String> delete(@PathVariable String customerNo) {
		this.subscriberRepository.removeByCustomerNo(customerNo);
		ResponseEntity<String> res  = new ResponseEntity<String>(HttpStatus.OK);
		return res;
	}
	
	@Autowired
	public void setSubscriberRepository(
			SubscriberRepository subscriberRepository) {
		this.subscriberRepository = subscriberRepository;
	}

}
