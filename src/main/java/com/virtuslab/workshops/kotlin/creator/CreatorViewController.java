package com.virtuslab.workshops.kotlin.creator;

import com.virtuslab.workshops.kotlin.run.RunService;
import com.virtuslab.workshops.kotlin.run.dto.CreateRunRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class CreatorViewController {
    private final RunService runService;

    public CreatorViewController(RunService runService) {
        Objects.requireNonNull(runService);
        this.runService = runService;
    }

    @GetMapping(value = "/created-runs")
    public String createdRuns(Model model) {
        model.addAttribute("runs", runService.allRunsCreatedByUser());
        return "created-runs";
    }

    @PostMapping(value = "/runs")
    public String createRun(CreateRunRequest createRunRequest) {
        runService.createRun(createRunRequest);
        return "redirect:/created-runs";
    }

    @GetMapping("/create-run")
    public String createRunForm(Model model) {
        model.addAttribute("createRunRequest", new CreateRunRequest());
        return "create-run";
    }

}
