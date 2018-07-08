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

package edu.neu.ccs.plagiarismdetector.comparison.factory;

import edu.neu.ccs.plagiarismdetector.comparison.ComparisonType;
import edu.neu.ccs.plagiarismdetector.comparison.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory for creating comparison strategies
 */
@Component
public class ComparisonStrategyTypeFactory {

    private final LCSStrategy lcsStrategy;
    private final NWStrategy nwStrategy;
    private final WeightedPolynomialStrategy weightedPolynomialStrategy;
    private final MossTrainedStrategy mossTrainedStrategy;

    /**
     * constructor for the class to set the require
     * private fields.
     */
    @Autowired
    private ComparisonStrategyTypeFactory(LCSStrategy lcsStrategy,
                                          NWStrategy nwStrategy,
                                          WeightedPolynomialStrategy weightedPolynomialStrategy,
                                          MossTrainedStrategy mossTrainedStrategy) {
        super();
        this.lcsStrategy = lcsStrategy;
        this.nwStrategy = nwStrategy;
        this.weightedPolynomialStrategy = weightedPolynomialStrategy;
        this.mossTrainedStrategy = mossTrainedStrategy;
    }

    /**
     * Chose the strategy based on the passed enum types.
     * @param cType comparison type
     * @return a comparison strategy
     */
    public ComparisonStrategy getComparisonStrategy(ComparisonType cType) {
        switch (cType) {
            case LCS:
                return lcsStrategy;
            case NW:
                return nwStrategy;
            case WEIGHTED:
                return weightedPolynomialStrategy;
            case MOSS_TRAINED:
                return mossTrainedStrategy;
            default:
                return lcsStrategy;
        }
    }
}
