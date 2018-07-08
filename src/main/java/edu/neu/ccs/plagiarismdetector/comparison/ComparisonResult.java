package edu.neu.ccs.plagiarismdetector.comparison;

import edu.neu.ccs.plagiarismdetector.report.DiffStatistics;

import java.util.Map;

/**
 * The class is a pojo for returning the final comparison strategy
 * for a single run of compare files
 */
public class ComparisonResult {

    private ComparisonBundle comparisonBundle;

    public void setDiffStatisticsList(Map<ComparisonType, DiffStatistics> diffStatisticsList) {
        this.diffStatisticsList = diffStatisticsList;
    }

    private Map<ComparisonType, DiffStatistics> diffStatisticsList;

    /**
     * Set the comparison results
     * @param comparisonBundle   The concatenated files of each user
     * @param diffStatisticsList The result of runs of of all comparison
     *                           strategies
     */
    public ComparisonResult(ComparisonBundle comparisonBundle, Map<ComparisonType, DiffStatistics> diffStatisticsList) {
        this.comparisonBundle = comparisonBundle;
        this.diffStatisticsList = diffStatisticsList;
    }

    /**
     * @return The concatenated files of each user
     */
    public ComparisonBundle getComparisonBundle() {
        return comparisonBundle;
    }

    /**
     * @param comparisonBundle The concatenated files of each user
     */
    public void setComparisonBundle(ComparisonBundle comparisonBundle) {
        this.comparisonBundle = comparisonBundle;
    }

    /**
     * @return The result of runs of of all comparison
     * strategies
     */
    public Map<ComparisonType, DiffStatistics> getDiffStatisticsList() {
        return diffStatisticsList;
    }

}
