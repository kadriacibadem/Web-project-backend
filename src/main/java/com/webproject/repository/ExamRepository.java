package com.webproject.repository;

import com.webproject.Exam;
import com.webproject.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam,Integer> {

    Optional<Exam> findFirstByUserid(int id);
    Optional<Exam> findFirstByUseridAndScore(int id,String score);
    Optional<Exam> findFirstByUseridAndMatscore(int id,String score);
    Optional<Exam> findFirstByUseridAndFenscore(int id,String score);

}
