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

import edu.neu.ccs.plagiarismdetector.comparison.ComparisonType;
import edu.neu.ccs.plagiarismdetector.util.WeightProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;

import static edu.neu.ccs.plagiarismdetector.comparison.ComparisonType.LCS;
import static edu.neu.ccs.plagiarismdetector.comparison.ComparisonType.NW;

/**
 * Moss trained comparison strategy
 */
@Component
public class MossTrainedStrategy extends WeightBasedStrategy {

    /**
     * here we retrive th etrained weights that needs to be assigned
     * to the Strategies after training them with the moss result
     * @param weightProperties config props
     */
    @Autowired
    public MossTrainedStrategy(WeightProperties weightProperties) {
        this.typeWeightsMapping = new EnumMap<>(ComparisonType.class);
        this.typeWeightsMapping.put(LCS, weightProperties.getMossTrainedConfig().getStrategy1Weight());
        this.typeWeightsMapping.put(NW, weightProperties.getMossTrainedConfig().getStrategy2Weight());
    }
}
