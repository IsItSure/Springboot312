package org.example.springboot312.controller;


import org.example.springboot312.model.User;
import org.example.springboot312.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    private final UserService userServiceImpl;

    @Autowired
    UserController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/" )
    public String userList(Model model) {
        List<User> users = userServiceImpl.getUsers();
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/userForm")
    public String userForm(@RequestParam(value="id", required = false) Long id, Model model) {
        User user;
        if (id == null) {
            user = new User();
        } else {
            user = userServiceImpl.getUserById(id);
        }
        model.addAttribute("user", user);
        return "userForm";
    }


    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        if ((user.getId() == null) || (user.getId() == 0)) {
            userServiceImpl.saveUser(user);
        } else {
            userServiceImpl.updateUser(user);
        }
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/";
    }
}
