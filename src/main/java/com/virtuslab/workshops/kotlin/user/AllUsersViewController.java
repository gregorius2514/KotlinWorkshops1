package com.virtuslab.workshops.kotlin.user;

import com.virtuslab.workshops.kotlin.run.RunService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class AllUsersViewController {
    private final RunService runService;

    public AllUsersViewController(RunService runService) {
        Objects.requireNonNull(runService);
        this.runService = runService;
    }

    @GetMapping(value = "/all-runs")
    public String allRuns(Model model) {
        model.addAttribute("runs", runService.allRuns());
        return "runs";
    }

    @GetMapping(value = "/participates-in-runs")
    public String allRunsUserParticipateIn(Model model) {
        model.addAttribute("runs", runService.allRunsUserParticipateIn());
        return "runs-user-participate-in";
    }
}