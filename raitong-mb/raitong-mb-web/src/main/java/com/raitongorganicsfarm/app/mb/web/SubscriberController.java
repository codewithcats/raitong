package com.raitongorganicsfarm.app.mb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public ModelAndView save(@ModelAttribute Subscriber subscriber) {
		subscriber = subscriberRepository.save(subscriber);
		ModelAndView mv = new ModelAndView("subscriberView");
		mv.addObject("subscriber", subscriber);
		return mv;
	}
}
