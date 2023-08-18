package com.omada5.ergasia_omadas_5;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/index")
    public String goToIndex() {
        return "index";
    }
}
