package com.raitongorganicsfarm.app.mb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private SubscriberRepository subscriberRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/subscribers/**")
	public ModelAndView create(@ModelAttribute Subscriber subscriber) {
		subscriber = this.subscriberRepository.save(subscriber);
		ModelAndView mv = new ModelAndView("subscribers-view");
		mv.addObject("subscriber", subscriber);
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/subscribers")
	public ModelAndView list() {
		List<Subscriber> subscribers = this.subscriberRepository.findAll();
		ModelAndView mv = new ModelAndView("subscribers-list");
		mv.addObject("subscribers", subscribers);
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/subscribers/{customerNo}")
	public ModelAndView info(@PathVariable String customerNo) {
		Subscriber subscriber = this.subscriberRepository.findByCustomerNo(customerNo);
		ModelAndView mv = new ModelAndView("subscribers-info");
		mv.addObject("subscriber", subscriber);
		return mv;
	}

}
