package com.webproject.controller;

import com.webproject.User;
import com.webproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest",new User());
        return "register_page";
    }

    @GetMapping("/page")
    public String getMainPage(User user, Model model){
        model.addAttribute("name",user.getName());
        return "../static/main_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new User());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user){
        System.out.println("register request: " + user);
       User registeredUser =  userService.registerUser(user.getName(), user.getPassword(), user.getEmail(),user.getSurname());
       return registeredUser == null ? "error_page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user,Model model){
        System.out.println("login request: " + user);
        User authenticated =  userService.authenticate(user.getEmail(), user.getPassword());
        if(authenticated != null){
            return getMainPage(authenticated,model);
        }else{
            return null;
        }
    }
}
