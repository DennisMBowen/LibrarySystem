package groupEleven.controller;

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
	
	@PostMapping ({"/addPatron"})
	public String addNewPatron(@ModelAttribute Patron p, Model model) {
		patronRepo.save(p);
		return viewPatrons(model);
	}
	
	@GetMapping ("viewCheckedOut/{id}")
	public String viewCheckedOutBooks(@PathVariable("id") long id, Model model) {
		Patron p = patronRepo.findById(id).orElse(null);
		model.addAttribute("books", p.getCheckedOutBooks());
		return "results";
	}
	
	@GetMapping ("/checkOut/{id}")
	public String checkOutBooks(@PathVariable("id") long id, Model model) {
		Patron p = patronRepo.findById(id).orElse(null);
		model.addAttribute("patron", p);
		model.addAttribute("books", bookRepo.findAll());
		return "checkOutBook";
	}
	
	@GetMapping ("/checkOut/{id}/{bid}")
	public String checkBookOut(@PathVariable("id") long id, @PathVariable("bid") long bid, Model model) {
		Patron p = patronRepo.findById(id).orElse(null);
		Book b = bookRepo.findById(bid).orElse(null);
		p.checkOutBook(b);
		b.setPatron(p);
		patronRepo.save(p);
		bookRepo.save(b);
		return viewPatrons(model);
	}
}
