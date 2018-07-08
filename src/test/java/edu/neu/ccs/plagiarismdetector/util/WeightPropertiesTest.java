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
public class WeightPropertiesTest {
    @Autowired
    private LogService logService;

    @Autowired
    private WeightProperties weightProperties;

    @Test
    public void checkProps() {
        logService.writeInfo("Weight Properties: " + weightProperties);
        assertNotNull("Weight Properties is not null?", weightProperties);
    }

    @Test
    public void checkMossTrainedConfig() {
        WeightProperties.MossTrainedConfig mossTrainedConfig = weightProperties.getMossTrainedConfig();
        logService.writeInfo("Moss Trained Config Properties: " + mossTrainedConfig);
        assertNotNull("Moss Trained Config is not null?", mossTrainedConfig);
    }

}
