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

package edu.neu.ccs.plagiarismdetector.user;

import edu.neu.ccs.plagiarismdetector.role.Role;
import edu.neu.ccs.plagiarismdetector.statistics.Stats;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static edu.neu.ccs.plagiarismdetector.util.Constants.ADMIN_ROLE;

/**
 * Representation of a user
 *
 * @version : 1.0
 */
@Entity
@Table(name = "user")
public class User {

    /**
     * The user Id is the primary key of Users
     */
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private Boolean isAdmin;

    private Set<Role> roles = new HashSet<>();
    private Set<Stats> stats = new HashSet<>();

    /**
     * Constructor
     */
    public User() {
        super();
    }

    /**
     * Constructor
     * @param user User object
     */
    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.password = user.password;

        this.isAdmin = CollectionUtils.isNotEmpty(user.getRoles().stream().
                filter(role -> role.getName().equals(ADMIN_ROLE))
                .collect(Collectors.toList()));

        this.stats = null;
        this.roles = null;
    }

    /**
     * Returns the ID of the user
     * @return id, long
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    /**
     * Sets the id of the user
     * @param id, long
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the username of the user
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user
     * @param username String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the first name of the user
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user
     * @param firstName String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the user
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user
     * @param lastName String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retuns the email of the user
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user password
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a Set of roles
     * @return Set<Role>
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * set the roles
     * @param roles Set<Role>
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Returns the stats
     * @return Set<Stats>
     */
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    public Set<Stats> getStats() {
        return stats;
    }

    /**
     * Set the stats
     * @param stats Set<Stats>
     */
    public void setStats(Set<Stats> stats) {
        this.stats = stats;
    }

    /**
     * Returns True if the user is admin
     * @return Boolean
     */
    @Transient
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Sets the admin status of the user
     * @param isAdmin Boolean
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}