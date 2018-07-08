package edu.neu.ccs.plagiarismdetector.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the Stats service
 *
 * @version 1.0
 */
@Service
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    /**
     * Constructor to initialize repository
     *
     * @param statsRepository Repository
     */
    @Autowired
    public StatsServiceImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    /**
     * Retreive the count of the stats repository
     *
     * @return long
     */
    @Override
    public long retrieveCount() {
        return statsRepository.count();
    }

    /**
     * Save a stats object in the repository
     *
     * @param stats : stats object
     * @return :
     */
    @Override
    public Stats saveStat(Stats stats) {
        return statsRepository.save(stats);
    }

    /**
     * Return all the statistics stored in the table
     *
     * @return : a list of stats
     */
    @Override
    public List<Stats> allStats() {
        List<Stats> stats = new ArrayList<>();
        Iterable<Stats> statistics = statsRepository.findAll();

        for (Stats stat : statistics) {
            Stats tempStat = new Stats(stat);
            stats.add(tempStat);
        }
        return stats;
    }
}
