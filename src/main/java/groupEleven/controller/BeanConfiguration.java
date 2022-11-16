package groupEleven.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import groupEleven.beans.Book;
import groupEleven.beans.User;

@Configuration
public class BeanConfiguration {
	
	@Bean
	public Book book() {
		Book bean = new Book ();
		return bean;
	}
	
	@Bean
	public User user() {
		User bean = new User();
		return bean;
	}

}
