package br.com.cineclube.cineclube.config.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.cineclube.cineclube.util.mvc.GenderStringToEnumConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new GenderStringToEnumConverter());
	}
}
