package groupEleven.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import groupEleven.beans.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
