package com.virtuslab.workshops.kotlin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO [hbysiak]
//it is only to prove that security works
//will be removed when we add some endpoint
@RestController
public class RestAPI {
    @RequestMapping("/testSecurity")
    public String index() {
        return "Security works!";
    }

}
