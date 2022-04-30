package com.webproject.repository;

import com.webproject.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmailAndPassword(String email,String password);

    Optional<User> findFirstByEmail(String email);

}
