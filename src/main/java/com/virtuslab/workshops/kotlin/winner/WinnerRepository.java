package com.virtuslab.workshops.kotlin.winner;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WinnerRepository extends JpaRepository<Winner, Integer> {

    Winner findByRunId(Integer runId);
}
