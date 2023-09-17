package com.omada5.ergasia_omadas_5.user;

import com.omada5.ergasia_omadas_5.security.JwtService;
import com.omada5.ergasia_omadas_5.security.LogoutService;
import com.omada5.ergasia_omadas_5.security.auth.AuthenticationRequest;
import com.omada5.ergasia_omadas_5.security.auth.AuthenticationResponse;
import com.omada5.ergasia_omadas_5.security.auth.AuthenticationService;
import com.omada5.ergasia_omadas_5.task.Task;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

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
    private final LogoutService logoutService;

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
        putUsername(model);

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

    @PostMapping(path = "/login")
    public ResponseEntity<String> authenticate(@ModelAttribute User newUser, HttpServletResponse httpServletResponse) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(
                new AuthenticationRequest(
                        newUser.getUsername(),
                        newUser.getPassword()
                )
        );

        if (authenticationResponse.getAccessToken() == null){
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange("http://localhost:8080/users/loginError", HttpMethod.GET, entity, String.class);
        }

        return editSession(authenticationResponse);
    }

    private String returnToLoginPage(Model model){
        putUsername(model);

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
        putUsername(model);

        model.addAttribute("isLoginHidden", true);
        model.addAttribute("isRegisterHidden", false);
        model.addAttribute("newUser", new User());
        return "login";
    }

    private ResponseEntity<String> editSession(AuthenticationResponse authenticationResponse) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authenticationResponse.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange("http://localhost:8080/tasks", HttpMethod.GET, entity, String.class);
    }

    private void putUsername(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("logedInUsername", authentication.getName());
            Optional<User> user = userService.getUserByUsername(authentication.getName());
            user.ifPresent(value -> model.addAttribute("logedInUserID", value.getId()));
        }
    }

    @GetMapping(path = "/logout")
    public RedirectView logout(HttpServletRequest request, HttpServletResponse response){
        logoutService.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);
        return new RedirectView("/index");
    }
}
