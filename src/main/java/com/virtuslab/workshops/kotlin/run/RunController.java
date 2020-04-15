package com.virtuslab.workshops.kotlin.run;

import com.virtuslab.workshops.kotlin.user.dto.SkinnyUserDto;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class RunController {
    private final RunService runService;

    public RunController(RunService runService) {
        Objects.requireNonNull(runService);
        this.runService = runService;
    }

    @GetMapping(value = "/runs/{runId}")
    public String runDetails(@PathVariable Integer runId, Model model) {
        model.addAttribute("run", runService.findById(runId));
        return "run-details";
    }

    @GetMapping(value = "/run/{runId}/participants")
    public String runParticipants(@PathVariable Integer runId, Model model) {
        Pair<String, List<SkinnyUserDto>> runNameAndParticipants = runService.getRunNameAndParticipants(runId);
        model.addAttribute("runName", runNameAndParticipants.getFirst());
        model.addAttribute("participants", runNameAndParticipants.getSecond());
        return "run-participants";
    }

    @PostMapping(value = "/runs/{runId}")
    public String participateInRun(@PathVariable Integer runId) {
        runService.participateInRun(runId);
        return "redirect:/";
    }

    @GetMapping(value = "/runs/delete/{runId}")
    public String removeRun(@PathVariable Integer runId) {
        runService.deleteById(runId);
        return "redirect:/created-runs";
    }
}
