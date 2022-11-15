package groupEleven.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import groupEleven.beans.Book;
import groupEleven.beans.User;
import groupEleven.repository.BookRepository;
import groupEleven.repository.UserRepository;

/**
 * @author alexh - aheinrichs
 * CIS175 - Fall 2022
 * Nov 14, 2022
 */
@Controller
public class UserWebController {
	@Autowired
	UserRepository userRepo;
	@Autowired
	BookRepository bookRepo;
	
	@GetMapping ({"/viewUsers"})
	public String viewUsers(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "userlist";
	}
	
	@GetMapping({"/addUser"})
	public String addNewContact(Model model) {
		User u = new User();
		model.addAttribute("newUser", u);
		return "userInput";
	}
	
	@PostMapping ({"/addUser"})
	public String addNewUser(@ModelAttribute User u, Model model) {
		userRepo.save(u);
		return viewUsers(model);
	}
	
	@GetMapping ("viewCheckedOut/{id}")
	public String viewCheckedOutBooks(@PathVariable("id") long id, Model model) {
		User u = userRepo.findById(id).orElse(null);
		model.addAttribute("books", u.getCheckedOutBooks());
		return "results";
	}
	
	@GetMapping ("/checkOut/{id}")
	public String checkOutBooks(@PathVariable("id") long id, Model model) {
		User u = userRepo.findById(id).orElse(null);
		model.addAttribute("user", u);
		model.addAttribute("books", bookRepo.findAll());
		return "checkOutBook";
	}
	
	@GetMapping ("/checkOut/{id}/{bid}")
	public String checkBookOut(@PathVariable("id") long id, @PathVariable("bid") long bid, Model model) {
		User u = userRepo.findById(id).orElse(null);
		Book b = bookRepo.findById(bid).orElse(null);
		u.checkOutBook(b);
		b.setUser(u);
		userRepo.save(u);
		bookRepo.save(b);
		return viewUsers(model);
	}
}
