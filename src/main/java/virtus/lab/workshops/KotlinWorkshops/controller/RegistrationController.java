package virtus.lab.workshops.KotlinWorkshops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import virtus.lab.workshops.KotlinWorkshops.model.dto.UserDto;
import virtus.lab.workshops.KotlinWorkshops.service.RegistrationService;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping
    public String add(@Valid UserDto userDto, BindingResult result, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.userDto", result);
            redirectAttrs.addFlashAttribute("userDto", userDto);
            return "registration";
        }
        registrationService.createAccount(userDto);
        return "redirect:/login";

    }
}
