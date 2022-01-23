package pl.swapmed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.swapmed.model.User;
import pl.swapmed.service.UserService;

import javax.validation.Valid;

@Controller
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("authentication/registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUsername(user.getUsername());
        if(userExists !=null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "Użytkownik o tej nazwie jest już zarejestrowany");
        }
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("/authentication/registration");
            return modelAndView;
        }
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setName(user.getName());
        user.setLastName(user.getLastName());
        userService.saveUser(user);
        modelAndView.setViewName("redirect:/");
        return modelAndView;

    }
    /*
    @GetMapping("/login")
    public String login() {
        return "authentication/login";


    }
     */

    @GetMapping("/confirm-logout")
    public String logoutConfirm() {
        return "authentication/logout";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/logout";
    }

    @GetMapping("/403")
    public String error() {
        return "/authentication/403";
    }
}
