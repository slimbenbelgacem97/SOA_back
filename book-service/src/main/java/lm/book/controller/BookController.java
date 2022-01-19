package lm.book.controller;

import lm.book.dto.BorrowRequest;
import lm.book.model.Book;
import lm.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {

    @Autowired
    private BookService bookService ;

    @GetMapping()
    public List<Book> getAll() {
        return this.bookService.getAll();
    }

    @GetMapping("user/{id}")
    public List<Book> getByUser(@PathVariable Long id) {
        return this.bookService.getByUser(id);
    }

    @GetMapping("{id}")
    public Book getOne(@PathVariable Long id) {
        return this.bookService.getOne(id);
    }

    @PostMapping()
    public Book create(@RequestPart(value = "image", required = false) MultipartFile image ,
                       @RequestPart("book") Book book) {
        return bookService.create(book ,image);
    }

    @PutMapping()
    public Book update(@RequestPart(value = "image", required = false) MultipartFile image ,
                       @RequestPart("book") Book book) {
        return this.bookService.update(book ,image);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PostMapping("/borrow")
    public Book borrow(@RequestBody BorrowRequest request) {
        return  bookService.borrow(request);
    }

    @PostMapping("/return")
    public Book returnBook(@RequestBody BorrowRequest request) {
        return  bookService.returnBook(request);
    }


}


