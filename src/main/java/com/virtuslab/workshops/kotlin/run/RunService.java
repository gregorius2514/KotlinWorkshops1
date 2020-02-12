package com.virtuslab.workshops.kotlin.run;

import com.virtuslab.workshops.kotlin.run.dto.CreateRunRequest;
import com.virtuslab.workshops.kotlin.run.dto.RunDetails;
import com.virtuslab.workshops.kotlin.security.AuthenticatedUserService;
import com.virtuslab.workshops.kotlin.user.model.User;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RunService {
    private final RunRepository runRepository;
    private final AuthenticatedUserService authService;

    public RunService(RunRepository runRepository, AuthenticatedUserService authService) {
        Objects.requireNonNull(runRepository);
        Objects.requireNonNull(authService);
        this.runRepository = runRepository;
        this.authService = authService;
    }

    public List<RunDetails> allRuns() {
        return runsAsDetails(runRepository.findAll());
    }

    public List<RunDetails> allRunsCreatedByUser() { //TODO maybe we shall parametrize this method with userId?
        return authService.authenticatedUser()
                .map(runRepository::findByCreator)
                .map(this::runsAsDetails)
                .orElseThrow(() -> new IllegalStateException(("Couldn't find user's runs")));
    }

    public List<RunDetails> allRunsUserParticipateIn() { //TODO maybe we shall parametrize this method with userId?
        return authService.authenticatedUser()
                .map(runRepository::findByParticipantsContaining)
                .map(this::runsAsDetails)
                .orElseThrow(() -> new IllegalStateException(("Couldn't find user's runs")));
    }

    public RunDetails participateInRun(Integer runId) {
        return authService.authenticatedUser()
                .flatMap(user -> runRepository
                        .findById(runId)
                        .map(run -> Pair.of(user, run)))
                .map(userRunPair -> {
                    Run run = userRunPair.getSecond();
                    User user = userRunPair.getFirst();
                    return run.addParticipant(user);
                })
                .map(runRepository::save)
                .map(this::runAsDetails)
                .orElseThrow(() -> new IllegalStateException("Couldn't participate in this run"));
    }

    public Run createRun(CreateRunRequest createRunRequest) {
        return authService.authenticatedUser()
                .map(user -> new Run(
                        createRunRequest.getPlace(),
                        user,
                        createRunRequest.getDistance(),
                        createRunRequest.getCapacity()))
                .map(runRepository::save)
                .orElseThrow(() -> new IllegalStateException("Couldn't create run"));
    }

    private List<RunDetails> runsAsDetails(List<Run> runs) {
        List<RunDetails> runDetails = new ArrayList<RunDetails>();
        for (Run run : runs) {
            runDetails.add(runAsDetails(run));
        }
        return runDetails;
    }

    private RunDetails runAsDetails(Run run) {
        return new RunDetails(run.getId(), run.getPlace(), run.getDistanceInMeters(), run.getPlacesLeft());
    }
}