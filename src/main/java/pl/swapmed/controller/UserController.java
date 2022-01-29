package pl.swapmed.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pl.swapmed.model.User;
import pl.swapmed.model.Workplace;
import pl.swapmed.service.CurrentUser;
import pl.swapmed.service.UserService;
import pl.swapmed.service.WorkplaceService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final WorkplaceService workplaceService;

    public UserController(UserService userService, WorkplaceService workplaceService) {
        this.userService = userService;
        this.workplaceService = workplaceService;
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
    public ModelAndView showUserWorkplaces(@AuthenticationPrincipal CurrentUser currentUser) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByUsername(currentUser.getUsername());
        List<Workplace> workplaceUserList = workplaceService.findByUsers_Id(user.getId());
        modelAndView.addObject("workplaceUserList", workplaceUserList);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/dashboard");
        return modelAndView;
    }
}
