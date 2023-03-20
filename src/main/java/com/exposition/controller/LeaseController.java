package com.exposition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/lease")
public class LeaseController {

	@GetMapping(value="/")
	public String Lease() {
		return "lease/lease";
	}
	
	@GetMapping(value="/calendar")
	   public String caldendar() {
	      return "lease/calendar";
	   }
}
