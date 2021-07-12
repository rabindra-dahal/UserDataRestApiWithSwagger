package com.example.demotestapp.controller;

import com.example.demotestapp.models.User;
import com.example.demotestapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserDataById(@PathVariable("id") long id) {
        Optional<User> userData = Optional.ofNullable(userService.findById(id));

        if (userData.isPresent()) {
            logger.debug("User data " + userData);
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            logger.debug("User data not found " + userData);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>>  getAllUsers(){
        List<User> users = userService.findAllUsers();
        if(users.isEmpty()){
            logger.debug("User data not found " + users);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.debug("User data  " + users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        try {
            User newUser = userService.save(user);
            logger.debug("User data : "+newUser);
            return new ResponseEntity<>(newUser,HttpStatus.CREATED);
        } catch (Exception e) {
            logger.debug("Exception "+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        try{
            Optional<User> userData = Optional.ofNullable(userService.findById(id));
            if(userData.isPresent()){
                User user1 = userData.get();
                user1.setAddress(user.getAddress());
                user1.setEmail(user.getEmail());
                user1.setName(user.getName());
                logger.debug("User data : "+ user1);
                return new ResponseEntity<>(userService.update(user1),HttpStatus.OK);
            }else{
                logger.debug("Not found "+userData);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch(Exception e){
            logger.debug("Exception "+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
        try{
            Optional<User> userData = Optional.ofNullable(userService.findById(id));
            if(userData.isPresent()){
                userService.deleteById(userData.get().getId());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                logger.debug("Not found "+userData);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch(Exception e){
            logger.debug("Exception "+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




}
