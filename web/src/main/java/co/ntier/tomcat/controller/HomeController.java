package co.ntier.tomcat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value="/")
	public ModelAndView getHome(){
		return new ModelAndView("home");
	}
	
	@RequestMapping(value="/secure/test")
	public ModelAndView getMembersArea(){
		return new ModelAndView("test");
	}
	
	@RequestMapping(value="/login")
	public ModelAndView getLogin(){
		return new ModelAndView("login");
	}
}
