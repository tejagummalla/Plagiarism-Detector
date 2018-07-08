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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST Controller to handle comparison
 */
@RestController
public class ComparisonController {
    /**
     * comparison service
     */
    private final ComparisonService comparisonService;

    /**
     * @param comparisonService service
     */
    @Autowired
    public ComparisonController(ComparisonService comparisonService) {
        this.comparisonService = comparisonService;
    }

    /**
     * REST mapping to get comparison files from users.
     * @param userID             user who is running the comparison
     * @param uploadFiles        files uploaded
     * @param metadata           directory info for files
     * @param comparisonStrategy on demand comp. strategy
     * @param weight             assigned weight to strategy 1 from slider on UI
     * @return A result containing the statistics of all strategy runs and
     * the concatenated files of each user
     */
    @PostMapping("/api/user/{userID}/compare")
    public ComparisonResult compare(@PathVariable Long userID,
                                    @RequestParam("files") MultipartFile[] uploadFiles,
                                    @RequestParam("metadata") String[] metadata,
                                    @RequestParam("strategy") String comparisonStrategy,
                                    @RequestParam("weight") Double weight) {
        return comparisonService.compare(userID, uploadFiles, metadata, comparisonStrategy, weight);
    }

}