package com.virtuslab.workshops.kotlin.run;

import com.virtuslab.workshops.kotlin.user.model.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunRepository extends JpaRepository<Run, Integer> {
    List<Run> findByCreator(User creator);

    List<Run> findByParticipantsContaining(User participant);

    List<Run> findByDateBefore(LocalDate queryDate);
}
