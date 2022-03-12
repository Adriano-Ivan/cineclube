package br.com.cineclube.cineclube.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppController {

	@RequestMapping()
	public String index() {
		return "index.html";
	}
}
