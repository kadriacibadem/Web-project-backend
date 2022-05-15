package com.webproject.controller;


import com.webproject.User;
import com.webproject.service.UserService;;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



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

    @GetMapping("/page/{id}")
    public String getMainPage2(@PathVariable("id") User user, Model model){
        model.addAttribute("id",user.getId());
        model.addAttribute("name",user.getName());
        User authenticated =  userService.authenticate(user.getEmail(), user.getPassword());
        return getMainPage(authenticated,model);
    }

    @GetMapping("/page")
    public String getMainPage(@ModelAttribute User user, Model model){
        model.addAttribute("id",user.getId());
        model.addAttribute("name",user.getName());
        return "main-page";
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

    @GetMapping("/exam/mat/{id}")
    public String matExam(@PathVariable("id") User user, Model model){
        model.addAttribute("name",user.getName());
        return "../static/mat-exam";
    }

    @GetMapping("/exam/fen/{id}")
    public String fenExam(@PathVariable("id") User user, Model model){
        model.addAttribute("name",user.getName());
        return "../static/fen-exam";
    }

    @GetMapping("/exam/ingilizce/{id}")
    public String englishExam(@PathVariable("id") User user, Model model){
        model.addAttribute("name",user.getName());
        model.addAttribute("id",user.getId());
        return "../static/english-exam";
    }


    @GetMapping("/fen/gezegenimizitaniyalim/{id}")
    public String getFenSubject1(@PathVariable("id") User user,Model model){
        model.addAttribute("name",user.getName());
        model.addAttribute("id",user.getId());
        return "../static/fen-konu1";
    }


    @GetMapping("/fen/maddeyitaniyalim/{id}")
    public String getFenSubject2(@PathVariable("id") User user,Model model){
        model.addAttribute("name",user.getName());
        model.addAttribute("id",user.getId());
        return "../static/fen-konu2";
    }

    @GetMapping("/fen/canlilar/{id}")
    public String getFenSubject3(@PathVariable("id") User user,Model model){
        model.addAttribute("name",user.getName());
        model.addAttribute("id",user.getId());
        return "../static/fen-konu3";
    }



}
