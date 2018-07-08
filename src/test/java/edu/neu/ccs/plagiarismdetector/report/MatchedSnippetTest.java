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

public class MatchedSnippetTest {

    /**
     *
     */
    @Test
    public void testMatchedSnippet() {
        MatchedSnippet matchedSnippet = new MatchedSnippet(5, 10);
        assertEquals(5, matchedSnippet.getMatchedLine1());
        assertEquals(10, matchedSnippet.getMatchedLine2());
    }

    /**
     *
     */
    @Test
    public void testAssignedMatchedSnippet() {
        MatchedSnippet matchedSnippet = new MatchedSnippet(5, 10);
        matchedSnippet.setMatchedLine1(100);
        matchedSnippet.setMatchedLine2(200);

        assertEquals(100, matchedSnippet.getMatchedLine1());
        assertEquals(200, matchedSnippet.getMatchedLine2());
    }
}

