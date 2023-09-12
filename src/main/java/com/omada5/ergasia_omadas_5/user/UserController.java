package com.omada5.ergasia_omadas_5.user;

import com.omada5.ergasia_omadas_5.security.auth.AuthenticationRequest;
import com.omada5.ergasia_omadas_5.security.auth.AuthenticationResponse;
import com.omada5.ergasia_omadas_5.security.auth.AuthenticationService;
import com.omada5.ergasia_omadas_5.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final AuthenticationService authenticationService;

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @DeleteMapping(path="user/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
    }

    @GetMapping(path = "/profile_view/{userId}")
    public String seeProfile(@PathVariable("userId") Long userId, Model model){
        Optional<User> optionalUser = userService.getUserById(userId);
        User user = null;
        List<Task> tasks = new ArrayList<>();
        if (optionalUser.isPresent()){
            user = optionalUser.get();
            tasks = userService.getTasksOfUser(userId);
        }

        model.addAttribute("user", user);
        model.addAttribute("tasks", tasks);
        return "profile_view";
    }

    @GetMapping(path = "/loginToAccess")
    public String loginToAccess(Model model){
        return returnToLoginPage(model);
    }

    @GetMapping(path = "/loginError")
    public String loginFailed(Model model){
        model.addAttribute("loginError", true);
        return returnToLoginPage(model);
    }

    @GetMapping(path = "/login")
    public String loginPage(Model model){
        return returnToLoginPage(model);
    }

    /*@PostMapping(path = "/login")
    public RedirectView login(@ModelAttribute User newUser, Model model){
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(
                new AuthenticationRequest(
                        newUser.getUsername(),
                        newUser.getPassword()
                )
        );

        if (authenticationResponse.getAccessToken() == null)
            return new RedirectView("/users/loginError");

        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + authenticationResponse.getToken() + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        return new RedirectView("/index");
    }*/

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@ModelAttribute User newUser) {
        ResponseEntity<AuthenticationResponse> responseEntity = ResponseEntity.ok(authenticationService.authenticate(
                new AuthenticationRequest(
                        newUser.getUsername(),
                        newUser.getPassword()
                )
        ));

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + responseEntity.getHeaders() + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        return responseEntity;
    }

    private String returnToLoginPage(Model model){
        model.addAttribute("isLoginHidden", false);
        model.addAttribute("isRegisterHidden", true);
        model.addAttribute("newUser", new User());
        return "login";
    }

    @GetMapping(path = "/register")
    public String registerPage(Model model){
        return returnToRegisterPage(model);
    }

    @PostMapping(path = "/register")
    public RedirectView register(@ModelAttribute User newUser,
                           @RequestParam(name = "client", required = false, defaultValue = "false") Boolean isClient,
                           @RequestParam(name = "developer", required = false, defaultValue = "false") Boolean isDeveloper,
                           Model model) {
        if (isClient)
            newUser.addRole(roleRepository.findByName("client").get());
        if (isDeveloper)
            newUser.addRole(roleRepository.findByName("developer").get());

        AuthenticationResponse authenticationResponse = userService.addNewUser(newUser);
        return new RedirectView("/index");
    }

    private String returnToRegisterPage(Model model){
        model.addAttribute("isLoginHidden", true);
        model.addAttribute("isRegisterHidden", false);
        model.addAttribute("newUser", new User());
        return "login";
    }
}
