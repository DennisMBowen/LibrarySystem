package groupEleven.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Book {
	
	//Variables
	@Id
	@GeneratedValue
	private long id;
	private String title;
	private String author;
	private String isbn;
	private int quantity;
		
	//Default no-arg constructor
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	//Constructor using all fields except id
	public Book(String title, String author, String isbn, int quantity) {
		super();
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.quantity = quantity;
	}

	//Getters and setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	//To String method
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", isbn=" + isbn + ", quantity="
				+ quantity + "]";
	}

}
