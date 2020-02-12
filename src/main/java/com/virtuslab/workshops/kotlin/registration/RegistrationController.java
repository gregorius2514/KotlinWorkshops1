package com.virtuslab.workshops.kotlin.registration;

import com.virtuslab.workshops.kotlin.user.dto.UserDto;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final MessageSourceAccessor messageSourceAccessor;

    public RegistrationController(RegistrationService registrationService, MessageSourceAccessor messageSourceAccessor) {
        Objects.requireNonNull(registrationService);
        Objects.requireNonNull(messageSourceAccessor);
        this.registrationService = registrationService;
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @GetMapping
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping
    public String add(@Valid UserDto userDto, BindingResult result, RedirectAttributes redirectAttrs) {
        checkPasswordsMatch(userDto, result);
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.userDto", result);
            redirectAttrs.addFlashAttribute("userDto", userDto);
            return "registration";
        }
        registrationService.createAccount(userDto);
        return "redirect:/login";

    }

    private void checkPasswordsMatch(UserDto userDto, BindingResult result) {
        if (!userDto.getPassword().equals(userDto.getMatchingPassword())) {
            result.addError(
                    new FieldError(
                            "userDto",
                            "matchingPassword",
                            messageSourceAccessor.getMessage("user.matchingPassword.match", new String[]{})));
        }
    }
}
