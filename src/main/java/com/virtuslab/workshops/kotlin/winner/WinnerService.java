package com.virtuslab.workshops.kotlin.winner;

import com.virtuslab.workshops.kotlin.run.RunRepository;
import com.virtuslab.workshops.kotlin.user.UserRepository;
import com.virtuslab.workshops.kotlin.user.model.User;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class WinnerService {

    private final WinnerRepository winnerRepository;
    private final RunRepository runRepository;
    private final UserRepository userRepository;

    public WinnerService(WinnerRepository winnerRepository, RunRepository runRepository, UserRepository userRepository) {
        Objects.requireNonNull(winnerRepository);
        Objects.requireNonNull(runRepository);
        Objects.requireNonNull(userRepository);

        this.winnerRepository = winnerRepository;
        this.runRepository = runRepository;
        this.userRepository = userRepository;
    }

    public Integer addRunWinner(Integer runId, Integer winnerId) {
        Objects.requireNonNull(runId);
        Objects.requireNonNull(winnerId);

        userRepository.findById(winnerId).ifPresent(user -> {
            runRepository.findById(runId).ifPresent(run -> {
                        Winner winner = winnerRepository.findByRunId(runId);
                        if (winner != null) {
                            winner.setUser(user);
                        } else {
                            winnerRepository.save(new Winner(run, user));
                        }
                    }
            );
        });

        return runId;
    }

    public Optional<User> findWinnerByRunId(Integer runId) {
        Winner winner = winnerRepository.findByRunId(runId);
        if (winner != null) {
            return Optional.of(winner.getUser());
        }
        return Optional.empty();
    }
}
