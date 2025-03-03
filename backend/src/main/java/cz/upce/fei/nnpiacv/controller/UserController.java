package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.dto.UserRequestDto;
import cz.upce.fei.nnpiacv.dto.UserResponseDto;
import cz.upce.fei.nnpiacv.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user/{id}")
    public User findUser(@PathVariable("id") Long id) {
        return userService.findUser(id);
    }


    @PostMapping("api/v1/users")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto user) {
        User createdUser = userService.createUser(new User(user.getEmail(), user.getPassword()));
        /*return ResponseEntity.status(201).body(
                new UserResponseDto(createdUser.getId(), createdUser.getEmail(), createdUser.getPassword()));
        */
        return ResponseEntity.status(201).body(
                UserResponseDto.builder().id(createdUser.getId()).password(createdUser.getPassword()).email(createdUser.getEmail()).build());
    }

    @GetMapping("/users")
    public Collection<? extends Object> findUsers(@RequestParam(required = false) String email) {
        if (email != null) {
            return userService.findUsers();
        } else {
            Optional<User> user = userService.findbyEmail(email);
            if (user == null) {
                return Collections.emptyList();
            } else {
                return Collections.singletonList(user);
            }
        }
    }
}
