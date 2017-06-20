package com.ssseul.cmmn;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
public class ServletContextConfig extends WebMvcConfigurerAdapter{

	@Resource
    private Environment env;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	
	@Bean
    public InternalResourceViewResolver jstlViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setOrder(3);
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    
    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver(){
    	FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
    	viewResolver.setOrder(1);
    	viewResolver.setCache(true);
    	viewResolver.setSuffix(".html");
    	return viewResolver;
    }
    
    @Bean
    public FreeMarkerConfigurer freeMarkerConfig(){	
    	FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
    	freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/views/");
    	return freeMarkerConfigurer;
    }
}
