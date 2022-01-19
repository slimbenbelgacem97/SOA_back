package lm.book.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name ;
    private String description ;
    private String author ;
    private String publishingDate ;
    private String image ;
    private boolean active ;
    @ManyToOne
    private User borrowerUser ;
}
