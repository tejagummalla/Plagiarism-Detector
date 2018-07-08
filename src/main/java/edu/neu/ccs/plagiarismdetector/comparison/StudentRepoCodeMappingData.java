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

/**
 * POJO for mapping between a repo and its code content
 */
public class StudentRepoCodeMappingData {
    /**
     * repo name
     */
    private String reponame;
    /**
     * code in the repo
     */
    private String code;

    /**
     * Initialize the repo name and code mapping
     * @param reponame name of the repo
     * @param code     code in the repo
     */
    StudentRepoCodeMappingData(String reponame, String code) {
        this.reponame = reponame;
        this.code = code;
    }

    /**
     * @return repo name
     */
    public String getReponame() {
        return reponame;
    }

    /**
     * @param reponame to be set
     */
    public void setReponame(String reponame) {
        this.reponame = reponame;
    }

    /**
     * @return code in the repo
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code to be set
     */
    public void setCode(String code) {
        this.code = code;
    }
}
