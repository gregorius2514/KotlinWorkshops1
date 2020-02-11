package com.virtuslab.workshops.kotlin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

    @RequestMapping("/")
    public String userHome() {
        return "user-home";
    }
}
