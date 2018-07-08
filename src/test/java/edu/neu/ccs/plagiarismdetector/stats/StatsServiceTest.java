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

package edu.neu.ccs.plagiarismdetector.stats;

import edu.neu.ccs.plagiarismdetector.statistics.Stats;
import edu.neu.ccs.plagiarismdetector.statistics.StatsService;
import edu.neu.ccs.plagiarismdetector.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Test the stats service
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatsServiceTest {

    @Autowired
    private StatsService statsService;

    @Autowired
    private UserRepository userRepository;

    /*@Test
    public void testCreation() {

        User user = new User();
        user.setPassword("test2");
        user.setEmail("test2@test.com");
        user.setFirstName("test2");
        user.setLastName("test2");
        user.setUsername("test2");

        Stats stats1  = new Stats();
        stats1.setRunningTime(10000L);
        stats1.setUser(user);

        Stats stats2  = new Stats();
        stats1.setRunningTime(15000L);
        stats2.setUser(user);

        user.getStats().add(stats1);
        user.getStats().add(stats2);

        User persistedUser = userRepository.save(user);

        assertNotNull("User is not null ?", persistedUser);
    }
*/
    @Test
    public void testStatCount() {
        Long total = statsService.retrieveCount();
        assertTrue("Total count is more than 0?", total > 0);
    }

    @Test
    public void testFindAll() {
        List<Stats> statsList = statsService.allStats();
        assertTrue("Total count is more than 0?", !statsList.isEmpty());
    }
}
