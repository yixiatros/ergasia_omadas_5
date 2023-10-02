package com.omada5.ergasia_omadas_5.user;


import com.omada5.ergasia_omadas_5.notification.Notification;
import com.omada5.ergasia_omadas_5.security.LogoutService;
import com.omada5.ergasia_omadas_5.security.auth.AuthenticationRequest;
import com.omada5.ergasia_omadas_5.security.auth.AuthenticationResponse;
import com.omada5.ergasia_omadas_5.security.auth.AuthenticationService;
import com.omada5.ergasia_omadas_5.task.Task;
import com.omada5.ergasia_omadas_5.task.TaskRepository;
import com.omada5.ergasia_omadas_5.task.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping(path = "/profile_view/{userId}/delete")
    public RedirectView deleteUser(@PathVariable("userId") Long userId, RedirectAttributes redirectAttributes) {
        userService.deleteUser(userId);
        redirectAttributes.addFlashAttribute("hasDeletedUser", true);
        return new RedirectView("/index");
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

        user.setImageData(multipartFile.getBytes());
        userRepository.save(user);

        return new RedirectView("/users/profile_view/" + user.getId());
    }

    @GetMapping(value = "/image/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Resource downloadImage(@PathVariable("userId") Long userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (!userOptional.isPresent())
            return null;

        byte[] image = userOptional.get().getImageData();

        return new ByteArrayResource(image);
    }

    @GetMapping(path = "/profile_view/notifications/{userId}")
    public String showNotifications(@PathVariable("userId") Long userId, Model model){
        putUsername(model);
        Optional<User> optionalUser = userService.getUserById(userId);
        if (!optionalUser.isPresent())
            return "/index";

        List<Notification> notifications = userService.getNotificationsOfUser(userId);
        notifications.sort(Comparator.comparing(Notification::getId).reversed());
        model.addAttribute("notifications", notifications);
        return "/notifications";
    }

    @GetMapping(path = "/profile_view/notifications/{userId}/delete/{notificationId}")
    public RedirectView deleteNotification(@PathVariable("userId") Long userId, @PathVariable("notificationId") Long notificationId, Model model, RedirectAttributes redirectAttributes){
        Optional<Notification> notificationOptional = userService.getNotificationById(notificationId);
        if (!notificationOptional.isPresent())
            return new RedirectView("/users/profile_view/notifications/" + userId.toString());

        userService.deleteNotification(notificationOptional.get());

        redirectAttributes.addFlashAttribute("hasNotificationDeleted", true);

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

    @GetMapping(path = "/register/registrationError/{message}")
    public String registerPageError(Model model, @PathVariable(name = "message") String message){
        model.addAttribute("registrationError", true);
        if (message.equals("null"))
            message = "Email is not valid";
        model.addAttribute("registrationErrorMessage", message);
        return returnToRegisterPage(model);
    }

    @GetMapping(path = "/register")
    public String registerPage(Model model){
        return returnToRegisterPage(model);
    }

    @PostMapping(path = "/register")
    public RedirectView register(@ModelAttribute User newUser,
                           @RequestParam(name = "client", required = false, defaultValue = "false") Boolean isClient,
                           @RequestParam(name = "developer", required = false, defaultValue = "false") Boolean isDeveloper,
                           Model model, RedirectAttributes redirectAttributes) {
        if (isClient)
            newUser.addRole(roleRepository.findByName("client").get());
        if (isDeveloper)
            newUser.addRole(roleRepository.findByName("developer").get());

        AuthenticationResponse authenticationResponse = userService.addNewUser(newUser);

        if (authenticationResponse.getAccessToken() == null){
            return new RedirectView("/users/register/registrationError/" + authenticationResponse.getRefreshToken());
        }

        redirectAttributes.addFlashAttribute("hasRegistered", true);

        return new RedirectView("/users/login");
    }

    private String returnToRegisterPage(Model model){
        putUsername(model);

        model.addAttribute("isLoginHidden", true);
        model.addAttribute("isRegisterHidden", false);
        model.addAttribute("newUser", new User());
        return "login";
    }

    @GetMapping(path = "/logout")
    public RedirectView logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, @RequestParam(defaultValue = "true") boolean cantChange){
        logoutService.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);

        redirectAttributes.addFlashAttribute("hasLoggedOut", true);
        if (!cantChange)
            redirectAttributes.addFlashAttribute("cantChange", false);

        return new RedirectView("/index");
    }

    @GetMapping(path = "/user_search")
    public String searchUser(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                             @RequestParam(name = "role", defaultValue = "-1") List<Long> roleId,
                             @RequestParam(name = "rating", defaultValue = "-1") int rating,
                             @RequestParam(name = "datetime", defaultValue = "-1") int datetime,
                             @RequestParam(name = "num_of_completed", defaultValue = "-1") int num_of_completed,
                             Model model){

        return getUserSearch(model, keyword, roleId, rating, datetime, num_of_completed);
    }

    @GetMapping(path = "/user_search/forward/{taskId}")
    public String searchUserForForward(@PathVariable("taskId") Long taskId,
                                       @RequestParam(name = "keyword", defaultValue = "") String keyword,
                                       @RequestParam(name = "role", defaultValue = "-1") List<Long> roleId,
                                       @RequestParam(name = "rating", defaultValue = "-1") int rating,
                                       @RequestParam(name = "datetime", defaultValue = "-1") int datetime,
                                       @RequestParam(name = "num_of_completed", defaultValue = "-1") int num_of_completed,
                                       Model model) {

        model.addAttribute("forwardingTaskId", taskId);

        roleId = new ArrayList<>();
        roleId.add(2L);
        return getUserSearch(model, keyword, roleId, rating, datetime, num_of_completed);
    }

    @GetMapping(path = "/user_search/forward/task/{taskId}/{userId}")
    public RedirectView forwardTask(@PathVariable("taskId") Long taskId, @PathVariable("userId") Long userId) {
        userService.saveNotification(
                userService.getUserById(userId).get(),
                "Task check invitation",
                userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get().getUsername() + " has invited you to check out the task with Title '" + taskService.getTaskById(taskId).get().getTitle() + "'. <a href=\"http://localhost:8080/task_view/" + taskId + "\" style=\"text-decoration: underline; cursor: pointer; color: blue;\">click here to go to the task</a>",
                true,
                false
        );
        return new RedirectView("/task_view/" + taskId);
    }

    private String getUserSearch(Model model , String keyword, List<Long> roleId, int rating, int datetime, int num_of_completed){
        putUsername(model);

        List<User> users = new ArrayList<>();
        if (keyword.equals(""))
            users = userService.getUsers();
        else
            users = userService.searchUsers(keyword);

        List<User> adminUsers = userRepository.findUsersByRoleName("admin");
        for (User u : adminUsers) {
            if (users.contains(u))
                users.remove(u);
        }

        List<Role> roles = roleRepository.findAll();
        roles.remove(roleRepository.findByName("admin").get());
        model.addAttribute("userRoles", roles);

        users = filterUsersByRole(users, roleId);
        users = filterUsersByRating(users, rating);
        users = filterUsersByNumOfCompleted(users, num_of_completed);
        if (datetime == 0)
            users.sort(Comparator.comparing(User::getId).reversed());

        model.addAttribute("users", users);

        return "/user_search";
    }

    private List<User> filterUsersByNumOfCompleted(List<User> users, int num_of_completed) {
        if (num_of_completed == -1)
            return users;

        List<Long> rolesId = new ArrayList<>();
        rolesId.add(2L); // developer id = 2
        users = filterUsersByRole(users, rolesId);

        List<Pair<Integer, Long>> numberOfCompleted = new ArrayList<>();
        for (User user: users){
            Pair<Integer, Long> pair = new Pair<Integer, Long>(taskRepository.findNumberOfCompletedTasksOfUser(user.getId()).size(), user.getId());
            numberOfCompleted.add(pair);
        }
        Collections.sort(numberOfCompleted, new Comparator<Pair<Integer, Long>>() {
            @Override
            public int compare(final Pair<Integer, Long> o1, final Pair<Integer, Long> o2) {
                return o1.a.compareTo(o2.a);
            }
        });

        List<User> newUsers = new ArrayList<>();
        if (num_of_completed == 0){
            Collections.reverse(numberOfCompleted);
        }

        for (Pair<Integer, Long> num : numberOfCompleted) {
            newUsers.add(userService.getUserById(num.b).get());
        }
        return newUsers;
    }

    private List<User> filterUsersByRating(List<User> users, int rating) {
        if (rating == -1)
            return users;

        List<User> newUsers = new ArrayList<>();
        for (User user: users){
            if (user.getRoles().contains(roleRepository.findByName("developer").get())) {
                List<UserRating> userRatings = userService.getUserRatingsOfUser(user.getId());
                int averageRating = 0;
                if (userRatings.size() > 0) {
                    for (UserRating r : userRatings)
                        averageRating += r.getRating();
                    averageRating = averageRating / userRatings.size();
                    if (averageRating > rating)
                        newUsers.add(user);
                }
            }
        }
        return newUsers;
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

    @PostMapping(path = "/profile_view/{userId}/change_username")
    public RedirectView changeUsername(@PathVariable("userId") Long userId,
                                       @RequestParam(name = "username") String username,
                                       RedirectAttributes redirectAttributes) {

        Optional<User> userOptional = userService.getUserByUsername(username);
        if (userOptional.isPresent()){
            redirectAttributes.addFlashAttribute("cantChange", true);
            return new RedirectView("/users/profile_view/" + userId);
        }

        User user = userService.getUserById(userId).get();
        user.setUsername(username);
        userRepository.save(user);
        redirectAttributes.addAttribute("cantChange", false);
        return new RedirectView("/users/logout");
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
