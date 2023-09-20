package com.omada5.ergasia_omadas_5;

import com.omada5.ergasia_omadas_5.notification.Notification;
import com.omada5.ergasia_omadas_5.security.JwtService;
import com.omada5.ergasia_omadas_5.user.User;
import com.omada5.ergasia_omadas_5.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final JwtService jwtService;
    private final UserService userService;

    @RequestMapping("/index")
    public String goToIndex(Model model) {
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
        return "index";
    }
}
