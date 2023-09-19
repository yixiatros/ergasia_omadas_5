package com.omada5.ergasia_omadas_5.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {

    @Query("SELECT t FROM TaskComment t Where t.id = ?1")
    Optional<TaskComment> findTaskCommentById(Long id);
}
