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

package edu.neu.ccs.plagiarismdetector.sprint3tests.lexical;

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
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComparisonTests {

    @Autowired
    private ComparisonService comparisonService;

    @Test
    public void testLexicalMatch() {
        ComparisonTestHelper helper = new ComparisonTestHelper("lexical");
        ComparisonMockData mockData = helper.createtComparisonMockData();
        Map<ComparisonType, DiffStatistics> typeStatsMap = comparisonService.compare(mockData.getFiles(),
                mockData.getMetadata()).getDiffStatisticsList();

        assertEquals("LCS Strategy value for lexical match",
                100.0,
                typeStatsMap.get(LCS).getMetricList().get(0).getMatchPercentage(), 1.0);
        assertEquals("NW Strategy value for lexical match",
                100.0,
                typeStatsMap.get(NW).getMetricList().get(0).getMatchPercentage(), 1.0);
        assertEquals("Weighted Polynomial Strategy value for lexical match",
                100.0,
                typeStatsMap.get(WEIGHTED).getMetricList().get(0).getMatchPercentage(), 1.0);
        assertEquals("Moss Trained Weights Strategy value for lexical match",
                100.0,
                typeStatsMap.get(MOSS_TRAINED).getMetricList().get(0).getMatchPercentage(), 1.0);
    }
}
