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

public class MatchedSnippet {
    /**
     * matched line no in student 1 code
     */
    private int matchedLine1;
    /**
     * matched line no in student 1 code
     */
    private int matchedLine2;

    /**
     * @return matched line no in student 1 code
     */
    public int getMatchedLine1() {
        return matchedLine1;
    }

    /**
     * @param matchedLine1 matched line no in student 1 code
     */
    public void setMatchedLine1(int matchedLine1) {
        this.matchedLine1 = matchedLine1;
    }

    /**
     * @return matched line no in student 1 code
     */
    public int getMatchedLine2() {
        return matchedLine2;
    }

    /**
     * @param matchedLine2 matched line no in student 1 code
     */
    public void setMatchedLine2(int matchedLine2) {
        this.matchedLine2 = matchedLine2;
    }

    /**
     * @param matchedLine1 matched line no in student 1 code
     * @param matchedLine2 matched line no in student 2 code
     */
    public MatchedSnippet(int matchedLine1, int matchedLine2) {
        this.matchedLine1 = matchedLine1;
        this.matchedLine2 = matchedLine2;
    }
}
