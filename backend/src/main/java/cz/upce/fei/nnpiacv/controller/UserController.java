package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService greetingService) {
        this.userService = greetingService;
    }

    @GetMapping("/user")
    public User findUserById(@RequestParam Long id) {
        return userService.findUserById(id);
    }
    @GetMapping("/user/{id}")
    public User findUserByPath(@PathVariable Long id) {
        return userService.findUserById(id);
    }
    @GetMapping("/users")
    public Collection<User> findUsers() {
        return userService.findUsers();
    }


}
