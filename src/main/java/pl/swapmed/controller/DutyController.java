package pl.swapmed.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.swapmed.model.Duty;
import pl.swapmed.model.Schedule;
import pl.swapmed.model.Workplace;
import pl.swapmed.service.DutyService;
import pl.swapmed.service.ScheduleService;
import pl.swapmed.service.WorkplaceService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/workplace/{id}/schedule/{scheduleId}/duty")
public class DutyController {

    private final DutyService dutyService;
    private final WorkplaceService workplaceService;
    private final ScheduleService scheduleService;


    public DutyController(DutyService dutyService,
                          WorkplaceService workplaceService,
                          ScheduleService scheduleService) {
        this.dutyService = dutyService;
        this.workplaceService = workplaceService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("")
    public ModelAndView myDuty(@PathVariable Long id,
                               @PathVariable Long scheduleId) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Workplace> workplace = workplaceService.findById(id);
        if (workplace.isPresent()) {
            Optional<Schedule> schedule = scheduleService.findById(scheduleId);
            if (schedule.isPresent()) {
                List<Duty> dutyList = dutyService.findAllByScheduleId(scheduleId);
                modelAndView.addObject("workplace", workplace);
                modelAndView.addObject("schedule", schedule);
                modelAndView.addObject("dutyList", dutyList);
                modelAndView.setViewName("/duty/list");
                return modelAndView;
            }
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addDuty(@PathVariable Long id,
                                @PathVariable Long scheduleId) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Workplace> workplace = workplaceService.findById(id);
        if (workplace.isPresent()) {
            Optional<Schedule> schedule = scheduleService.findById(scheduleId);
            if (schedule.isPresent()) {
                modelAndView.addObject("workplace", workplace.get());
                modelAndView.addObject("schedule", schedule);
                modelAndView.addObject("duty", new Duty());
                modelAndView.setViewName("/duty/add");
                return modelAndView;
            }
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addDutyToSchedule(@Valid Duty duty, BindingResult bindingResult,
                                          @PathVariable Long id,
                                          @PathVariable Long scheduleId) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Workplace> workplace = workplaceService.findById(id);
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
                dutyService.save(duty);
                modelAndView.setViewName("/duty/add");
                return modelAndView;
            }
        }
        modelAndView.setViewName("redirect:/error");
        return modelAndView;
    }

}
