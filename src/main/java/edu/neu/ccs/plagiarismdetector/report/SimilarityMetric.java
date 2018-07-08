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
 * a metric for showing diff characteristics between two pieces of code
 */
public class SimilarityMetric {

    /**
     * percent similarity between two codes
     */
    private Double matchPercentage;
    /**
     * Student 1 identifier i.e. name or directory
     */
    private String student1Identifier;
    /**
     * Student 2 identifier i.e. name or directory
     */
    private String student2Identifier;
    /**
     * similar code snippets
     */
    private List<MatchedSnippet> matchedSnippets;

    /**
     * @param matchPercentage i.e. percent similarity between two codes
     * @param matchedSnippets i.e. similar code snippets
     */
    public SimilarityMetric(Double matchPercentage, List<MatchedSnippet> matchedSnippets) {
        this.matchPercentage = matchPercentage;
        this.matchedSnippets = matchedSnippets;
    }

    /**
     * @param matchPercentage    i.e. percent similarity between two codes
     * @param student1Identifier Student 1 identifier i.e. name or directory
     * @param student2Identifier Student 2 identifier i.e. name or directory
     */
    public SimilarityMetric(Double matchPercentage, String student1Identifier, String student2Identifier) {
        this.matchPercentage = matchPercentage;
        this.student1Identifier = student1Identifier;
        this.student2Identifier = student2Identifier;
    }

    /**
     * @return percent similarity between two codes
     */
    public Double getMatchPercentage() {
        return matchPercentage;
    }

    /**
     * @param matchPercentage percent similarity between two codes
     */
    public void setMatchPercentage(Double matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    /**
     * @return Student 1 identifier i.e. name or directory
     */
    public String getStudent1Identifier() {
        return student1Identifier;
    }

    /**
     * @param student1Identifier Student 1 identifier i.e. name or directory
     */
    public void setStudent1Identifier(String student1Identifier) {
        this.student1Identifier = student1Identifier;
    }

    /**
     * @return Student 2 identifier i.e. name or directory
     */
    public String getStudent2Identifier() {
        return student2Identifier;
    }

    /**
     * @param student2Identifier Student 2 identifier i.e. name or directory
     */
    public void setStudent2Identifier(String student2Identifier) {
        this.student2Identifier = student2Identifier;
    }

    /**
     * @return similar code snippets
     */
    public List<MatchedSnippet> getMatchedSnippets() {
        return matchedSnippets;
    }

    /**
     * @param matchedSnippets similar code snippets
     */
    public void setMatchedSnippets(List<MatchedSnippet> matchedSnippets) {
        this.matchedSnippets = matchedSnippets;
    }

}
