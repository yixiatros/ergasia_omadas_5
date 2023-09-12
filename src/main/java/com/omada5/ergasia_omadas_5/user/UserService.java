package com.omada5.ergasia_omadas_5.user;

import com.omada5.ergasia_omadas_5.security.auth.AuthenticationResponse;
import com.omada5.ergasia_omadas_5.security.auth.AuthenticationService;
import com.omada5.ergasia_omadas_5.security.auth.RegisterRequest;
import com.omada5.ergasia_omadas_5.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @GetMapping
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping
    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @GetMapping
    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @GetMapping
    public List<Task> getTasksOfUser(Long userId){
        return userRepository.findTasksOfUserById(userId);
    }

    @GetMapping
    public Optional<User> getUserById(Long userId){
        return userRepository.findUserById(userId);
    }

    public AuthenticationResponse addNewUser(User user){
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        Optional<User> userOptional1 = userRepository.findByUsername(user.getUsername());

        if (userOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }

        if (userOptional1.isPresent()){
            throw new IllegalStateException("username taken");
        }

        return authenticationService.register(
                new RegisterRequest(
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getRoles()
                )
        );
    }

    public void deleteUser(Long userId){
        boolean exists = userRepository.existsById(userId);

        if(!exists)
            throw new IllegalStateException("User with id " + userId + "does not exist.");

        userRepository.deleteById(userId);
    }
}
