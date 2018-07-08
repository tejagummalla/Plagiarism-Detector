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
import edu.neu.ccs.plagiarismdetector.report.MatchedSnippet;
import edu.neu.ccs.plagiarismdetector.report.SimilarityMetric;
import org.antlr.v4.runtime.Token;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * LCS algorithm implementation
 */
@Component
public class LCSStrategy extends NonWeightBasedStrategy {
    /**
     * @param list1 node list to be compared
     * @param list2 node list to be compared
     * @return similarity metric
     */
    @Override
    public SimilarityMetric compare(List<AST> list1, List<AST> list2) {
        return measureSimilarity(list1, list2);
    }


    /**
     *The parent function to run the LCS strategy
     * @param list1 The list of ASt nodes of the user 1
     * @param list2 The list of ASt nodes of the user 2
     * @return The similarity meassure between the two AST lists
     */
    private SimilarityMetric measureSimilarity(List<AST> list1, List<AST> list2) {
        int totalSize = list1.size() + list2.size();
        if (totalSize == 0)
            throw new IllegalArgumentException("Both lists are empty.");

        int[][] lcsMatrix = runLCS(list1, list2);
        List<MatchedSnippet> snippets = getMatchedNodes(list1, list2, lcsMatrix);

        return new SimilarityMetric(((2.0 * snippets.size()) / totalSize) * 100, snippets);
    }

    /**
     * Compute similarity meassures using LCS
     * @param list1 node list to be compared
     * @param list2 node list to be compared
     * @return similarity matrix
     */
    private int[][] runLCS(List<AST> list1, List<AST> list2) {
        int size1 = list1.size();
        int size2 = list2.size();

        int[][] map = new int[size1 + 1][size2 + 1];

        for (int i = 1; i <= size1; i++) {
            for (int j = 1; j <= size2; j++) {
                AST ast1 = list1.get(i - 1);
                AST ast2 = list2.get(j - 1);

                if (ast1.getHash().equals(ast2.getHash())) {
                    map[i][j] = map[i - 1][j - 1] + 1;
                } else
                    map[i][j] = Math.max(map[i - 1][j], map[i][j - 1]);
            }
        }

        return map;
    }

    /**
     * Obtainin the nodes that match
     * @param list1     node list to be compared
     * @param list2     node list to be compared
     * @param lcsMatrix similarity matrix
     * @return list of matched snippets
     */
    private List<MatchedSnippet> getMatchedNodes(List<AST> list1, List<AST> list2, int[][] lcsMatrix) {
        int i = list1.size();
        int j = list2.size();

        List<MatchedSnippet> snippets = new ArrayList<>();
        while (i > 0 && j > 0) {
            AST ast1 = list1.get(i - 1);
            AST ast2 = list2.get(j - 1);

            Token token1 = (Token) ast1.getPayload();
            Token token2 = (Token) ast2.getPayload();

            if (ast1.getHash().equals(ast2.getHash())) {
                snippets.add(new MatchedSnippet(token1.getLine(), token2.getLine()));
                i--;
                j--;
            } else if (lcsMatrix[i - 1][j] > lcsMatrix[i][j - 1])
                i--;
            else
                j--;
        }
        return snippets;
    }

}
