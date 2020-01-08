package com.virtuslab.workshops.kotlin.user;

import com.virtuslab.workshops.kotlin.security.AuthenticatedUserService;
import com.virtuslab.workshops.kotlin.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserDetailsView {

    private AuthenticatedUserService authenticatedUserService;

    @Autowired
    public UserDetailsView(AuthenticatedUserService authenticatedUserService) {
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping("/user")
    public String userDetailsView(Model viewModel) {
        UserDto userDto = new UserDto();

        authenticatedUserService
                .authenticatedUser()
                .ifPresent(user -> {
                    userDto.setEmail(user.getEmail());
                    userDto.setFirstName(user.getFirstName());
                    userDto.setLastName(user.getLastName());
                });
        viewModel.addAttribute("user", userDto);

        return "user";
    }
}
