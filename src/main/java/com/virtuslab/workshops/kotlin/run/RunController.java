package com.virtuslab.workshops.kotlin.run;

import com.virtuslab.workshops.kotlin.run.dto.RunDetails;
import com.virtuslab.workshops.kotlin.run.dto.WinnerDto;
import com.virtuslab.workshops.kotlin.user.UserService;
import com.virtuslab.workshops.kotlin.user.dto.SkinnyUserDto;
import com.virtuslab.workshops.kotlin.winner.WinnerService;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RunController {
    private final RunService runService;
    private final UserService userService;
    private final WinnerService winnerService;

    public RunController(RunService runService, UserService userService, WinnerService winnerService) {
        Objects.requireNonNull(runService);
        Objects.requireNonNull(userService);
        Objects.requireNonNull(winnerService);

        this.runService = runService;
        this.userService = userService;
        this.winnerService = winnerService;
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

    @GetMapping(value = "/runs/statistics")
    public String runsStatistics(Model model) {
        List<RunDetails> finishedRuns = runService.findFinishedRuns();
        model.addAttribute("runs", finishedRuns);
        return "runs-statistics";
    }

    @GetMapping(value = "/runs/add/winner/{runId}")
    public String addRunWinnerView(@PathVariable Integer runId, Model model) {
        RunDetails run = runService.findById(runId);
        if (run.getDate().isAfter(LocalDate.now())) {
            // TODO: 09.06.2020 Add information and display on UI that only for finished runs
            //      can be added winner
            return "run-details";
        } else {
            model.addAttribute("run", run);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("winnerDto", new WinnerDto());
            return "run-add-winner";
        }
    }

    @PostMapping(value = "/runs/add/winner")
    public String addRunWinner(WinnerDto winnerDto, Model model) {

        Integer updatedRunId = winnerService.addRunWinner(winnerDto.getRunId(), winnerDto.getWinnerId());
        model.addAttribute("run", runService.findById(updatedRunId));

        return "run-details";
    }
}
