package com.webproject.service;

import com.webproject.Exam;
import com.webproject.User;
import com.webproject.repository.ExamRepository;
import org.springframework.stereotype.Service;

@Service
public class ExamService {
    private ExamRepository examRepository;

    public ExamService(ExamRepository examRepository){
        this.examRepository = examRepository;
    }



    public Exam setScoreandTime(String score,String time, User user){

        Exam exam = new Exam();
        exam.setScore(score);
        exam.setTime(time);
        exam.setUserid(user.getId());
        return examRepository.save(exam);

    }

    public Boolean checkUser(int id){
        if(examRepository.findFirstByUserid(id).isPresent()){
            return true;
        }
        else {
            return false;
        }
    }


    public Exam setFenScoreandTime(String score,String time, User user){
        Exam exam = new Exam();
        exam.setFenscore(score);
        exam.setFentime(time);
        exam.setUserid(user.getId());
        return examRepository.save(exam);
    }

    public Exam setMatScoreandTime(String score,String time, User user){
        Exam exam = new Exam();
        exam.setMatscore(score);
        exam.setMattime(time);
        exam.setUserid(user.getId());
        return examRepository.save(exam);
    }

    public Exam authenticate(int id){
        return examRepository.findFirstByUserid(id).orElse(null);
    }

    public Boolean checkEnglishExam(int id,String score){
        if(examRepository.findFirstByUseridAndScore(id,score).isPresent()){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean checkMatExam(int id,String score){
        if(examRepository.findFirstByUseridAndMatscore(id,score).isPresent()){
            return true;
        }
        else{
            return false;
        }
    }


    public Boolean checkFenExam(int id,String score){
        if(examRepository.findFirstByUseridAndFenscore(id,score).isPresent()){
            return true;
        }
        else{
            return false;
        }
    }

}
