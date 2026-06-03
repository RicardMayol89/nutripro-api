package com.ricard.nutriproapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ricard.nutriproapi.model.TrackingEntry;

/**
 * Repositorio JPA para la entidad TrackingEntry.
 */
@Repository
public interface TrackingRepository extends JpaRepository<TrackingEntry, Long> {

    List<TrackingEntry> findAllByUserId(Long userId);
}
