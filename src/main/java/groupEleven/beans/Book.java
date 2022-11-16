package groupEleven.beans;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Book {
	
	//Variables
	@Id
	@GeneratedValue
	private long id;
	private String title;
	private String author;
	private String isbn;
	private int quantity;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "patron", nullable = true)
	private Patron patron;
		
	//Constructor using all fields except id
	public Book(String title, String author, String isbn, int quantity) {
		super();
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.quantity = quantity;
	}

}
