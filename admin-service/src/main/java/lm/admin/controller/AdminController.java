package lm.admin.controller;

import lm.admin.dto.AuthenticationRequest;
import lm.admin.model.Admin;
import lm.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService ;

    @GetMapping()
    public List<Admin> getAll() {
        return this.adminService.getAll();
    }

    @GetMapping("{id}")
    public Admin getOne(@PathVariable Long id) {
        return this.adminService.getOne(id);
    }

    @PostMapping()
    public Admin create(@RequestBody Admin admin) {
        return adminService.create(admin);
    }

    @PutMapping()
    public Admin update(@RequestBody Admin admin) {
        return this.adminService.update(admin);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        adminService.delete(id);
    }

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return adminService.login(authenticationRequest);
    }

}


