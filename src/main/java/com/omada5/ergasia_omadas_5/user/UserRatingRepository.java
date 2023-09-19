package com.omada5.ergasia_omadas_5.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRatingRepository extends JpaRepository<UserRating, Long> {

    @Query("SELECT u FROM UserRating u WHERE u.id = ?1")
    Optional<UserRating> findUserRatingById(Long id);

    @Query("SELECT u FROM UserRating u WHERE u.userWhoIsRated.id = ?1")
    List<UserRating> findUserRatingByUserWhoIsRated(Long userId);
}
