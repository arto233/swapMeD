package pl.swapmed.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.swapmed.model.Workplace;
import pl.swapmed.model.User;
import pl.swapmed.service.*;

import javax.validation.Valid;
import java.util.*;

@Controller
public class WorkplaceController {

    private final WorkplaceService workplaceService;
    private final UserService userService;


    public WorkplaceController(WorkplaceService workplaceService,
                               UserService userService) {
        this.workplaceService = workplaceService;
        this.userService = userService;
    }

    @GetMapping("/groups")
    public ModelAndView groups() {
        ModelAndView modelAndView = new ModelAndView();
        List<Workplace> workplaceList = workplaceService.findAll();
        modelAndView.addObject("workplaceList", workplaceList);
        modelAndView.setViewName("/user/groups/groups");
        return modelAndView;
    }

    @GetMapping("/groups/add")
    public ModelAndView addGroup() {
        ModelAndView modelAndView = new ModelAndView();
        Workplace workplace = new Workplace();
        modelAndView.addObject("workplace", workplace);
        modelAndView.setViewName("/user/groups/add/addGroupForm");
        return modelAndView;
    }

    @PostMapping("/groups/add")
    public ModelAndView saveNewGroup(@Valid Workplace workplace,
                                     BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/user/groups/add/addGroupForm");
            return modelAndView;
        }
        workplace.setCity(workplace.getCity());
        workplace.setName(workplace.getName());
        workplace.setAddress(workplace.getAddress());
        workplace.setDivision(workplace.getDivision());
        workplaceService.save(workplace);
        modelAndView.setViewName("redirect:/groups");

        return modelAndView;


    }

    @GetMapping("/groups/{id}")
    public String addUserToGroupConfirm(@AuthenticationPrincipal CurrentUser currentUser,
                                        @PathVariable long id) {

        Optional<Workplace> employmentPlace = workplaceService.findById(id);
        if (employmentPlace.isPresent()) {
            User user = userService.findUserByUsername(currentUser.getUser().getUsername());
            employmentPlace.get().addUser(user);
            workplaceService.save(employmentPlace.get());

        } else {
            return "redirect:/error";
        }
        return "redirect:/dashboard";
    }
}
