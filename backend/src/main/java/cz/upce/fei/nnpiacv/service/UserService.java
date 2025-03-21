package cz.upce.fei.nnpiacv.service;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.repository.UserRepository;

import exception.UserAlreadyExistsException;
import exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }
    public Collection<User> findUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findbyEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.deleteById(id);
        }
        return user;
    }
/*
    public User findUserById(Long id) {
        return users.get(id);
    }*/
}
