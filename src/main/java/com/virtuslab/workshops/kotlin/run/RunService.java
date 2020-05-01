package com.virtuslab.workshops.kotlin.run;

import com.virtuslab.workshops.kotlin.run.dto.CreateRunRequest;
import com.virtuslab.workshops.kotlin.run.dto.RunDetails;
import com.virtuslab.workshops.kotlin.security.AuthenticatedUserService;
import com.virtuslab.workshops.kotlin.user.dto.SkinnyUserDto;
import com.virtuslab.workshops.kotlin.user.model.User;
import com.virtuslab.workshops.kotlin.winner.WinnerService;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
public class RunService {
    private static final String EMPTY_FIRST_NAME = "";
    private static final String EMPTY_LAST_NAME = "";

    private final RunRepository runRepository;
    private final WinnerService winnerService;
    private final AuthenticatedUserService authService;
    private final Clock clock;

    public RunService(RunRepository runRepository, WinnerService winnerService, AuthenticatedUserService authService, Clock clock) {
        Objects.requireNonNull(runRepository);
        Objects.requireNonNull(winnerService);
        Objects.requireNonNull(authService);
        Objects.requireNonNull(clock);

        this.runRepository = runRepository;
        this.winnerService = winnerService;
        this.authService = authService;
        this.clock = clock;
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

    //    TODO [hbysiak] we shouldn't return entity
    public RunDetails createRun(CreateRunRequest createRunRequest) {
        return authService.authenticatedUser()
                .map(user -> new Run(
                        createRunRequest.getPlace(),
                        createRunRequest.getName(),
                        createRunRequest.getDescription(),
                        user,
                        createRunRequest.getDistance(),
                        createRunRequest.getCapacity(),
                        createRunRequest.getDate(),
                        createRunRequest.getStartTime()))
                .map(runRepository::save)
                .map(this::runAsDetails)
                .orElseThrow(() -> new IllegalStateException("Couldn't create run"));
    }

    public RunDetails findById(Integer id) {
        return runRepository.findById(id)
                .map(this::runAsDetails)
                .orElseThrow(() -> new IllegalStateException("Couldn't find run"));
    }

    public Pair<String, List<SkinnyUserDto>> getRunNameAndParticipants(Integer id) {
        return runRepository.findById(id)
                .map(run -> Pair.of(
                        run.getName(),
                        run.getParticipants().stream()
                                .map(user -> new SkinnyUserDto(
                                        user.getFirstName(),
                                        user.getLastName(),
                                        user.getEmail()
                                ))
                                .collect(toList())))
                .orElseThrow(() -> new IllegalStateException("Couldn't find run"));
    }

    public void deleteById(Integer runId) {
        runRepository.deleteById(runId);
    }

    public List<RunDetails> findFinishedRuns() {
        LocalDate today = LocalDate.now(clock);
        return runRepository
                .findByDateBefore(today)
                .stream()
                .map(run ->
                        runAsDetails(run)
                )
                .collect(toList());
    }

    private List<RunDetails> runsAsDetails(List<Run> runs) {
        List<RunDetails> runDetails = new ArrayList<>();
        for (Run run : runs) {
            runDetails.add(runAsDetails(run));
        }
        return runDetails;
    }

    private RunDetails runAsDetails(Run run) {
        User winner = winnerService.findWinnerByRunId(run.getId()).orElse(null);
        return new RunDetails(run.getId(), run.getPlace(), run.getName(), run.getDescription(), run.getDate(), run.getStartTime(), run.getDistanceInMeters(), run.getPlacesLeft(), winner);
    }
}
