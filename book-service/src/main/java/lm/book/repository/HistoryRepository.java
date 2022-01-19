package lm.book.repository;

import lm.book.dto.BorrowedBookStat;
import lm.book.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    @Query("SELECT c.date, COUNT(c) FROM History AS c WHERE c.type = :type GROUP BY c.date ORDER BY c.date DESC")
    List<Object> findAllByType(String type) ;
}
