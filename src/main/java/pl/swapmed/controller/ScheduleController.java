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
import pl.swapmed.model.User;
import pl.swapmed.model.Workplace;
import pl.swapmed.service.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/workplace/{id}/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final WorkplaceService workplaceService;
    private final DutyService dutyService;
    private final UserService userService;


    public ScheduleController(ScheduleService scheduleService,
                              WorkplaceService workplaceService,
                              DutyService dutyService,
                              UserService userService) {
        this.scheduleService = scheduleService;
        this.workplaceService = workplaceService;
        this.dutyService = dutyService;
        this.userService = userService;
    }

    @GetMapping("")
    public ModelAndView workplaceSchedulesList(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Workplace> workplace = workplaceService.findById(id);
        if (workplace.isPresent()) {
            List<Schedule> scheduleList = scheduleService.findByWorkplaceId(workplace.get().getId());
            modelAndView.addObject("workplace", workplace);
            modelAndView.addObject("scheduleList", scheduleList);
            modelAndView.setViewName("/schedule/list");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addSchedule(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Workplace> workplace = workplaceService.findById(id);
        if (workplace.isPresent()) {
            Schedule schedule = new Schedule();
            modelAndView.addObject("workplace", workplace);
            modelAndView.addObject("schedule", schedule);
            modelAndView.setViewName("/schedule/add");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView saveSchedule(@Valid Schedule schedule,
                                     BindingResult bindingResult,
                                     @AuthenticationPrincipal CurrentUser currentUser,
                                     @PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/schedule/add");
            return modelAndView;
        }
        Optional<Workplace> workplace = workplaceService.findById(id);
        if (workplace.isPresent()) {
            User user = userService.findUserByUsername(currentUser.getUser().getUsername());
            schedule.setMonth(schedule.getMonth());
            schedule.setYear(schedule.getYear());
            schedule.setWorkplace(workplace.get());
            schedule.addUser(user);
            scheduleService.save(schedule);
            modelAndView.setViewName("redirect:/workplace/{id}/schedule");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }
}
