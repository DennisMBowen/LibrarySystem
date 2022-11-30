package groupEleven.beans;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "patron", nullable = true)
	private Patron patron;
	
	private LocalDate dueDate;
		
	//Constructor using all fields except id
	public Book(String title, String author, String isbn) {
		super();
		this.title = title;
		this.author = author;
		this.isbn = isbn;
	}
	
	public Book(String title, String author, String isbn, Patron patron, LocalDate dueDate) {
		super();
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.patron = patron;
		this.dueDate = dueDate;
	}
	
	public boolean isOverdue() {
		LocalDate today = LocalDate.now();
		if(this.dueDate == null) {
			return false;
		} else if(this.dueDate.isBefore(today)) {
			return true;
		} else {
			return false;
		}
	}

}
