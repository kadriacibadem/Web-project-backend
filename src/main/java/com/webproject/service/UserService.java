package com.webproject.service;

import com.webproject.User;
import com.webproject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public User registerUser(String name, String password, String email, String surname){
        if(name == null || password == null){
            return null;
        }else{
            if(usersRepository.findFirstByEmail(email).isPresent()){
                System.out.println("Duplicate");
                return null;
            }
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            user.setEmail(email);
            user.setSurname(surname);
            return usersRepository.save(user);
        }
    }

    public User authenticate(String email, String password){
        return usersRepository.findByEmailAndPassword(email, password).orElse(null);
    }




}
