package lm.user.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Userr")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String firstName ;
    private String lastName ;
    private String level ;
    private boolean active ;
}
