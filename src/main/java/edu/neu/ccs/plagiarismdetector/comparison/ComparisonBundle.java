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

import java.util.List;


/**
 * A wrapper class for maintaining input information
 */
public class ComparisonBundle {

    /**
     * list of mapping between repo names and constituent code
     */
    private List<StudentRepoCodeMappingData> studentRepoCodeMappingDataList;

    /**
     * @param studentRepoCodeMappingDataList list to be set
     */
    public ComparisonBundle(List<StudentRepoCodeMappingData> studentRepoCodeMappingDataList) {
        this.studentRepoCodeMappingDataList = studentRepoCodeMappingDataList;
    }

    /**
     * @return the list
     */
    public List<StudentRepoCodeMappingData> getStudentRepoCodeMappingDataList() {
        return studentRepoCodeMappingDataList;
    }

    /**
     * @param studentRepoCodeMappingDataList to be set
     */
    public void setStudentRepoCodeMappingDataList(List<StudentRepoCodeMappingData> studentRepoCodeMappingDataList) {
        this.studentRepoCodeMappingDataList = studentRepoCodeMappingDataList;
    }
}
