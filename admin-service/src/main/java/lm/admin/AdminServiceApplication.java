package lm.admin;

import lm.admin.model.Admin;
import lm.admin.repository.AdminRepository;
import lm.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AdminServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdminServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(AdminService adminService , AdminRepository adminRepository) {
		return args -> {
			Admin admin = adminRepository.findByUsername("yoyo").orElse(null);
			if(admin == null) adminService.create(new Admin(1L,"Eya", "Bk","yoyo", "123456"));
		};
	}
}

@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService myUserDetailsService;


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests().antMatchers("/api/admins/login" ).permitAll();
//				anyRequest().authenticated().and().exceptionHandling();
	}

}