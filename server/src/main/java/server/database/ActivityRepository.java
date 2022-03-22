package server.database;

import commons.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("SELECT a FROM Activity as a WHERE a.consumptionInWh >= ?1 and a.consumptionInWh <= ?2")
    public List<Activity> findActivitiesInRange(Long lowerBound, Long upperBound);
}