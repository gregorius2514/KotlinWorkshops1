package virtus.lab.workshops.KotlinWorkshops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import virtus.lab.workshops.KotlinWorkshops.model.UserDto;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

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
        return "redirect:/testSecurity";

    }

//    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
//    public ModelAndView registerUserAccount(
//            @ModelAttribute("user") @Valid UserDto accountDto,
//            BindingResult result,
//            WebRequest request,
//            Errors errors) {
//
//        User registered = new User();
//        if (!result.hasErrors()) {
//            registered = createUserAccount(accountDto, result);
//        }
//        if (registered == null) {
//            result.rejectValue("email", "message.regError");
//        }
//        if (result.hasErrors()) {
//            return new ModelAndView("registration", "user", accountDto);
//        }
//        else {
//            return new ModelAndView("successRegister", "user", accountDto);
//        }
//    }
//    private User createUserAccount(UserDto accountDto, BindingResult result) {
//        User registered = null;
//        try {
//            registered = service.registerNewUserAccount(accountDto);
//        } catch (EmailExistsException e) {
//            return null;
//        }
//        return registered;
//    }
}
