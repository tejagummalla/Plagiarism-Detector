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

package edu.neu.ccs.plagiarismdetector.comparison;

import edu.neu.ccs.plagiarismdetector.comparison.factory.ComparisonStrategyTypeFactory;
import edu.neu.ccs.plagiarismdetector.comparison.strategy.MossTrainedStrategy;
import edu.neu.ccs.plagiarismdetector.comparison.strategy.WeightedPolynomialStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyFactoryTest {

    @Autowired
    private ComparisonStrategyTypeFactory comparisonStrategyTypeFactory;

    @Test
    public void testWeightedPolynomialStrategy() {
        assertThat(comparisonStrategyTypeFactory.getComparisonStrategy
                (ComparisonType.WEIGHTED)).isInstanceOf(WeightedPolynomialStrategy.class);
    }

    @Test
    public void testMossTrainedStrategy() {
        assertThat(comparisonStrategyTypeFactory.getComparisonStrategy
                (ComparisonType.MOSS_TRAINED)).isInstanceOf(MossTrainedStrategy.class);
    }
}
