package com.omada5.ergasia_omadas_5.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long /*Id*/> {

    @Query("SELECT r FROM Role r WHERE r.name = ?1")
    Optional<Role> findByName(String name);
}
