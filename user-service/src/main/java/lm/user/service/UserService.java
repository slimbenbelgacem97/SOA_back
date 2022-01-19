package lm.user.service;

import lm.user.dto.AuthenticationRequest;
import lm.user.dto.AuthenticationResponse;
import lm.user.model.MyUserDetails;
import lm.user.model.User;
import lm.user.repository.UserRepository;
import lm.user.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;


    public List<User> getAll() {
        return userRepository.findAllByActive(true) ;
    }

    public User getOne(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user) ;
    }

    public User update(User user) {
        User toUpdateUser = getOne(user.getId()) ;
        toUpdateUser.setFirstName(user.getFirstName());
        toUpdateUser.setLastName(user.getLastName());
        toUpdateUser.setUsername(user.getUsername());
        toUpdateUser.setLevel(user.getLevel());
        toUpdateUser.setActive(true);
        return userRepository.save(toUpdateUser) ;
    }

    public ResponseEntity login(AuthenticationRequest request) throws Exception {
        try {
            Authentication a = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        }
        catch (BadCredentialsException e) { throw new Exception("Incorrect username or password", e); }
        final MyUserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token , userDetails));
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new) ;
        user.setActive(false);
        userRepository.save(user);
    }

}
