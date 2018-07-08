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
package edu.neu.ccs.plagiarismdetector.report;

import java.util.List;

/**
 * output of comparison between codes
 */
public class DiffStatistics {
    /**
     * List of similarities between uploaded codes
     */
    private List<SimilarityMetric> metricList;

    /**
     * @param metricList List of similarities between uploaded codes
     */
    public DiffStatistics(List<SimilarityMetric> metricList) {
        super();
        this.metricList = metricList;
    }

    /**
     * @return List of similarities between uploaded codes
     */
    public List<SimilarityMetric> getMetricList() {
        return metricList;
    }

    /**
     * @param metricList List of similarities between uploaded codes
     */
    public void setMetricList(List<SimilarityMetric> metricList) {
        this.metricList = metricList;
    }
}
