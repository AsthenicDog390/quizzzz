package server.database;

import commons.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("SELECT DISTINCT a FROM Activity as a WHERE a.consumptionInWh >= ?1 and a.consumptionInWh <= ?2")
    List<Activity> findActivitiesInRange(Long lowerBound, Long upperBound);
    //query for getting all the distinct activities with a power consumption between "lower bound" and "upper bound" and returning them in a List
}