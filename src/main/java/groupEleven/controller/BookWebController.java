package groupEleven.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
import groupEleven.beans.Patron;
import groupEleven.repository.BookRepository;
import groupEleven.repository.PatronRepository;

/**
 * @author group eleven
 * CIS175-Fall 2022
 * November 13, 2022
 */

@Controller
public class BookWebController {
	@Autowired
	BookRepository bookRepo;
	@Autowired
	PatronRepository patronRepo;
	
	@GetMapping({"/viewAll"})
	public String viewAllBooks(Model model) {
		//if(repo.findAll().isEmpty()) {
			//return addNewBook(model);
		//}
		
		List<Book> books = bookRepo.findAll();
		// This line sorts by title
		Collections.sort(books, (b1, b2) -> (b1.getTitle().compareTo(b2.getTitle())));
		
		model.addAttribute("books", books);
		return "booklist";
	}
	
	
	@GetMapping({"/viewAllByAuthor"})
	public String viewAllByAuthor(Model model) {
		List<Book> books = bookRepo.findAll();
		Collections.sort(books, (b1, b2) -> (b1.getAuthor().compareTo((b2.getAuthor()))));
		
		model.addAttribute("books", books);
		return "booklist";
	}
	
	@GetMapping({"/viewAllByID"})
	public String viewAllByID(Model model) {
		List<Book> books = bookRepo.findAll();
	
		
		model.addAttribute("books", books);
		return "booklist";
	}
	
	@GetMapping("/viewAvailable")
	public String viewAvailableBooks(Model model) {
		List<Book> availableBooks = new ArrayList<Book>();
		List <Book> allBooks = new ArrayList<Book>();
		allBooks = (List<Book>) bookRepo.findAll();
		for (int i = 0; i < allBooks.size(); i++) {
			Book currentBook = allBooks.get(i);
			if(currentBook.getPatron()== null){
				availableBooks.add(currentBook);
			}
		}
		model.addAttribute("books", availableBooks);
		return "checkedInBooks";
	}

	@GetMapping("/viewOverdue")
	public String viewOverdueBooks(Model model) {
		List<Book> overdueBooks = new ArrayList<Book>();
		List <Book> allBooks = new ArrayList<Book>();
		allBooks = (List<Book>) bookRepo.findAll();
		for (int i = 0; i < allBooks.size(); i++) {
			Book currentBook = allBooks.get(i);
			if(currentBook.isOverdue() == true){
				overdueBooks.add(currentBook);
			}
		}
		model.addAttribute("books", overdueBooks);
		return "overduebooks";
	}
	
	@GetMapping("/addSample")
	public String addSampleBooks(Model model) {
		Patron p = new Patron("aheinrichs", "root", "Alex", "Heinrichs", null);
		Patron p2 = new Patron("jhill", "yeet", "Joe", "Hill", null);
		Patron p3 = new Patron("glanphier", "abc123", "Gabe", "Lanphier", null);
		patronRepo.save(p);
		patronRepo.save(p2);
		patronRepo.save(p3);
		Book b = new Book("The Godfather", "Mario Puzo", "9780593542590", null, LocalDate.of(2022, 11, 29));
		b.setPatron(p);
		p.checkOutBook(b);
		bookRepo.save(b);
		b = new Book("Moby Dick", "Herman Melville", "9781566192637", null, LocalDate.of(2022, 12, 2));
		b.setPatron(p);
		p.checkOutBook(b);
		bookRepo.save(b);
		b = new Book("House of Leaves", "Mark Danielewski", "9780375407321");
		bookRepo.save(b);
		b = new Book("Great Expectations", "Charles Dickens", "9780582330887", null, LocalDate.of(1980, 6, 12));
		b.setPatron(p2);
		p2.checkOutBook(b);
		bookRepo.save(b);
		b = new Book("Haunting of Hill House", "Shirley Jackson", "9788477026211", null, LocalDate.of(2022, 11, 25));
		b.setPatron(p);
		p.checkOutBook(b);
		bookRepo.save(b);
		b = new Book("1984", "George Orwell", "9780151660346");
		bookRepo.save(b);
		patronRepo.save(p);
		patronRepo.save(p2);
		return viewAllBooks(model);
	}

	@GetMapping("/edit/{id}")
	public String showUpdateBook(@PathVariable("id") long id, @DateTimeFormat(pattern="yyyy-MM-dd") Model model) {
		Book b = bookRepo.findById(id).orElse(null);
		model.addAttribute("newBook", b);
		return "edit";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable("id") long id, Model model) {
		Book b = bookRepo.findById(id).orElse(null);
		bookRepo.delete(b);
		return viewAllBooks(model);
	}
	
	@PostMapping("/update/{id}")
	public String reviseBook(Book b, @DateTimeFormat(pattern="yyyy-MM-dd") Model model) {
		bookRepo.save(b);
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
		bookRepo.save(b);
		return viewAllBooks(model);
	}
	
	
}
