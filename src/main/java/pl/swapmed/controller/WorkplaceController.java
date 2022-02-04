package pl.swapmed.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.swapmed.model.Schedule;
import pl.swapmed.model.Workplace;
import pl.swapmed.model.User;
import pl.swapmed.service.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/workplace")
public class WorkplaceController {

    private final WorkplaceService workplaceService;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final DutyService dutyService;


    public WorkplaceController(WorkplaceService workplaceService,
                               UserService userService, ScheduleService scheduleService, DutyService dutyService) {
        this.workplaceService = workplaceService;
        this.userService = userService;
        this.scheduleService = scheduleService;
        this.dutyService = dutyService;
    }

    @GetMapping("")
    public ModelAndView showAllWorkplaces(@AuthenticationPrincipal CurrentUser currentUser) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByUsername(currentUser.getUsername());
        List<Workplace> workplaceList = workplaceService.findAll();
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("workplaceList", workplaceList);
        modelAndView.setViewName("/workplace/list");
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addGroup() {
        ModelAndView modelAndView = new ModelAndView();
        Workplace workplace = new Workplace();
        modelAndView.addObject("workplace", workplace);
        modelAndView.setViewName("/workplace/add");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView saveNewGroup(@Valid Workplace workplace,
                                     BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/workplace/add");
            return modelAndView;
        }
        workplace.setCity(workplace.getCity());
        workplace.setName(workplace.getName());
        workplace.setAddress(workplace.getAddress());
        workplace.setDivision(workplace.getDivision());
        workplaceService.save(workplace);
        modelAndView.setViewName("redirect:/workplace");

        return modelAndView;


    }

    @GetMapping("/{workplaceId}")
        public ModelAndView workplaceSchedulesList(@PathVariable Long workplaceId,
                                                   @AuthenticationPrincipal CurrentUser currentUser) {
            ModelAndView modelAndView = new ModelAndView();
            Optional<Workplace> workplace = workplaceService.findById(workplaceId);
            if (workplace.isPresent()) {
                User user = userService.findUserByUsername(currentUser.getUsername());
                List<Schedule> scheduleList = scheduleService.findByWorkplace_IdAndUser(workplace.get().getId(), user);
                Collections.sort(scheduleList, (a, b) -> a.getMonth().compareTo(b.getMonth()));
                Collections.sort(scheduleList, (a, b) -> a.getYear().compareTo(b.getYear()));
                modelAndView.addObject("workplace", workplace);
                modelAndView.addObject("scheduleList", scheduleList);
                modelAndView.setViewName("/schedule/list");
                return modelAndView;
            }
            modelAndView.setViewName("redirect:/error");
            return modelAndView;
        }

    @GetMapping("/{workplaceId}/edit")
    public ModelAndView editWorkplace(@PathVariable Long workplaceId) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
        if (workplace.isPresent()) {
            modelAndView.addObject("workplace", workplace.get());
            modelAndView.setViewName("/workplace/edit");
            return modelAndView;

        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }

    @PostMapping("/{workplaceId}/edit")
    public ModelAndView editWorkplaceForm(@Valid Workplace workplace,
                                          BindingResult bindingResult,
                                          @PathVariable Long workplaceId) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/workplace/edit");
            return modelAndView;
        }

        workplaceService.updateWorkplace(workplace.getCity(), workplace.getName(),
                workplace.getAddress(), workplace.getDivision(), workplaceId);
        modelAndView.setViewName("redirect:/workplace");

        return modelAndView;
    }

    @GetMapping("/{workplaceId}/add")
    public ModelAndView addUserToWorkplace(@PathVariable long workplaceId) {

        ModelAndView modelAndView = new ModelAndView();
        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
        if (workplace.isPresent()) {
            modelAndView.setViewName("/user/workplace/addConfirm");
            modelAndView.addObject("workplace", workplace.get());
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }

    @PostMapping("/{workplaceId}/add")
    public String addUserToWorkplaceConfirm(@AuthenticationPrincipal CurrentUser currentUser,
                                            @PathVariable long workplaceId) {

        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
        if (workplace.isPresent()) {
            User user = userService.findUserByUsername(currentUser.getUser().getUsername());
            workplace.get().addUser(user);
            workplaceService.save(workplace.get());

        } else {
            return "redirect:/error";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/{workplaceId}/details")
    public ModelAndView showDetails(@PathVariable Long workplaceId,
                                    @AuthenticationPrincipal CurrentUser currentUser) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByUsername(currentUser.getUsername());
        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
        if (workplace.isPresent()) {
            Boolean checkUser = workplace.get().getUsers().contains(user);
            modelAndView.addObject("checkUser", checkUser);
            modelAndView.addObject("workplace", workplace.get());
            modelAndView.setViewName("/workplace/details");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }

    @GetMapping("/{workplaceId}/delete-user")
    public ModelAndView deleteUserFromWorkplace(@PathVariable Long workplaceId) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
        if (workplace.isPresent()) {
            modelAndView.addObject("workplace", workplace.get());
            modelAndView.setViewName("/user/workplace/deleteConfirm");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }

    @PostMapping("/{workplaceId}/delete-user")
    public ModelAndView deleteUserFromWorkplaceConfirm(@AuthenticationPrincipal CurrentUser currentUser,
                                                       @PathVariable long workplaceId) {

        ModelAndView modelAndView = new ModelAndView();
        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
        if (workplace.isPresent()) {
            User user = userService.findUserByUsername(currentUser.getUser().getUsername());
            workplaceService.deleteUserFromWorkplace(user.getId(), workplace.get().getId());
            dutyService.deleteDutiesByUserId(user.getId());
            scheduleService.deleteUserFromSchedule(user.getId(), workplaceId);
            modelAndView.setViewName("redirect:/dashboard");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }
}
