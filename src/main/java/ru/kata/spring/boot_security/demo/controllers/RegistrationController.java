package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;

@Controller
@RequestMapping("/new")
public class RegistrationController {
    final private UserService userService;
    final private RoleRepository roleRepository;
    @Autowired
    public RegistrationController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }
    @GetMapping("")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "new";
    }
    @PostMapping
    public String createUser(@ModelAttribute("user") User user, Principal principal) {
        userService.createUser(user);
        if (principal == null) {
            return "redirect:/login";
        } else {
            return "redirect:/admin";
        }
    }
}
