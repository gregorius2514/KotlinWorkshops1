package virtus.lab.workshops.KotlinWorkshops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

    @RequestMapping("/")
    public String homePage() {
        return "home-page";
    }
}
