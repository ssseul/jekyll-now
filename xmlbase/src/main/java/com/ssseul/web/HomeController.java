package com.ssseul.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Handles requests for the application home page.
 */
/*
public class HomeController implements Controller{
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
    @Override
    public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {

    	logger.info("Welcome home! The client locale is {}.");
    	
    	ModelAndView mav = new ModelAndView();
        mav.addObject("message", "동호야 수고했어!!!" );
        mav.setViewName("home");
        
        return mav;
    }
}
*/
public class HomeController extends AbstractController{
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
    @Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
    	logger.info("Welcome home! The client locale is {}.");
    	
    	ModelAndView mav = new ModelAndView();
        mav.addObject("message", "동호야 수고했어!!!" );
        mav.setViewName("home");
        
        return mav;
	}
}