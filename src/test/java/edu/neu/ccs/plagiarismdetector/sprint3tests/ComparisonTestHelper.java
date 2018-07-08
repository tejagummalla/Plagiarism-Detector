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

package edu.neu.ccs.plagiarismdetector.sprint3tests;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Create Mock data
 */
public class ComparisonTestHelper {
    private static final String PARENT_REPO_PATH = "src/test/resources/sprint3_mock/";
    private static final String STUDENT_1 = "Student1";
    private static final String STUDENT_2 = "Student2";

    private String scenarioType;
    private List<MultipartFile> files;
    private List<String> metadata;

    /**
     * @param scenarioType type of scenario like exact, lexical etc
     */
    public ComparisonTestHelper(String scenarioType) {
        this.scenarioType = scenarioType;
        this.files = new ArrayList<>();
        this.metadata = new ArrayList<>();
    }

    /**
     * @return mock data
     */
    public ComparisonMockData createtComparisonMockData() {
        addFiles(STUDENT_1);
        addFiles(STUDENT_2);

        return new ComparisonMockData(files.toArray(new MultipartFile[0]),
                metadata.toArray(new String[0]));
    }

    /**
     * @param directory input file directory
     */
    private void addFiles(String directory) {
        String directoryPath = PARENT_REPO_PATH + scenarioType + "/" + directory;
        Collection<File> inputFiles = FileUtils.listFiles(new File(directoryPath), null, true);

        for (File file : inputFiles) {
            MultipartFile multipartFile = null;
            try {
                multipartFile = new MockMultipartFile(file.getName(),
                        file.getName(), "text/plain", IOUtils.toByteArray(new FileInputStream(file)));
            } catch (IOException e) {
                e.printStackTrace();
            }

            files.add(multipartFile);
            metadata.add(directory);

        }
    }

}
