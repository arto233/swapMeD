package pl.swapmed.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.swapmed.model.Duty;
import pl.swapmed.model.Schedule;
import pl.swapmed.model.User;
import pl.swapmed.model.Workplace;
import pl.swapmed.service.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/workplace/{workplaceId}/schedule")
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
    ModelAndView showSchedule(@PathVariable Long workplaceId,
                              @AuthenticationPrincipal CurrentUser currentUser) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
        if (workplace.isPresent()) {
            User user = userService.findUserByUsername(currentUser.getUsername());
            List<Schedule> schedules = scheduleService.findByWorkplaceId(workplaceId);
            int dayNumbers = daysNumberInMonth(schedules.get(0).getMonth(), schedules.get(0).getYear());
            modelAndView.addObject("workplace", workplace);
            modelAndView.addObject("dayNumber", dayNumbers);
            modelAndView.addObject("schedules", schedules);
            modelAndView.addObject("user", user);

            modelAndView.setViewName("schedule/list");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }


    @GetMapping("/add")
    public ModelAndView addSchedule(@PathVariable Long workplaceId) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
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
                                     @PathVariable Long workplaceId) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/schedule/add");
            return modelAndView;
        }
        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
        if (workplace.isPresent()) {
            User user = userService.findUserByUsername(currentUser.getUser().getUsername());
            //schedule.setId(null);
            schedule.setMonth(schedule.getMonth());
            schedule.setYear(schedule.getYear());
            schedule.setWorkplace(workplace.get());
            schedule.setUser(user);
            scheduleService.save(schedule);
            modelAndView.setViewName("redirect:/workplace/"+workplaceId);
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }

    @GetMapping("/{scheduleId}")
    ModelAndView showScheduleDetails(@PathVariable Long workplaceId,
                                     @PathVariable Long scheduleId,
                                     @AuthenticationPrincipal CurrentUser currentUser) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
        if (workplace.isPresent()) {
            Optional<Schedule> schedule = scheduleService.findById(scheduleId);
            if (schedule.isPresent()) {
                List<Duty> duties = dutyService.findAllByScheduleId(scheduleId);
                duties.sort(Comparator.comparing(Duty::getStart));
                User user = userService.findUserByUsername(currentUser.getUsername());
                modelAndView.addObject("duties", duties);
                modelAndView.addObject("user", user);
                modelAndView.addObject("schedule", schedule.get());
                modelAndView.addObject("workplace",workplace.get());
                //int dayNumbers = daysNumberInMonth(schedule.get().getMonth(), schedule.get().getYear());
                //modelAndView.addObject("dayNumber", dayNumbers);
                modelAndView.setViewName("schedule/details");
                return modelAndView;
            }
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }

    public int daysNumberInMonth(int month, int year) {
        LocalDate monthOfSchedule = LocalDate.of(year, month, 1);
        return monthOfSchedule.getDayOfMonth();
    }
}
