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

import edu.neu.ccs.plagiarismdetector.ast.AST;
import edu.neu.ccs.plagiarismdetector.ast.ASTService;
import edu.neu.ccs.plagiarismdetector.comparison.strategy.LCSStrategy;
import edu.neu.ccs.plagiarismdetector.comparison.strategy.NonWeightBasedStrategy;
import edu.neu.ccs.plagiarismdetector.report.SimilarityMetric;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LCSStrategyTests {

    @Autowired
    private ASTService astService;

    private String readFile(File file, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(file.toPath());
        return new String(encoded, encoding);
    }

    @Test
    public void lcsVariableNameChangeTest1() throws IOException {
        String code1 = readFile(new File("src/test/resources/mock/student1/add.c"),
                Charset.forName("UTF-8"));
        String code2 = readFile(new File("src/test/resources/mock/student2/add.c"),
                Charset.forName("UTF-8"));

        NonWeightBasedStrategy strategy = new LCSStrategy();

        List<AST> list1 = astService.preOrder(code1);
        List<AST> list2 = astService.preOrder(code2);

        List<AST> filteredList1 = strategy.getFilteredList(list1);
        List<AST> filteredList2 = strategy.getFilteredList(list2);

        SimilarityMetric metric = strategy.compare(filteredList1, filteredList2);
        assertEquals("Metric should not be null", 100.0, metric.getMatchPercentage(), 10.0);
    }

}
