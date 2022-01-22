package pl.swapmed.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.swapmed.model.User;
import pl.swapmed.service.CurrentUser;
import pl.swapmed.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create-user")
    @ResponseBody
    public String createUser() {
        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@wp.pl");
        user.setPassword("admin");
        user.setName("Admin");
        user.setLastName("Adminowski");
        userService.saveUser(user);
        return "admin";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String userInfo(@AuthenticationPrincipal CurrentUser customUser) {
        User modelUser = customUser.getUser();
        return "Hello " + modelUser.getUsername();
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("currentUser", currentUser);
        return "user/dashboard";
    }
}
