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

package edu.neu.ccs.plagiarismdetector.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainingPropertiesTest {
    @Autowired
    private LogService logService;

    @Autowired
    private TrainingProperties trainingProperties;

    @Test
    public void checkProps() {
        logService.writeInfo("Training Properties: " + trainingProperties);
        assertNotNull("Training Properties is not null?", trainingProperties);
    }

    @Test
    public void checkMossConfig() {
        TrainingProperties.MossConfig mossConfig = trainingProperties.getMossConfig();
        logService.writeInfo("Moss Config Properties: " + mossConfig);
        assertNotNull("Moss Config is not null?", mossConfig);
    }

}
