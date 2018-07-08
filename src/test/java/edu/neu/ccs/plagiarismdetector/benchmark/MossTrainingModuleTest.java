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

package edu.neu.ccs.plagiarismdetector.benchmark;

import edu.neu.ccs.plagiarismdetector.comparison.ComparisonBundle;
import edu.neu.ccs.plagiarismdetector.comparison.ComparisonService;
import edu.neu.ccs.plagiarismdetector.comparison.ComparisonType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static edu.neu.ccs.plagiarismdetector.comparison.ComparisonType.LCS;
import static edu.neu.ccs.plagiarismdetector.comparison.ComparisonType.NW;
import static org.junit.Assert.assertTrue;

/**
 * Test to check MOSS trained model
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class MossTrainingModuleTest {
    @Autowired
    private ComparisonService comparisonService;

    @Autowired
    MossTrainingModule trainingModule;

    @Test
    public void trainedModel() throws IOException {
        List<MultipartFile> uploadFiles = new ArrayList<>();
        List<String> metadata = new ArrayList<>();

        String repoPath = "src/test/resources/moss_mock";
        String student1Path = "src/test/resources/moss_mock/student1";
        String student2Path = "src/test/resources/moss_mock/student2";

        for (File file : FileUtils.listFiles(new File(student1Path), null, true)) {
            MultipartFile multipartFile = new MockMultipartFile(file.getName(),
                    file.getName(), "text/plain", IOUtils.toByteArray(new FileInputStream(file)));
            uploadFiles.add(multipartFile);
            metadata.add("student1");
        }

        for (File file : FileUtils.listFiles(new File(student2Path), null, true)) {
            MultipartFile multipartFile = new MockMultipartFile(file.getName(),
                    file.getName(), "text/plain", IOUtils.toByteArray(new FileInputStream(file)));
            uploadFiles.add(multipartFile);
            metadata.add("student2");
        }

        ComparisonBundle bundle = comparisonService.createComparisonBundle(
                uploadFiles.toArray(new MultipartFile[0]),
                metadata.toArray(new String[0]));

        Map<ComparisonType, Double> strategyWeights = trainingModule.train(repoPath, bundle);

        assertTrue("LCS weight?", strategyWeights.containsKey(LCS));
        assertTrue("NW weight?", strategyWeights.containsKey(NW));

    }
}