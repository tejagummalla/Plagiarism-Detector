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

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Different strategies for code plagiarism detection
 */
public enum ComparisonType {
    /**
     * LCS Similarity based comparison using AST nodes
     */
    LCS("longest_common_subsequence"),
    /**
     * Implementation of Needleman Wunsh algorithm
     */
    NW("needleman_wunsch"),
    /**
     * Weighted comparison with fixed weights assigned to above strategies
     */
    WEIGHTED("weighted"),
    /**
     * Comparison with Moss trained weights assigned to above strategies
     */
    MOSS_TRAINED("moss_trained");

    /**
     * name of the type
     */
    private String name;

    /**
     * @param name of the type to be set
     */
    ComparisonType(String name) {
        this.name = name;
    }

    /**
     * @return name of the type
     */
    public String getName() {
        return name;
    }

    /**
     * @return a stream of comparison types
     */
    public static Stream<ComparisonType> stream() {
        return Arrays.stream(ComparisonType.values());
    }
}
