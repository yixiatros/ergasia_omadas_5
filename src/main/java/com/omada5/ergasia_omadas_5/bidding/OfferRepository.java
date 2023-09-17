package com.omada5.ergasia_omadas_5.bidding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("SELECT o FROM Offer o Where o.id = ?1")
    Optional<Offer> findOfferById(Long id);
}
