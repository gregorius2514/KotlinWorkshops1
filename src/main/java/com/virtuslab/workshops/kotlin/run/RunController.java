package com.virtuslab.workshops.kotlin.run;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class RunController {
    private final RunService runService;

    public RunController(RunService runService) {
        Objects.requireNonNull(runService);
        this.runService = runService;
    }

    @PostMapping(value = "/runs/{runId}")
    public String participateInRun(@PathVariable Integer runId, HttpServletRequest request) {
        runService.participateInRun(runId);
        return "redirect:/";
    }

}
