package com.example.sweater.controller;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) { // Переходить будет пользователь
        // для того что бы искать пользователя, нужно создать репозиторий (UserRepo)
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.put("message", "User exist");
            return "registration";
        }
        // обработка того, если у нас нет пользователя
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ROLE)); // на вход ожидается коллекция Set, но т.к у нас 1 значение, мы можем использовать шорткат, который создает Set с единственным значение
        if (user.getPassword().length() != 0)
            userRepo.save(user);
        else {
            System.err.println("Password invalid");
            return "redirect:/login";
        }
        return "redirect:/login"; // будем переходить на страницу /login при успешной регистрации
    }
}
