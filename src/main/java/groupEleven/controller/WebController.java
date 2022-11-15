package groupEleven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import groupEleven.beans.Book;
import groupEleven.repository.BookRepository;

/**
 * @author group eleven
 * CIS175-Fall 2022
 * November 13, 2022
 */

@Controller
public class WebController {
	@Autowired
	BookRepository repo;
	
	@GetMapping({"/", "viewAll"})
	public String viewAllBooks(Model model) {
		//if(repo.findAll().isEmpty()) {
			//return addNewBook(model);
		//}
		model.addAttribute("books", repo.findAll());
		return "results";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateBook(@PathVariable("id") long id, Model model) {
		Book b = repo.findById(id).orElse(null);
		model.addAttribute("newBook", b);
		return "edit";
	}
	
	@PostMapping("/update/{id}")
	public String reviseBook(Book b, Model model) {
		repo.save(b);
		return viewAllBooks(model);
	}
	
}
