package com.yoon.finalexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class YoonFinalExamController {
	
	@GetMapping("/index")
	public String getHello(Model model) {
		model.addAttribute("name", "han");
		return "index.html";
	}

}