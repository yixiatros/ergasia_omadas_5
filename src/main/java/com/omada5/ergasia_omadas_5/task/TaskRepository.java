package com.omada5.ergasia_omadas_5.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.title = ?1")
    List<Task> findByTitle(String title);

    @Query("SELECT t FROM Task t WHERE t.title LIKE %?1%")
    List<Task> findTasksBySearch(String search);

    @Query("SELECT t FROM Task t WHERE t.id = ?1")
    Optional<Task> findTaskById(Long taskId);
}
