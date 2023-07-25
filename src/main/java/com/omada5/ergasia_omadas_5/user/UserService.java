package com.omada5.ergasia_omadas_5.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void addNewUser(User user){
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        Optional<User> userOptional1 = userRepository.findUserByUsername(user.getUsername());

        if (userOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }

        if (userOptional1.isPresent()){
            throw new IllegalStateException("username taken");
        }

        userRepository.save(user);
    }

    public void deleteUser(Long userId){
        boolean exists = userRepository.existsById(userId);

        if(!exists)
            throw new IllegalStateException("User with id " + userId + "does not exist.");

        userRepository.deleteById(userId);
    }
}
