package groupEleven.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import groupEleven.beans.Book;

@Configuration
public class BeanConfiguration {
	
	@Bean
	public Book book() {
		Book bean = new Book ();
		return bean;
	}

}
