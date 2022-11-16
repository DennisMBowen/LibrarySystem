package groupEleven.beans;

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
public class User {
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
	
	public void checkOutBook(Book b) {
		this.checkedOutBooks.add(b);
	}
}
