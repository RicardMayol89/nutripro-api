package com.ricard.nutriproapi.repository;

import com.ricard.nutriproapi.model.TrackingEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad TrackingEntry.
 */
@Repository
public interface TrackingRepository extends JpaRepository<TrackingEntry, Long> {

    List<TrackingEntry> findAllByUserId(Long userId);
}
