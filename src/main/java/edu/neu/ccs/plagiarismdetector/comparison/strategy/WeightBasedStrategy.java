/*
 * Copyright (c) 2018. Team-108 Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License.  You may obtain a copy
 *  of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package edu.neu.ccs.plagiarismdetector.comparison.strategy;

import edu.neu.ccs.plagiarismdetector.comparison.ComparisonBundle;
import edu.neu.ccs.plagiarismdetector.comparison.ComparisonType;
import edu.neu.ccs.plagiarismdetector.report.DiffStatistics;
import edu.neu.ccs.plagiarismdetector.report.SimilarityMetric;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static edu.neu.ccs.plagiarismdetector.comparison.ComparisonType.LCS;
import static edu.neu.ccs.plagiarismdetector.comparison.ComparisonType.NW;

/**
 * Weight based strategy contract
 */
public abstract class WeightBasedStrategy implements ComparisonStrategy {
    /**
     * type-weights mapping
     */
    Map<ComparisonType, Double> typeWeightsMapping;

    /**
     * The comparison bundle of the diff statistics to the user code
     * @param comparisonBundle input data
     */
    @Override
    public DiffStatistics compare(ComparisonBundle comparisonBundle,
                                  Map<ComparisonType, DiffStatistics> diffStatisticsList) {
        List<SimilarityMetric> newList = new ArrayList<>();

        Set<Map.Entry<ComparisonType, DiffStatistics>> weightedDiffStatisticsList =
                diffStatisticsList.entrySet().stream()
                        .filter(entry -> (entry.getKey() == LCS || entry.getKey() == NW))
                        .collect(Collectors.toSet());

        for (Map.Entry<ComparisonType, DiffStatistics> entry : weightedDiffStatisticsList) {
            ComparisonType type = entry.getKey();
            DiffStatistics statistics = entry.getValue();

            int i = 0;
            for (SimilarityMetric metric : statistics.getMetricList()) {
                Double newMatch = metric.getMatchPercentage() * this.typeWeightsMapping.get(type) * 0.01;
                SimilarityMetric newMetric = null;
                if (newList.size() > i)
                    newMetric = newList.get(i);
                if (newMetric == null) {
                    newMetric = new SimilarityMetric(newMatch, metric.getStudent1Identifier(),
                            metric.getStudent2Identifier());
                    newList.add(newMetric);
                } else {
                    newMetric.setMatchPercentage(newMetric.getMatchPercentage() + newMatch);
                }
                i++;
            }
        }
        return new DiffStatistics(newList);
    }

    /**
     * To get the mapping of the assigned weight percentage of each strategy
     * @return The weight mapping for comparison type to weight
     */
    public Map<ComparisonType, Double> getTypeWeightsMapping() {
        return typeWeightsMapping;
    }

    /**
     * To set the mapping of the assigned weight percentage of each strategy
     * @param typeWeightsMapping the comparison type to weight mapping
     */
    public void setTypeWeightsMapping(Map<ComparisonType, Double> typeWeightsMapping) {
        this.typeWeightsMapping = typeWeightsMapping;
    }
}
