package lm.user.controller;

import lm.user.dto.AuthenticationRequest;
import lm.user.model.User;
import lm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService ;

    @GetMapping()
    public List<User> getAll() {
        return this.userService.getAll();
    }

    @GetMapping("{id}")
    public User getOne(@PathVariable Long id) {
        return this.userService.getOne(id);
    }

    @PostMapping()
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping()
    public User update(@RequestBody User user) {
        return this.userService.update(user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return userService.login(authenticationRequest);
    }
}
