package groupEleven.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
public class BookWebController {
	@Autowired
	BookRepository repo;
	
	@GetMapping({"/viewAll"})
	public String viewAllBooks(Model model) {
		//if(repo.findAll().isEmpty()) {
			//return addNewBook(model);
		//}
		model.addAttribute("books", repo.findAll());
		return "results";
	}
	
	@GetMapping({"/viewAvailable"})
	public String viewAvailableBooks (Model model) {
		List<Book> availableBooks = new ArrayList<Book>();
		List <Book> allBooks = new ArrayList<Book>();
		allBooks = (List<Book>) repo.findAll();
		for (int i = 0; i < allBooks.size(); i++) {
			Book currentBook = allBooks.get(i);
			if(currentBook.getDueDate()== null){
				availableBooks.add(currentBook);
			}
		}
		model.addAttribute("books", availableBooks);
		return "checkedInBooks";
	}

	
	@GetMapping("/addSample")
	public String addSampleBooks(Model model) {
		Book b = new Book("The Godfather", "Mario Puzo", "9780593542590");
		repo.save(b);
		b = new Book("Moby Dick", "Herman Melville", "9781566192637");
		repo.save(b);
		b = new Book("Great Expectations", "Charles Dickens", "9780582330887");
		repo.save(b);
		return viewAllBooks(model);
	}

	@GetMapping("/edit/{id}")
	public String showUpdateBook(@PathVariable("id") long id, @DateTimeFormat(pattern="yyyy-MM-dd") Model model) {
		Book b = repo.findById(id).orElse(null);
		model.addAttribute("newBook", b);
		return "edit";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable("id") long id, Model model) {
		Book b = repo.findById(id).orElse(null);
		repo.delete(b);
		return viewAllBooks(model);
	}
	
	@PostMapping("/update/{id}")
	public String reviseBook(Book b, @DateTimeFormat(pattern="yyyy-MM-dd") Model model) {
		repo.save(b);
		return viewAllBooks(model);
	}

	
	@GetMapping("/addBook")
	  public String addNewBook(Model model) {
		Book b = new Book();
		model.addAttribute("newBook", b);
		return "add";
	}
	
	@PostMapping("/addBook")
	  public String addNewBook(@ModelAttribute Book b, Model model) {
		repo.save(b);
		return viewAllBooks(model);
	}
	
	
}
