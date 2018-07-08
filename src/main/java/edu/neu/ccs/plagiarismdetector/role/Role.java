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

package edu.neu.ccs.plagiarismdetector.role;

import edu.neu.ccs.plagiarismdetector.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The role entity to define the role of the user
 */
@Entity
@Table(name = "role")
public class Role {
    private Long id;
    private String name;

    private Set<User> users = new HashSet<>();

    /**
     * The Id is auto generated on call
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Mapping the user role to the Users
     * @return The set of users having the role
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "roles")
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Set the user to this role
     * @param users The user whose role needs to be set to this role
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
