package lm.admin.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
public class MyUserDetails implements UserDetails {

    private long id ;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public MyUserDetails(Admin user) {
        this.id = user.getId() ;
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.firstName = user.getFirstName() ;
        this.lastName = user.getLastName();
        this.active = true;
        this.authorities = null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
