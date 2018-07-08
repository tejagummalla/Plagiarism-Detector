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

import edu.neu.ccs.plagiarismdetector.user.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {

    @Autowired
    private LogService logService;

    @Autowired
    private UserController uc;

    @Test
    public void InfoLogCheck() {
        String POPULATED = "LOG POPOLUATED";
        assertEquals(logService.writeInfo("In Test InfoLogCheck - Login"), POPULATED);
    }

    @Test
    public void ErrorLogCheck() {
        String POPULATED = "LOG POPOLUATED";
        assertEquals(logService.writeError("suledisha@gmail.com",
                "In Test ErrorLogCheck- INVALID USERNAME AND/OR PASSWORD"), POPULATED);
    }

}
