package com.virtuslab.workshops.kotlin.run;

import com.virtuslab.workshops.kotlin.UserBuilder;
import com.virtuslab.workshops.kotlin.run.dto.CreateRunRequest;
import com.virtuslab.workshops.kotlin.run.dto.RunDetails;
import com.virtuslab.workshops.kotlin.security.AuthenticatedUserService;
import com.virtuslab.workshops.kotlin.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RunServiceTest {

    private RunService runService;
    private List<Run> inMemoryRunsDatabase = new ArrayList<>();
    private RunRepository runRepository;
    private AuthenticatedUserService authService;
    private Clock fixedClock;
    private User loggedInUser;

    @BeforeEach
    private void before() {
        fixedClock = Clock.fixed(Instant.parse("2020-04-15T16:45:42.00Z"), ZoneId.systemDefault());

        runRepository = mock(RunRepository.class);
        authService = mock(AuthenticatedUserService.class);
        runService = new RunService(runRepository, authService);

        loggedInUser = UserBuilder.getInstance()
                .id(1)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("test@example.com")
                .build();

        when(authService.authenticatedUser())
                .thenReturn(Optional.of(loggedInUser));

        when(runRepository.findAll())
                .thenReturn(inMemoryRunsDatabase);

        when(runRepository.findByCreator(any(User.class)))
                .thenAnswer(answer -> {
                    User queryUser = answer.getArgument(0);

                    return inMemoryRunsDatabase
                            .stream()
                            .filter(user -> user.getCreator().equals(queryUser))
                            .collect(Collectors.toList());
                });

        when(runRepository.findByParticipantsContaining(any(User.class)))
                .thenAnswer(answer -> {
                    User queryUser = answer.getArgument(0);

                    return inMemoryRunsDatabase
                            .stream()
                            .filter(run -> run.getParticipants().contains(queryUser))
                            .collect(Collectors.toList());
                });

        when(runRepository.findById(any(Integer.class)))
                .thenAnswer(answer -> {
                    Integer queryRunId = answer.getArgument(0);

                    return inMemoryRunsDatabase
                            .stream()
                            .filter(run -> run.getId().equals(queryRunId))
                            .findFirst();
                });

        when(runRepository.save(any(Run.class)))
                .thenAnswer(anwer -> {
                    Run run = anwer.getArgument(0);
                    inMemoryRunsDatabase.add(run);

                    return run;
                });
    }

    @AfterEach
    private void after() {
        inMemoryRunsDatabase.clear();
    }

    @Test
    public void shouldReturnAllRuns() {
        // given
        List<Run> runs = RunTestDataMaker.getInstance()
                .createTestRuns(Arrays.asList(
                        new RunTestData(1,
                                UserBuilder.getInstance()
                                        .id(1)
                                        .build(),
                                UserBuilder.getInstance()
                                        .id(3)
                                        .build(),
                                fixedClock
                        ),
                        new RunTestData(2,
                                UserBuilder.getInstance()
                                        .id(1)
                                        .build(),
                                UserBuilder.getInstance()
                                        .id(4).build(),
                                fixedClock)
                ));
        runs.forEach(runRepository::save);
        List<RunDetails> expectedRuns = convertToRunDetails(runs);

        // when
        List<RunDetails> actualRuns = runService.allRuns();

        // then
        assertEquals(expectedRuns, actualRuns);
    }

    @Test
    public void shouldReturnRunsCreatedByUser() {
        // given
        User expectedUser = UserBuilder.getInstance()
                .id(1)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("test@example.com")
                .build();

        when(authService.authenticatedUser())
                .thenReturn(Optional.of(expectedUser));

        Run expectedRun = RunTestDataMaker.getInstance()
                .createTestRun(new RunTestData(
                        1,
                        expectedUser,
                        UserBuilder.getInstance()
                                .id(3)
                                .build(),
                        fixedClock));

        List<Run> runs = RunTestDataMaker.getInstance()
                .createTestRuns(Arrays.asList(
                        new RunTestData(1, expectedUser, UserBuilder.getInstance().id(2).build(), fixedClock),
                        new RunTestData(2, UserBuilder.getInstance().id(2).build(), UserBuilder.getInstance().id(3).build(), fixedClock),
                        new RunTestData(2, UserBuilder.getInstance().id(2).build(), UserBuilder.getInstance().id(4).build(), fixedClock)
                ));
        runs.forEach(runRepository::save);

        // when
        List<RunDetails> actualRuns = runService.allRunsCreatedByUser();

        // then
        assertEquals(convertToRunDetails(Arrays.asList(expectedRun)), actualRuns);
    }

    @Test
    public void shouldReturnRunsWhereUserParticipateIn() {
        // given
        List<Run> runs = RunTestDataMaker.getInstance()
                .createTestRuns(
                        Arrays.asList(
                                new RunTestData(1, UserBuilder.getInstance().id(2).build(), loggedInUser, fixedClock),
                                new RunTestData(2, UserBuilder.getInstance().id(2).build(), null, fixedClock),
                                new RunTestData(3, UserBuilder.getInstance().id(3).build(), loggedInUser, fixedClock)
                        )
                );
        runs.forEach(runRepository::save);
        List<RunDetails> expectedUserRuns = convertToRunDetails(Arrays.asList(runs.get(0), runs.get(2)));

        // when
        List<RunDetails> actualUserRuns = runService.allRunsUserParticipateIn();

        // then
        assertEquals(expectedUserRuns, actualUserRuns);
    }

    @Test
    public void shouldAddUserToRunAsParticipant() {
        // given
        List<Run> runs = RunTestDataMaker.getInstance()
                .createTestRuns(
                        Arrays.asList(
                                new RunTestData(
                                        1, UserBuilder.getInstance()
                                        .id(1)
                                        .build(),
                                        null,
                                        fixedClock
                                ),
                                new RunTestData(
                                        2, UserBuilder.getInstance()
                                        .id(2)
                                        .build(),
                                        null,
                                        fixedClock
                                )

                        )
                );
        runs.forEach(runRepository::save);
        RunDetails expectedRunDetails = new RunDetails(
                1, "Random place of the run", "Test run name", "Test run description",
                LocalDate.now(fixedClock), LocalTime.now(fixedClock), 999, 998
        );

        // when
        RunDetails actualRunDetails = runService.participateInRun(1);

        // then
        assertEquals(expectedRunDetails, actualRunDetails);
    }

    @Test
    public void createRun() {
        // given
        Run expectedRun2 = RunBuilder.getInstance()
                .place("random")
                .name("test run")
                .description("test description")
                .date(LocalDate.now(fixedClock))
                .startTime(LocalTime.now(fixedClock))
                .distanceInMeters(999)
                .participantsCapacity(999)
                .participants(new HashSet<>())
                .creator(loggedInUser)
                .build();

        CreateRunRequest createRunRequest = new CreateRunRequest(
                "random",
                "test run",
                "test description",
                LocalDate.now(fixedClock),
                LocalTime.now(fixedClock),
                999,
                999
        );

        // when
        Run actualRun = runService.createRun(createRunRequest);

        // then
        assertEquals(expectedRun2, actualRun);
    }

    @Test
    public void shouldFindRunById() {
        // given
        List<Run> runs =
                RunTestDataMaker.getInstance()
                        .createTestRuns(Arrays.asList(
                                new RunTestData(
                                        1,
                                        UserBuilder.getInstance()
                                                .id(1)
                                                .build(),
                                        UserBuilder.getInstance()
                                                .id(2)
                                                .build(),
                                        fixedClock),
                                new RunTestData(
                                        2,
                                        UserBuilder.getInstance()
                                                .id(3)
                                                .build(),
                                        UserBuilder.getInstance()
                                                .id(4)
                                                .build(),
                                        fixedClock)
                        ));
        runs.forEach(runRepository::save);

        Run expectedRun =
                RunTestDataMaker.getInstance()
                        .createTestRun(new RunTestData(
                                2,
                                UserBuilder.getInstance()
                                        .id(3).build(),
                                UserBuilder.getInstance()
                                        .id(4)
                                        .build(),
                                fixedClock));

        // when
        RunDetails actualRun = runService.findById(expectedRun.getId());

        // then
        assertEquals(convertToRunDetail(expectedRun), actualRun);
    }

    private List<RunDetails> convertToRunDetails(List<Run> runs) {
        return runs
                .stream()
                .map(this::convertToRunDetail)
                .collect(Collectors.toList());
    }

    private RunDetails convertToRunDetail(Run run) {
        return new RunDetails(
                run.getId(),
                run.getPlace(),
                run.getName(),
                run.getDescription(),
                run.getDate(),
                run.getStartTime(),
                run.getDistanceInMeters(),
                run.getPlacesLeft());
    }
}

