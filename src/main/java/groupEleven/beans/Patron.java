package groupEleven.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author group eleven
 * CIS175 - Fall 2022
 * Nov 14, 2022
 */
@Data
@Entity
@NoArgsConstructor
public class Patron {
	@Id
	@GeneratedValue
	private long id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	
	@OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
	@JoinColumn(name = "books", nullable = true)
	private List<Book> checkedOutBooks;
	
	public Patron(String userName, String password, String firstName, String lastName, List<Book> checkedOutBooks) {
		super();
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		if(checkedOutBooks == null) {
			this.checkedOutBooks = new ArrayList<Book>();
		} else {
			this.checkedOutBooks = checkedOutBooks;
		}
	}
	
	public void checkOutBook(Book b) {
		this.checkedOutBooks.add(b);
	}
	
	public void returnBook(Book b) {
		this.checkedOutBooks.remove(b);
	}
	
	public boolean hasOverdueBooks() {
		if(this.getCheckedOutBooks().isEmpty()) {
			return false;
		}
		for (int i = 0; i < this.getCheckedOutBooks().size(); i++) {
			Book currentBook = this.getCheckedOutBooks().get(i);
			if(currentBook.isOverdue()) {
				return true;
			}
		}
		return false;
	}

}
