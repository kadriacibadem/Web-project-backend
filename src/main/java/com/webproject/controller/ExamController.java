package com.webproject.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.webproject.Exam;
import com.webproject.User;
import com.webproject.service.ExamService;
import com.webproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService){
        this.examService = examService;
    }


    @GetMapping("/page2")
    public String getMainPage(Exam exam, Model model,User user){
        model.addAttribute("id",user.getId());
        model.addAttribute("name",user.getName());
        return "main-page";
    }


    @RequestMapping(value = "/exam/ingilizce/{id}/", method = RequestMethod.GET)
    public String getScore(@RequestParam(name = "score", required = true)String score,@PathVariable("id") User user,Model model,
                           @RequestParam(name = "time", required = true)String time)
    {

        //Önceden sınava girip girmediğini kontrol eder
        if(!examService.checkEnglishExam(user.getId(),score)){
            examService.setScoreandTime(score,time,user);
        }


        Exam authenticated =  examService.authenticate(user.getId());
        if(authenticated != null){
            return getMainPage(authenticated,model,user);
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/exam/fen/{id}/", method = RequestMethod.GET)
    public String getFenScore(@RequestParam(name = "score", required = true)String score,@PathVariable("id") User user,Model model,
                           @RequestParam(name = "time", required = true)String time)
    {

        //Sınava girip girmediğini kontrol eder
        if(!examService.checkFenExam(user.getId(),score)){
            examService.setFenScoreandTime(score,time,user);
        }


        Exam authenticated =  examService.authenticate(user.getId());
        if(authenticated != null){
            return getMainPage(authenticated,model,user);
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/exam/mat/{id}/", method = RequestMethod.GET)
    public String getMatScore(@RequestParam(name = "score", required = true)String score,@PathVariable("id") User user,Model model,
                              @RequestParam(name = "time", required = true)String time)
    {

        //Sınava girip girmediğini kontrol eder
        if(!examService.checkMatExam(user.getId(),score)){
            examService.setScoreandTime(score,time,user);
        }


        Exam authenticated =  examService.authenticate(user.getId());
        if(authenticated != null){
            return getMainPage(authenticated,model,user);
        }else{
            return null;
        }
    }



}
