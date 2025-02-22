package cz.upce.fei.nnpiacv.service;

import cz.upce.fei.nnpiacv.domain.User;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private Map<Long, User> users = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @PostConstruct
    public void init() {
        users.put(1L, new User(1L, "filip@seznam.cz", "heslo"));
        users.put(2L, new User(2L, "tomas@seznam.cz", "heslo"));
    }

    public Collection<User> findUsers() {
        return users.values();
    }


    public String findUser() {
        logger.info("Users were printed");
        StringBuilder sb = new StringBuilder();
        for (User user : users.values()) {
            sb.append(user).append("\n");
        }
        return sb.toString();
    }

    //method find user by id
    public User findUserById(Long id) {
        logger.info("User with id: " + id + " was printed");
        return users.get(id);
    }
}
