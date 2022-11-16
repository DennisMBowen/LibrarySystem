package groupEleven.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import groupEleven.beans.Book;
import groupEleven.beans.Patron;

@Configuration
public class BeanConfiguration {
	
	@Bean
	public Book book() {
		Book bean = new Book ();
		return bean;
	}
	
	@Bean
	public Patron user() {
		Patron bean = new Patron();
		return bean;
	}

}
