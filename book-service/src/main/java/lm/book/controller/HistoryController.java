package lm.book.controller;

import lm.book.model.History;
import lm.book.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/histories")
public class HistoryController {

    @Autowired
    private HistoryService historyService ;

    @GetMapping()
    public List<History> getAll() {
        return this.historyService.getAll();
    }

    @GetMapping("stats")
    public Object getStats() {
        return this.historyService.getBorrowedBookPerDay();
    }

}

