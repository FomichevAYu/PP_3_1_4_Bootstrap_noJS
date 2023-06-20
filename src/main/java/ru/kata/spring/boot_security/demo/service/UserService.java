package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.security.Principal;
import java.util.List;

public interface UserService {

    List<User> findAll();

    User findOne(long id);

    User getAuthUser(Principal principal);

    User findByUsername(String username);

    void save(User user);

    void update(User user);

    void delete(long id);

}
