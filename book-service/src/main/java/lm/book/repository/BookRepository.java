package lm.book.repository;

import lm.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByBorrowerUserId(Long id);
    List<Book> findAllByActive(boolean active) ;
}
