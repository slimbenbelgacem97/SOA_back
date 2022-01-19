package lm.book.service;

import lm.book.model.History;
import lm.book.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository ;

    public List<History> getAll() {
        return historyRepository.findAll() ;
    }

    public History create(History book) {
        return historyRepository.save(book) ;
    }

    public Object getBorrowedBookPerDay(){
        return historyRepository.findAllByType("BORROW") ;
    }
}
