package groupEleven.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
 * @author alexh - aheinrichs
 * CIS175 - Fall 2022
 * Nov 14, 2022
 */
@Controller
public class PatronWebController {
	@Autowired
	PatronRepository patronRepo;
	@Autowired
	BookRepository bookRepo;
	
	@GetMapping ({"/viewPatrons"})
	public String viewPatrons(Model model) {
		model.addAttribute("patrons", patronRepo.findAll());
		return "patronlist";
	}
	
	@GetMapping({"/addPatron"})
	public String addNewPatron(Model model) {
		Patron p = new Patron();
		model.addAttribute("newPatron", p);
		return "patroninput";
	}
	
	@GetMapping("/editPatron/{id}")
	public String showUpdatePatron(@PathVariable("id") long id, Model model) {
		Patron p = patronRepo.findById(id).orElse(null);
		model.addAttribute("newPatron", p);
		return "editpatron";
	}
	
	@GetMapping("/deletePatron/{id}")
	public String deletePatron(@PathVariable("id") long id, Model model) {
		Patron p = patronRepo.findById(id).orElse(null);
		patronRepo.delete(p);
		return viewPatrons(model);
	}
	
	@PostMapping("/updatePatron/{id}")
	public String revisePatron(Patron p, Model model) {
		patronRepo.save(p);
		return viewPatrons(model);
	}
	
	@PostMapping ({"/addPatron"})
	public String addNewPatron(@ModelAttribute Patron p, Model model) {
		patronRepo.save(p);
		return viewPatrons(model);
	}
	
	@GetMapping ("viewCheckedOut/{id}")
	public String viewCheckedOutBooks(@PathVariable("id") long id, Model model) {
		Patron p = patronRepo.findById(id).orElse(null);
		model.addAttribute("patron", p);
		model.addAttribute("books", p.getCheckedOutBooks());
		return "checkedoutbooks";
	}
	
	@GetMapping ("/checkOut/{id}")
	public String checkOutBooks(@PathVariable("id") long id, Model model) {
		Patron p = patronRepo.findById(id).orElse(null);
		model.addAttribute("patron", p);
		List<Book> availableBooks = new ArrayList<Book>();
		List <Book> allBooks = new ArrayList<Book>();
		allBooks = (List<Book>) bookRepo.findAll();
		for (int i = 0; i < allBooks.size(); i++) {
			Book currentBook = allBooks.get(i);
			if(currentBook.getDueDate()== null){
				availableBooks.add(currentBook);
			}
		}
		model.addAttribute("books", availableBooks);
		return "checkOutBook";
	}
	
	@GetMapping ("/checkOut/{id}/{bid}")
	public String checkBookOut(@PathVariable("id") long id, @PathVariable("bid") long bid, Model model) {
		Patron p = patronRepo.findById(id).orElse(null);
		Book b = bookRepo.findById(bid).orElse(null);
		p.checkOutBook(b);
		b.setPatron(p);
		//sets dueDate on Book entity to 2 weeks from now()
		LocalDate dueDate = LocalDate.now().plusDays(14);
		b.setDueDate(dueDate);
		patronRepo.save(p);
		bookRepo.save(b);
		return viewPatrons(model);
	}
	
	@GetMapping ("/returnBook/{id}/{bid}")
	public String returnBook(@PathVariable("id") long id, @PathVariable("bid") long bid, Model model) {
		Patron p = patronRepo.findById(id).orElse(null);
		Book b = bookRepo.findById(bid).orElse(null);
		
		//Console print to test isOverdue book function
		if (b.isOverdue()) {
			System.out.println(b.getTitle() + " is overdue.");
		} else {
			System.out.println (b.getTitle() + " is not overdue");
		}
		
		p.returnBook(b);
		b.setPatron(null);
		b.setDueDate(null);
		bookRepo.save(b);
		return viewPatrons(model);
	}
}
