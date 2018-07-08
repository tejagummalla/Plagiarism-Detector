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
 * Needleman Wulsch comparison algorithm implementation
 */
@Component
public class NWStrategy extends NonWeightBasedStrategy {
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
     * Class to check the similar nodes and in accordance to that
     * calculate the similar nodes.
     * @param list1 node list to be compared
     * @param list2 node list to be compared
     * @return similarity metric
     */
    private SimilarityMetric measureSimilarity(List<AST> list1, List<AST> list2) {
        if (list1.isEmpty() || list2.isEmpty())
            throw new IllegalArgumentException("The files are empty");

        int[][] substitutionMatrix = getSubstitutionMatrix(list1, list2);
        List<MatchedSnippet> snippets = getMatchedNodes(list1, list2, substitutionMatrix);

        Double matchPercentage = 2.0 * snippets.size() * 100 / (list1.size() + list2.size());
        return new SimilarityMetric(matchPercentage, snippets);
    }

    /**
     * Calculate and evaluate the substitution matrix
     * @param list1 node list to be compared
     * @param list2 node list to be compared
     * @return substition matrix
     */
    private int[][] getSubstitutionMatrix(List<AST> list1, List<AST> list2) {
        int size1 = list1.size();
        int size2 = list2.size();

        int[][] substitutionMatrix = new int[size1 + 1][size2 + 1];

        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                AST ast1 = list1.get(i);
                AST ast2 = list2.get(j);

                substitutionMatrix[i][j] = ast1.getHash().equals(ast2.getHash()) ? 1 : -1;
            }
        }
        return substitutionMatrix;
    }

    /**
     * Calculagte the matched nodes between both the files
     * @param list1              node list to be compared
     * @param list2              node list to be compared
     * @param substitutionMatrix input matrix
     * @return list of matched nodes
     */
    private List<MatchedSnippet> getMatchedNodes(List<AST> list1, List<AST> list2, int[][] substitutionMatrix) {
        List<MatchedSnippet> snippets = new ArrayList<>();

        int[][] scoreMatrix = getScoreMatrix(list1, list2, substitutionMatrix);

        int i = list1.size();
        int j = list2.size();

        while (i != 0 && j != 0) {
            switch (scoreMatrix[i][j]) {
                case 0:
                    AST ast1 = list1.get(i - 1);
                    AST ast2 = list2.get(j - 1);

                    Token token1 = (Token) ast1.getPayload();
                    Token token2 = (Token) ast2.getPayload();

                    snippets.add(new MatchedSnippet(token1.getLine(), token2.getLine()));

                    i--;
                    j--;
                    break;
                case 1:
                    i--;
                    break;
                case -1:
                    j--;
                    break;
                default:
                    break;
            }
        }
        return snippets;
    }

    /**
     * To obtain the score matrix after the comparison
     * @param list1              node list to be compared
     * @param list2              node list to be compared
     * @param substitutionMatrix substitutionMatrix input matrix
     * @return score matrix
     */
    private int[][] getScoreMatrix(List<AST> list1, List<AST> list2, int[][] substitutionMatrix) {
        int size1 = list1.size();
        int size2 = list2.size();
        int[][] c = new int[size1 + 1][size2 + 1];

        for (int i = 0; i <= size1; i++)
            c[i][0] = i * -1;

        for (int j = 0; j <= size2; j++)
            c[0][j] = j * -1;

        int[][] scoreMatrix = new int[size1 + 1][size2 + 1];
        for (int i = 1; i <= size1; i++)
            for (int j = 1; j <= size2; j++)
                setScoreMatrix(substitutionMatrix, c, scoreMatrix, i, j);
        return scoreMatrix;
    }

    /**
     * @param substitutionMatrix substitution matrix
     * @param c                  temp matrix
     * @param scoreMatrix        score matrix
     * @param i                  index i
     * @param j                  index j
     */
    private void setScoreMatrix(int[][] substitutionMatrix, int[][] c, int[][] scoreMatrix, int i, int j) {
        int scoreDiagonal = c[i - 1][j - 1] + substitutionMatrix[i][j];
        int scoreup = c[i - 1][j] - 1;
        int scoreleft = c[i][j - 1] - 1;

        c[i][j] = Math.max(Math.max(scoreDiagonal, scoreup), scoreleft);

        if (c[i][j] == scoreDiagonal)
            scoreMatrix[i][j] = 0;
        else if (c[i][j] == scoreleft)
            scoreMatrix[i][j] = -1;
        else
            scoreMatrix[i][j] = 1;
    }
}
