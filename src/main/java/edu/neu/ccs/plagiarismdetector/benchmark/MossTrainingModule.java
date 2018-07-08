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

package edu.neu.ccs.plagiarismdetector.benchmark;

import edu.neu.ccs.plagiarismdetector.comparison.ComparisonBundle;
import edu.neu.ccs.plagiarismdetector.comparison.ComparisonType;
import edu.neu.ccs.plagiarismdetector.comparison.strategy.ComparisonStrategy;
import edu.neu.ccs.plagiarismdetector.comparison.strategy.LCSStrategy;
import edu.neu.ccs.plagiarismdetector.comparison.strategy.NWStrategy;
import edu.neu.ccs.plagiarismdetector.util.TrainingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

import static edu.neu.ccs.plagiarismdetector.comparison.ComparisonType.LCS;
import static edu.neu.ccs.plagiarismdetector.comparison.ComparisonType.NW;

/**
 * Implemented a training model to train the weights
 * assigned to the implemented strategies such that
 * the results are more aligned to Stanford's MOSS results
 */
@Component
public class MossTrainingModule {

    private Double strategy1Weight;
    private Double strategy2Weight;

    private final MossService mossService;
    private final TrainingProperties trainingProperties;

    @Autowired
    public MossTrainingModule(MossService mossService, TrainingProperties trainingProperties) {
        this.mossService = mossService;
        this.trainingProperties = trainingProperties;
        this.strategy1Weight = trainingProperties.getInitialWeight();
        this.strategy2Weight = trainingProperties.getInitialWeight();
    }

    /**
     * @param path             location of the files
     * @param comparisonBundle input data
     * @return the trained weights for the strategies using moss weight as the benchmark
     */
    Map<ComparisonType, Double> train(String path, ComparisonBundle comparisonBundle) {
        Map<ComparisonType, Double> strategyWeights = new EnumMap<>(ComparisonType.class);
        double mossScore = mossService.getMatch(path);

        double delta = trainingProperties.getDelta();
        int maxIterations = trainingProperties.getMaxIterations();

        int iterations = 0;

        ComparisonStrategy strategy = new LCSStrategy();
        Double strategy1Score = strategy.compare(comparisonBundle, null).getMetricList().get(0).getMatchPercentage();

        strategy = new NWStrategy();
        Double strategy2Score = strategy.compare(comparisonBundle, null).getMetricList().get(0).getMatchPercentage();

        double totalpercent = strategy1Weight * strategy1Score + strategy2Weight * strategy2Score;

        while (Math.abs(mossScore - totalpercent) > delta && iterations < maxIterations) {
            iterations++;
            double diff1Score = Math.abs(strategy1Score - mossScore);
            double diff2Score = Math.abs(strategy2Score - mossScore);

            if (diff2Score < diff1Score) {
                strategy1Weight = strategy1Weight - trainingProperties.getAdjustValue();
                strategy2Weight = strategy2Weight + trainingProperties.getAdjustValue();
            } else {
                strategy1Weight = strategy1Weight + trainingProperties.getAdjustValue();
                strategy2Weight = strategy2Weight - trainingProperties.getAdjustValue();
            }
            totalpercent = strategy1Weight * strategy1Score + strategy2Weight * strategy2Score;
        }

        strategyWeights.put(LCS, strategy1Weight);
        strategyWeights.put(NW, strategy2Weight);

        return strategyWeights;
    }

}