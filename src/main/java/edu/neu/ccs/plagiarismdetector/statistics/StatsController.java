package edu.neu.ccs.plagiarismdetector.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest Controller to handle the statistics requests
 *
 * @version 1.0
 */
@RestController
public class StatsController {

    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    /**
     * A mapping to retrieve a summary of all the statistics
     *
     * @return Long
     */
    @GetMapping("/api/stats/count")
    public long getCount() {
        return statsService.retrieveCount();
    }

    /**
     * A mapping to return the List of stats
     *
     * @return List<Stats>
     */
    @GetMapping("/api/stats")
    public List<Stats> getStats() {
        return statsService.allStats();
    }
}
