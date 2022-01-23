package pl.swapmed.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.swapmed.service.DutyService;
import pl.swapmed.service.ScheduleService;
import pl.swapmed.service.WorkplaceService;

@Controller
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

    @GetMapping("/duty")
    public ModelAndView myDuty() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/duty/list");
        return modelAndView;
    }

    @GetMapping("/duty/add")
    public ModelAndView addDuty() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/duty/add");
        return modelAndView;
    }
}
