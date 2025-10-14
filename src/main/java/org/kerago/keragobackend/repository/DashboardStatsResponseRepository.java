package org.kerago.keragobackend.repository;

import org.kerago.keragobackend.model.DashboardStatsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardStatsResponseRepository extends JpaRepository<DashboardStatsResponse,Long> {
}
