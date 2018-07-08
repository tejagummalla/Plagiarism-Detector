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

package edu.neu.ccs.plagiarismdetector.sprint3tests.not_plagiarised;

import edu.neu.ccs.plagiarismdetector.comparison.ComparisonService;
import edu.neu.ccs.plagiarismdetector.comparison.ComparisonType;
import edu.neu.ccs.plagiarismdetector.report.DiffStatistics;
import edu.neu.ccs.plagiarismdetector.sprint3tests.ComparisonMockData;
import edu.neu.ccs.plagiarismdetector.sprint3tests.ComparisonTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static edu.neu.ccs.plagiarismdetector.comparison.ComparisonType.*;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComparisonTests {

    @Autowired
    private ComparisonService comparisonService;

    @Test
    public void testNotPlagiarized() {
        ComparisonTestHelper helper = new ComparisonTestHelper("not_plagiarised");
        ComparisonMockData mockData = helper.createtComparisonMockData();
        Map<ComparisonType, DiffStatistics> typeStatsMap = comparisonService.compare(mockData.getFiles(),
                mockData.getMetadata()).getDiffStatisticsList();

        assertTrue("LCS Strategy for not_plagiarised",
                typeStatsMap.get(LCS).getMetricList().get(0).getMatchPercentage() < 75);
        assertTrue("NW Strategy value for not_plagiarised",
                typeStatsMap.get(NW).getMetricList().get(0).getMatchPercentage() < 75);
        assertTrue("Weighted Polynomial Strategy value for not_plagiarised",
                typeStatsMap.get(WEIGHTED).getMetricList().get(0).getMatchPercentage() < 75);
        assertTrue("Moss Trained Weights Strategy value for not_plagiarised",
                typeStatsMap.get(MOSS_TRAINED).getMetricList().get(0).getMatchPercentage() < 75);
    }
}
