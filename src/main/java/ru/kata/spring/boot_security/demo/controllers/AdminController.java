package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.utill.UserValidator;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;
    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }
    @GetMapping
    public ResponseEntity<List<User>> showAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}/edit")
    public String showOneUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("allRoles", roleService.getRoles());
        return "user";
    }

    @PatchMapping("/admin/user/{id}")
    public String update(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/new")
    public String showPageCreatingUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getRoles());
        return "admin";
    }

    @PostMapping("/admin/user")
    public String create(@ModelAttribute("user") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/user/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
