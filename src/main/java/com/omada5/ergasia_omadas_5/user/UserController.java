package com.omada5.ergasia_omadas_5.user;

import com.omada5.ergasia_omadas_5.image.FileUploadUtil;
import com.omada5.ergasia_omadas_5.notification.Notification;
import com.omada5.ergasia_omadas_5.security.LogoutService;
import com.omada5.ergasia_omadas_5.security.auth.AuthenticationRequest;
import com.omada5.ergasia_omadas_5.security.auth.AuthenticationResponse;
import com.omada5.ergasia_omadas_5.security.auth.AuthenticationService;
import com.omada5.ergasia_omadas_5.task.Task;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;
    private final UserRepository userRepository;

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

        model.addAttribute("isClient", false);
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (role.getName().equals("client")){
                model.addAttribute("isClient", true);
                break;
            }
        }

        List<UserRating> userRatings = userService.getUserRatingsOfUser(optionalUser.get().getId());
        model.addAttribute("userRatings", userRatings);

        float averageRating = 0;
        int[] percentageOfRatings = new int[5];
        for (int i = 0; i < 5; i++) {
            percentageOfRatings[i] = 0;
        }
        if (userRatings.size() > 0){
            for (UserRating rating : userRatings){
                averageRating += rating.getRating();
                percentageOfRatings[rating.getRating() - 1]++;
            }
            for (int i = 0; i < 5; i++) {
                percentageOfRatings[i] = (int) ((float)percentageOfRatings[i] / (float)userRatings.size() * 100);
            }
            averageRating = averageRating / userRatings.size();
        }

        model.addAttribute("averageRating", (int) averageRating);
        model.addAttribute("numberOfRatings", percentageOfRatings);

        if (user.getRoles().contains(roleRepository.findByName("developer").get()))
            model.addAttribute("isDeveloper", true);

        return "profile_view";
    }

    @PostMapping(path = "/profile_view/change_picture")
    public RedirectView changeProfilePicture(Model model, @RequestParam("image") MultipartFile multipartFile) throws IOException{
        User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setProfilePicture(fileName);
        userRepository.save(user);

        String uploadDir = "user-photos/" + user.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return new RedirectView("/users/profile_view/" + user.getId());
    }

    @GetMapping(path = "/profile_view/notifications/{userId}")
    public String showNotifications(@PathVariable("userId") Long userId, Model model){
        putUsername(model);
        Optional<User> optionalUser = userService.getUserById(userId);
        if (!optionalUser.isPresent())
            return "/index";

        List<Notification> notifications = userService.getNotificationsOfUser(userId);
        model.addAttribute("notifications", notifications);
        return "/notifications";
    }

    @GetMapping(path = "/profile_view/notifications/{userId}/delete/{notificationId}")
    public RedirectView deleteNotification(@PathVariable("userId") Long userId, @PathVariable("notificationId") Long notificationId, Model model){
        Optional<Notification> notificationOptional = userService.getNotificationById(notificationId);
        if (!notificationOptional.isPresent())
            return new RedirectView("/users/profile_view/notifications/" + userId.toString());

        userService.deleteNotification(notificationOptional.get());

        return new RedirectView("/users/profile_view/notifications/" + userId.toString());
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

    @GetMapping(path = "/logout")
    public RedirectView logout(HttpServletRequest request, HttpServletResponse response){
        logoutService.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);
        return new RedirectView("/index");
    }

    @GetMapping(path = "/user_search")
    public String searchUser(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                             @RequestParam(name = "role", defaultValue = "-1") List<Long> roleId,
                             @RequestParam(name = "ability", defaultValue = "-1") List<Long> abilityId,
                             Model model){

        putUsername(model);

        List<User> users = new ArrayList<>();
        if (keyword.equals(""))
            users = userService.getUsers();
        else
            users = userService.searchUsers(keyword);

        List<Role> roles = roleRepository.findAll();
        roles.remove(roleRepository.findByName("admin").get());
        model.addAttribute("userRoles", roles);

        users = filterUsersByRole(users, roleId);
        model.addAttribute("users", users);

        return "/user_search";
    }

    private List<User> filterUsersByRole(List<User> users, List<Long> roleId){
        if (roleId.get(0) == -1)
            roleId.remove(0);

        if (roleId.size() == 0)
            return users;

        List<Role> roles = new ArrayList<>();
        for (Long id: roleId)
            roles.add(roleRepository.findRoleById(id).get());
        roles.remove(roleRepository.findByName("admin").get());

        List<User> newUsers = new ArrayList<>();
        for (User user: users)
            for (Role role: roles)
                if (user.getRoles().contains(role)){
                    newUsers.add(user);
                    break;
                }
        return newUsers;
    }

    @GetMapping(path = "/profile_view/rate/{userId}")
    public String rateUserPage(@PathVariable("userId") Long userId, Model model) {
        putUsername(model);
        User ratedUser = userService.getUserById(userId).get();
        model.addAttribute("ratedUser", ratedUser);

        return "/user_rate";
    }

    @PostMapping(path = "/profile_view/rate/{userId}")
    public RedirectView rateUser(@PathVariable("userId") Long userId,
                                 @RequestParam(name = "star_input", defaultValue = "1") int starInput,
                                 @RequestParam(name = "comment", defaultValue = "") String comment) {

        userService.saveUserRating(
                starInput,
                comment,
                userService.getUserById(userId).get()
        );

        return new RedirectView("/users/profile_view/" + userId.toString());
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
            Optional<User> userOptional = userService.getUserByUsername(authentication.getName());
            if (userOptional.isPresent()){
                model.addAttribute("logedInUserID", userOptional.get().getId());
                List<Notification> notifications = userService.getNotificationsOfUser(userOptional.get().getId());
                model.addAttribute("notifications", notifications);
            }
        }
    }
}
