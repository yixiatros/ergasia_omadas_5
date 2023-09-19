package com.omada5.ergasia_omadas_5.user;

import com.omada5.ergasia_omadas_5.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long /* id */> {

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    Optional<User> findUserById(Long userId);

    @Query("SELECT t FROM Task t WHERE t.creator.id = ?1")
    List<Task> findTasksOfUserById(Long userId);

    @Query("SELECT u FROM User u WHERE u.username LIKE %?1%")
    List<User> findUsersBySearch(String search);
}
