package edu.neu.ccs.plagiarismdetector.statistics;

import java.util.List;

/**
 * Provide services to save and retrieve statistics
 *
 * @version 1.0
 */
public interface StatsService {

    /**
     * Retrieve the count of the stats repository
     */
    long retrieveCount();

    /**
     * Save a stats objects in the repository
     */
    Stats saveStat(Stats stats);

    /**
     * Retrieve all stats
     */
    List<Stats> allStats();
}
