package virtus.lab.workshops.KotlinWorkshops;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAPI {

    @RequestMapping("/testSecurity")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
