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

package edu.neu.ccs.plagiarismdetector.stats;

import edu.neu.ccs.plagiarismdetector.statistics.Stats;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatsTest {

    /**
     *
     */
    @Test
    public void testStats() {
        Stats stats = new Stats();
        stats.setId(1);

        assertEquals(1, stats.getId());

    }

    /**
     *
     */
    @Test
    public void testAssignedStats() {
        Stats stats = new Stats(1000L);

        assertEquals(1000L, stats.getRunningTime(), 1);

    }
}
