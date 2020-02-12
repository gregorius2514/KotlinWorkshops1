package com.virtuslab.workshops.kotlin.run;

import com.virtuslab.workshops.kotlin.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RunRepository extends JpaRepository<Run, Integer> {
    List<Run> findByCreator(User creator);

    List<Run> findByParticipantsContaining(User participant);
}
