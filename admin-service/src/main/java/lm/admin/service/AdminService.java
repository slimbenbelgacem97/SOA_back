package lm.admin.service;

import lm.admin.dto.AuthenticationRequest;
import lm.admin.dto.AuthenticationResponse;
import lm.admin.model.Admin;
import lm.admin.model.MyUserDetails;
import lm.admin.repository.AdminRepository;
import lm.admin.util.JwtUtil;
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
public class AdminService {

    @Autowired
    private AdminRepository adminRepository ;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;


    public List<Admin> getAll() {
       return adminRepository.findAll() ;
    }

    public Admin getOne(Long id) {
        return adminRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public Admin create(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin) ;
    }

    public Admin update(Admin admin) {
        Admin toUpdateAdmin = getOne(admin.getId()) ;
        toUpdateAdmin.setFirstName(admin.getFirstName());
        toUpdateAdmin.setLastName(admin.getLastName());
        toUpdateAdmin.setUsername(admin.getUsername());
        return adminRepository.save(toUpdateAdmin) ;
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
        adminRepository.deleteById(id);
    }
}
