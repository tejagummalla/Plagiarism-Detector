/*
 * Copyright (c) 2018. Team-108 Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package edu.neu.ccs.plagiarismdetector.role;

import edu.neu.ccs.plagiarismdetector.user.UserRepository;
import edu.neu.ccs.plagiarismdetector.util.LogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static edu.neu.ccs.plagiarismdetector.util.Constants.ADMIN_ROLE;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleTest {

    @Autowired
    private LogService logService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testRole() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        assertEquals(1L, role.getId(), 1);
        assertEquals(ADMIN_ROLE, role.getName());
    }

    @Test
    public void testFindAll() {
        Set<Role> statsList = roleService.getAllRoles();
        assertEquals("Total count is 2 ?", 2, statsList.size());
    }
}
