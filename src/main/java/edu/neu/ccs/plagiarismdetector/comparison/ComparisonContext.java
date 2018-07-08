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

import edu.neu.ccs.plagiarismdetector.comparison.strategy.ComparisonStrategy;
import edu.neu.ccs.plagiarismdetector.report.DiffStatistics;

import java.util.Map;

/**
 * context for the comparison
 */
public class ComparisonContext {
    /**
     * strategy to be implemented
     */
    private ComparisonStrategy comparisonStrategy;

    /**
     *
     * @param comparisonStrategy to be set
     */
    ComparisonContext(ComparisonStrategy comparisonStrategy) {
        this.comparisonStrategy = comparisonStrategy;
    }

    /**
     * @param comparisonBundle input information
     * @return a diff statistics for the strategy
     */
    public DiffStatistics compare(ComparisonBundle comparisonBundle,
                                  Map<ComparisonType, DiffStatistics> diffStatisticsList) {
        return comparisonStrategy.compare(comparisonBundle, diffStatisticsList);
    }
}
