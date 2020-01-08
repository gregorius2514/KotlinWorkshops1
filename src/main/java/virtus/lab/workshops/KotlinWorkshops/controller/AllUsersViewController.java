package virtus.lab.workshops.KotlinWorkshops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;

@Controller
public class AllUsersViewController {

    @RequestMapping(value = "/runs", method = RequestMethod.GET)
    public String allUsersView(Model model) {
        model.addAttribute("users", findAllUsers());
        return "runs";
    }

    private List<UserDetail> findAllUsers() {
        return Arrays.asList(
                new UserDetail("Jan", "Kowalski", 100)
        );
    }
}

class UserDetail {

    private String firstName;
    private String lastName;
    private int userNumber;

    public UserDetail(
            String firstName,
            String lastName,
            int userNumber
    ) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.userNumber = userNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getUserNumber() {
        return userNumber;
    }
}
