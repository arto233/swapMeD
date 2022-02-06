package pl.swapmed.controller;

import org.springframework.boot.Banner;
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
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/workplace/{workplaceId}/schedule/{scheduleId}/duty")
public class DutyController {

    private final DutyService dutyService;
    private final WorkplaceService workplaceService;
    private final ScheduleService scheduleService;
    private final UserService userService;
    private static Boolean hasSameDuty = false;
    private static Boolean hasBeforeOrAfterDuty = false;


    public DutyController(DutyService dutyService,
                          WorkplaceService workplaceService,
                          ScheduleService scheduleService, UserService userService) {
        this.dutyService = dutyService;
        this.workplaceService = workplaceService;
        this.scheduleService = scheduleService;
        this.userService = userService;
    }


    @GetMapping("/add")
    public ModelAndView addDuty(@PathVariable Long workplaceId,
                                @PathVariable Long scheduleId,
                                @AuthenticationPrincipal CurrentUser currentUser) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
        if (workplace.isPresent()) {
            Optional<Schedule> schedule = scheduleService.findById(scheduleId);
            if (schedule.isPresent()) {
                Duty duty = new Duty();
                List<Duty> duties = dutyService.findAllByScheduleId(scheduleId);
                duties.sort(Comparator.comparing(Duty::getStart));
                User user = userService.findUserByUsername(currentUser.getUsername());
                modelAndView.addObject("duties", duties);
                modelAndView.addObject("user", user);
                modelAndView.addObject("workplace", workplace.get());
                modelAndView.addObject("schedule", schedule.get());
                modelAndView.addObject("duty", duty);
                modelAndView.addObject("localDateTime", LocalDateTime.now());
                modelAndView.setViewName("/duty/add");
                return modelAndView;
            }
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addDutyToSchedule(@Valid Duty duty, BindingResult bindingResult,
                                          @PathVariable Long workplaceId,
                                          @PathVariable Long scheduleId,
                                          @AuthenticationPrincipal CurrentUser currentUser) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByUsername(currentUser.getUsername());
        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
        if (workplace.isPresent()) {
            Optional<Schedule> schedule = scheduleService.findById(scheduleId);
            if (schedule.isPresent()) {
                if (bindingResult.hasErrors()) {
                    modelAndView.setViewName("/duty/add");
                    return modelAndView;
                }
                duty.setStart(duty.getStart());
                duty.setEnd(duty.getEnd());
                duty.setSchedule(schedule.get());
                duty.setUser(user);
                dutyService.save(duty);
                modelAndView.setViewName("redirect:/workplace/" + workplaceId + "/schedule/" + scheduleId + "/duty/add");
                return modelAndView;
            }
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }

    @GetMapping("/{dutyId}/delete")
    public ModelAndView dutyRemove(@PathVariable Long workplaceId,
                                   @PathVariable Long scheduleId,
                                   @PathVariable Long dutyId) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
        if (workplace.isPresent()) {
            Optional<Schedule> schedule = scheduleService.findById(scheduleId);
            if (schedule.isPresent()) {
                Optional<Duty> duty = dutyService.findById(dutyId);
                if (duty.isPresent()) {
                    modelAndView.addObject("workplace", workplace);
                    modelAndView.addObject("schedule", schedule);
                    modelAndView.addObject("duty", duty);
                    modelAndView.setViewName("duty/delete");
                    return modelAndView;
                }
            }

        }


        modelAndView.setViewName("redirect:/error");

        return modelAndView;
    }

    @PostMapping("/{dutyId}/delete")
    public ModelAndView dutyRemoveConfirm(@PathVariable Long workplaceId,
                                          @PathVariable Long scheduleId,
                                          @PathVariable Long dutyId) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
        if (workplace.isPresent()) {
            Optional<Schedule> schedule = scheduleService.findById(scheduleId);
            if (schedule.isPresent()) {
                Optional<Duty> duty = dutyService.findById(dutyId);
                if (duty.isPresent()) {
                    dutyService.delete(dutyId);
                    modelAndView.setViewName("redirect:/workplace/" + workplaceId + "/schedule/" + scheduleId + "/duty/add");
                    return modelAndView;
                }
            }
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }

    @GetMapping("/{dutyId}/findShift")
    public ModelAndView dutyShift(@PathVariable Long workplaceId,
                                  @PathVariable Long scheduleId,
                                  @PathVariable Long dutyId,
                                  @AuthenticationPrincipal CurrentUser currentUser) {

        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByUsername(currentUser.getUsername());
        Optional<Workplace> workplace = workplaceService.findById(workplaceId);
        if (workplace.isPresent()) {
            Optional<Schedule> schedule = scheduleService.findById(scheduleId);
            if (schedule.isPresent()) {
                Optional<Duty> duty = dutyService.findById(dutyId);
                if (duty.isPresent()) {
                    List<User> possibleShifts = new ArrayList<>();
                    List<User> allUsersInSchedule = userService.findAllUsersToShift(workplaceId, schedule.get().getMonth(), schedule.get().getYear());
                    for (User userToShift : allUsersInSchedule) {
                        Set<Duty> userToShiftDuties = userToShift.getDuties();
                        hasSameDuty = false;
                        hasBeforeOrAfterDuty = false;
                        for (Duty userToShiftDuty : userToShiftDuties) {

                            if ((userToShiftDuty.getStart().equals(duty.get().getStart())) &&
                                    (userToShiftDuty.getEnd().equals(duty.get().getEnd()))) {
                                hasSameDuty = true;
                            }
                        }
                        if (hasSameDuty == false) {
                            possibleShifts.add(userToShift);
                        }
                    }

                modelAndView.addObject("workplace", workplace.get());
                modelAndView.addObject("schedule", schedule.get());
                modelAndView.addObject("duty", duty.get());
                modelAndView.addObject("possibleShifts", possibleShifts);
                modelAndView.setViewName("duty/shiftList");
                return modelAndView;
            }
        }
    }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
}

}
