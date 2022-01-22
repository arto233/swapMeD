package pl.swapmed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/registration")
    public String registration() {
        return "authentication/registration";
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
