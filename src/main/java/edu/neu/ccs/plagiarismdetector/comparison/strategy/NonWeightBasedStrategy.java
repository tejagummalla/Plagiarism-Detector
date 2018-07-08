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

package edu.neu.ccs.plagiarismdetector.comparison.strategy;

import edu.neu.ccs.plagiarismdetector.ast.AST;
import edu.neu.ccs.plagiarismdetector.ast.ASTService;
import edu.neu.ccs.plagiarismdetector.comparison.ComparisonBundle;
import edu.neu.ccs.plagiarismdetector.comparison.ComparisonType;
import edu.neu.ccs.plagiarismdetector.comparison.StudentRepoCodeMappingData;
import edu.neu.ccs.plagiarismdetector.report.DiffStatistics;
import edu.neu.ccs.plagiarismdetector.report.SimilarityMetric;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Non weights strategy super class
 */
public abstract class NonWeightBasedStrategy implements ComparisonStrategy {
    private ASTService astService;

    public NonWeightBasedStrategy() {
        this.astService = new ASTService();
    }

    /**
     * To be implemented by child
     * @param list1 node list to be compared
     * @param list2 node list to be compared
     * @return similarity metric
     */
    public abstract SimilarityMetric compare(List<AST> list1, List<AST> list2);

    /**
     * The main method that is implemented to execute the compare method.
     * @param comparisonBundle input data
     * @return diff statistics for the run
     */
    @Override
    public DiffStatistics compare(ComparisonBundle comparisonBundle,
                                  Map<ComparisonType, DiffStatistics> diffStatisticsList) {
        List<SimilarityMetric> similarityList = new ArrayList<>();

        List<StudentRepoCodeMappingData> list = comparisonBundle.getStudentRepoCodeMappingDataList();
        int size = list.size();

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {

                String s1 = list.get(i).getCode();
                String s2 = list.get(j).getCode();

                List<AST> list1 = astService.preOrder(s1);
                List<AST> list2 = astService.preOrder(s2);

                List<AST> filteredList1 = getFilteredList(list1);
                List<AST> filteredList2 = getFilteredList(list2);

                SimilarityMetric similarityMetric = compare(filteredList1, filteredList2);

                similarityMetric.setStudent1Identifier(list.get(i).getReponame());
                similarityMetric.setStudent2Identifier(list.get(j).getReponame());

                similarityList.add(similarityMetric);
            }
        }

        return new DiffStatistics(similarityList);
    }

    /**
     * The filtered list on nodes to be executed.
     * @param list node list to be compared
     * @return filtered token nodes
     */
    public List<AST> getFilteredList(List<AST> list) {
        return list.stream()
                .filter(node -> node.getPayload() instanceof Token)
                .collect(Collectors.toList());
    }
}
