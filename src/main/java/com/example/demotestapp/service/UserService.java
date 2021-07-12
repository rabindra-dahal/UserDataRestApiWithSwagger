package com.example.demotestapp.service;

import com.example.demotestapp.models.User;
import com.example.demotestapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User save(User user) throws Exception{
        userValidation(user);
        if(user.getId() != null && existsById(user.getId())){
            throw new Exception("User with id : "+user.getId()+" already exists.");
        }
        return userRepository.save(user);
    }

    public User update(User user) throws Exception{
        userValidation(user);
        if (!existsById(user.getId())) {
            throw new Exception("Unable to find User with id: " + user.getId());
        }
        return userRepository.save(user);
    }

    private void userValidation(User user) throws Exception {
        if(StringUtils.isEmpty(user.getName())){
            throw new Exception("User name is required.");
        }
        if(StringUtils.isEmpty(user.getAddress())){
            throw new Exception("User address is required.");
        }
        if(StringUtils.isEmpty(user.getEmail())){
            throw new Exception("User email is required.");
        }

    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }
    public void deleteById(Long id) throws Exception{
        if(!existsById(id)){
            throw new Exception("Unable to find user with id : "+ id);
        }
        userRepository.deleteById(id);
    }



}
