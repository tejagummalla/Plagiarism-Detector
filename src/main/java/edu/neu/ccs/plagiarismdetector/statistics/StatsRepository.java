package edu.neu.ccs.plagiarismdetector.statistics;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * A stats repository which used the crud repository
 *
 * @version 1.0
 */
@Repository
public interface StatsRepository extends CrudRepository<Stats, Long> {
}
