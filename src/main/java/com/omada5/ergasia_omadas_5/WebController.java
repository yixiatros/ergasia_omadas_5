package com.omada5.ergasia_omadas_5;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class WebController {
    @RequestMapping("/index")
    public String goToIndex() {
        return "index";
    }
}
