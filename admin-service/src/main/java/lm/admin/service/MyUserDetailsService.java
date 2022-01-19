package lm.admin.service;

import lm.admin.model.Admin;
import lm.admin.model.MyUserDetails;
import lm.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository ;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepository.findByUsername(username);
        admin.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
        return admin.map(MyUserDetails::new).get();
    }

}