class RunBuilder {
    private Integer id;
    private String place;
    private String name;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private Set<User> participants;
    private User creator;
    private Integer distanceInMeters;
    private Integer participantsCapacity;

    public static RunBuilder getInstance() {
        return new RunBuilder();
    }

    public RunBuilder id(Integer id) {
        this.id = id;
        return this;
    }

    public RunBuilder place(String place) {
        this.place = place;
        return this;
    }

    public RunBuilder name(String name) {
        this.name = name;
        return this;
    }

    public RunBuilder description(String description) {
        this.description = description;
        return this;
    }

    public RunBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public RunBuilder startTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public RunBuilder participants(Set<User> participants) {
        this.participants = participants;
        return this;
    }

    public RunBuilder creator(User creator) {
        this.creator = creator;
        return this;
    }

    public RunBuilder distanceInMeters(Integer distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
        return this;
    }

    public RunBuilder participantsCapacity(Integer participantsCapacity) {
        this.participantsCapacity = participantsCapacity;
        return this;
    }

    public Run build() {
        Run run = new Run();
        run.setId(this.id);
        run.setPlace(this.place);
        run.setName(this.name);
        run.setDescription(this.description);
        run.setCreator(this.creator);
        run.setDistanceInMeters(this.distanceInMeters);
        run.setParticipantsCapacity(this.participantsCapacity);
        run.setDate(this.date);
        run.setStartTime(this.startTime);
        run.setParticipants(this.participants);

        return run;
    }
}

