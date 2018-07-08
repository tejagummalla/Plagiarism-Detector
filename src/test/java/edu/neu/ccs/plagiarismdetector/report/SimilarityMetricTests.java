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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test the SimilarityMetric class
 *
 * @author : Apoorva
 * @version : 1.0
 * @since : 23 March 2018
 */
public class SimilarityMetricTests {

    /**
     * Test set and get methods of similarity metric
     */
    @Test
    public void similarityMetricTest1() {
        SimilarityMetric sm = new SimilarityMetric(20.07,
                "add.c", "adder.c");
        assertEquals((Object) 20.07, sm.getMatchPercentage());
        assertEquals("add.c", sm.getStudent1Identifier());
        assertEquals("adder.c", sm.getStudent2Identifier());
        sm.setMatchPercentage(21.97);
        sm.setStudent1Identifier("halfAdder.c");
        sm.setStudent2Identifier("fullAdder.c");
        assertEquals((Object) 21.97, sm.getMatchPercentage());
        assertEquals("halfAdder.c", sm.getStudent1Identifier());
        assertEquals("fullAdder.c", sm.getStudent2Identifier());
    }

    /**
     * Test null values
     */
    @Test
    public void similarityMetricTest3() {
        SimilarityMetric sm = new SimilarityMetric(null,
                null, null);
        assertNull(sm.getMatchPercentage());
        assertNull(sm.getStudent1Identifier());
        assertNull(sm.getStudent2Identifier());
        sm.setMatchPercentage(80.0);
        sm.setStudent1Identifier("one.c");
        sm.setStudent2Identifier("two.c");
        assertEquals((Object) 80.0, sm.getMatchPercentage());
        assertEquals("one.c", sm.getStudent1Identifier());
        assertEquals("two.c", sm.getStudent2Identifier());

    }
}
