package pl.swapmed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/registration")
    public String registration() {
        return "authentication/registration";
    }

    @GetMapping("/login")
    public String login() {
        return "authentication/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "authentication/logout";
    }

    @GetMapping("/403")
    public String error() {
        return "authentication/403";
    }
}
