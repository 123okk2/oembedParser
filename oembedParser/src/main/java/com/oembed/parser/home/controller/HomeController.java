package com.oembed.parser.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oembed.parser.oembed.service.OembedService;

@Controller
public class HomeController {
	
	@Autowired OembedService oembedService;

	@GetMapping("/")
	public ModelAndView homepage() {
		
		ModelAndView homePage = new ModelAndView("home");
		homePage.addObject("sourceList", oembedService.getOembedSourceList());
		
		return homePage;
	}

}
