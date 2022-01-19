package lm.book.service;

import lm.book.dto.BorrowRequest;
import lm.book.model.Book;
import lm.book.model.History;
import lm.book.model.User;
import lm.book.repository.BookRepository;
import lm.book.repository.UserRepository;
import lm.book.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository ;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private UploadUtil uploadUtil ;

    @Autowired
    private HistoryService historyService ;


    public List<Book> getAll() {
       return bookRepository.findAllByActive(true) ;
    }

    public Book getOne(Long id) {
        return bookRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public Book create(Book book, MultipartFile image) {
        String newFileName = uploadUtil.upload(image);
        book.setImage("images/" + newFileName);
        return bookRepository.save(book) ;
    }

    public Book update(Book book, MultipartFile image) {
        Book toUpdateAdmin = getOne(book.getId()) ;
        if(image != null ){
            String newFileName = uploadUtil.upload(image);
            toUpdateAdmin.setImage("images/" + newFileName);
        }
        toUpdateAdmin.setName(book.getName());
        toUpdateAdmin.setDescription(book.getDescription());
        toUpdateAdmin.setAuthor(book.getAuthor());
        toUpdateAdmin.setPublishingDate(book.getPublishingDate());
        return bookRepository.save(toUpdateAdmin) ;
    }

    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(IllegalArgumentException::new) ;
        book.setActive(false);
        bookRepository.save(book);
    }

    public List<Book> getByUser(Long id) {
        return bookRepository.findAllByBorrowerUserId(id) ;
    }

    public Book borrow(BorrowRequest request) {
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(request.getUserId()).orElseThrow(IllegalArgumentException::new);
        book.setBorrowerUser(user);
        historyService.create(new History(null , LocalDate.now().toString(), "BORROW" , user , book ));
        return bookRepository.save(book);
    }

    public Book returnBook(BorrowRequest request) {
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(IllegalArgumentException::new);
        User user = userRepository.findById(request.getUserId()).orElseThrow(IllegalArgumentException::new);
        book.setBorrowerUser(null);
        historyService.create(new History(null , LocalDate.now().toString(), "RETURN" , user , book ));
        return bookRepository.save(book);
    }
}
