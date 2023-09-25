package com.omada5.ergasia_omadas_5.task;

import com.omada5.ergasia_omadas_5.bidding.Offer;
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

    @Query("SELECT o FROM Offer o WHERE o.task.id = ?1 AND o.isActive")
    List<Offer> findActiveOffersOfTaskById(Long taskId);

    @Query("SELECT o FROM Offer o WHERE o.task.id = ?1 AND o.bidder.username = ?2 AND o.isActive")
    List<Offer> findOffersOfUserToTask(Long taskId, String username);

    @Query("SELECT t FROM TaskComment t WHERE t.task.id = ?1")
    List<TaskComment> findCommentsOfTask(Long taskId);

    @Query("SELECT t FROM Task t WHERE t.assignedDeveloper.id = ?1 AND t.developerHasCompleted AND t.clientHasCompleted")
    List<Task> findNumberOfCompletedTasksOfUser(Long userId);
}