class RunTestData {
    private int runId;
    private final LocalDate startDate;
    private final LocalTime startTime;
    private int participantsCapacity;
    private User creator;
    private Set<User> participants;

    public RunTestData(
            int runId,
            User creator,
            User participant,
            Clock fixedClock
    ) {
        this.runId = runId;
        this.creator = creator;
        this.startDate = LocalDate.now(fixedClock);
        this.startTime = LocalTime.now(fixedClock);
        this.participantsCapacity = 999;
        this.participants = new HashSet();
        if (participant != null) {
            this.participants.add(participant);
        }
    }

    public int getRunId() {
        return runId;
    }

    public User getCreator() {
        return creator;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public int getParticipantsCapacity() {
        return participantsCapacity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}

class RunTestDataMaker {

    public static RunTestDataMaker getInstance() {
        return new RunTestDataMaker();
    }

    public List<Run> createTestRuns(List<RunTestData> runTestData) {
        return runTestData
                .stream()
                .map(this::createRun)
                .collect(Collectors.toList());
    }

    public Run createTestRun(RunTestData runTestData) {
        return createRun(runTestData);
    }

    private Run createRun(RunTestData runTestData) {
        return RunBuilder.getInstance()
                .id(runTestData.getRunId())
                .creator(runTestData.getCreator())
                .participants(runTestData.getParticipants())
                .name("Test run name")
                .description("Test run description")
                .place("Random place of the run")
                .date(runTestData.getStartDate())
                .startTime(runTestData.getStartTime())
                .distanceInMeters(999)
                .participantsCapacity(runTestData.getParticipantsCapacity())
                .build();
    }
}