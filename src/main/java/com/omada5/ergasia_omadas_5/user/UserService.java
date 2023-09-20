package com.omada5.ergasia_omadas_5.user;

import com.omada5.ergasia_omadas_5.notification.Notification;
import com.omada5.ergasia_omadas_5.notification.NotificationRepository;
import com.omada5.ergasia_omadas_5.security.auth.AuthenticationResponse;
import com.omada5.ergasia_omadas_5.security.auth.AuthenticationService;
import com.omada5.ergasia_omadas_5.security.auth.RegisterRequest;
import com.omada5.ergasia_omadas_5.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;
    private final UserRatingRepository userRatingRepository;
    private final NotificationRepository notificationRepository;

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

    public void saveUserRating(int rating, String comment, User userWhoIsRated){
        Optional<User> userOptional = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!userOptional.isPresent())
            return;

        var userRating = UserRating.builder()
                .rater(userOptional.get())
                .userWhoIsRated(userWhoIsRated)
                .rating(rating)
                .comment(comment)
                .dateTime(LocalDateTime.now())
                .build();
        userRatingRepository.save(userRating);
    }

    public List<UserRating> getUserRatingsOfUser(Long userId){
        return userRatingRepository.findUserRatingByUserWhoIsRated(userId);
    }

    public List<User> searchUsers(String search) {
        return userRepository.findUsersBySearch(search);
    }

    public Optional<Notification> getNotificationById(Long notificationId) {
        return notificationRepository.findNotificationById(notificationId);
    }

    public void deleteNotification(Notification notification) {
        notificationRepository.delete(notification);
    }

    public List<Notification> getNotificationsOfUser(Long userId) {
        return notificationRepository.findNotificationByUserId(userId);
    }

    public void saveNotification(User recipient, Task task, String title, String description, boolean isDeletable, boolean isHireRequest) {
        var notification = Notification.builder()
                .sender(getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get())
                .recipient(recipient)
                .task(task)
                .title(title)
                .description(description)
                .isDeletable(isDeletable)
                .isHireRequest(isHireRequest)
                .build();
        notificationRepository.save(notification);
    }

    public void saveNotification(User recipient, String title, String description, boolean isDeletable, boolean isHireRequest) {
        var notification = Notification.builder()
                .sender(getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get())
                .recipient(recipient)
                .title(title)
                .description(description)
                .isDeletable(isDeletable)
                .isHireRequest(isHireRequest)
                .build();
        notificationRepository.save(notification);
    }
}
