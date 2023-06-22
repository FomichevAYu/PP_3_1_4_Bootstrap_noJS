package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Init {
    private final RoleRepository roleRepository;
    private final UserService userService;
    @Autowired
    public Init(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @PostConstruct
    @Transactional
    public void initTestUsers() {
        Role userTest = new Role("ROLE_USER");
        Role adminTest = new Role("ROLE_ADMIN");
        roleRepository.save(userTest);
        roleRepository.save(adminTest);
        Set<Role> userTestRole = Stream.of(userTest).collect(Collectors.toSet());
        Set<Role> adminTestRole = Stream.of(adminTest).collect(Collectors.toSet());
        User user = new User("User", "User", 22, "user@mail.ru", "user", userTestRole);
        User admin = new User("Admin", "Admin", 22, "admin@mail.ru", "admin", adminTestRole);
        userService.save(user);
        userService.save(admin);

    }
}
