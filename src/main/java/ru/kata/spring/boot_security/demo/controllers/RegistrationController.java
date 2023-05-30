package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/new")
public class RegistrationController {
    private UserService userService;
    private RoleRepository roleRepository;
    @Autowired
    public RegistrationController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }
    @GetMapping("")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        List<Role> roles =  roleRepository.findAll();
        model.addAttribute("allRoles", roles);

        return "new";
    }
    @PostMapping
    public String createUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds, Principal principal) {
        List<Role> roles = roleRepository.findAllById(roleIds);
        user.setRoles(roles);
        userService.createUser(user);
        if (principal == null) {
            return "redirect:/login";
    } else {
        return "redirect:/admin";
        }
    }
}
