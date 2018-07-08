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
import edu.neu.ccs.plagiarismdetector.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

import static edu.neu.ccs.plagiarismdetector.util.Constants.USER_ID;
import static edu.neu.ccs.plagiarismdetector.util.Constants.USER_ROLE;

/**
 * User controller to handle user requests
 */
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserRepository userRepository,
                          RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Function adds a new user
     *
     * @param user The user that needs to be stored
     * @return The user after he has been registered
     */
    @PostMapping("/api/register")
    public User register(@RequestBody User user, HttpSession session) {
        User existingUser = userRepository.findUserByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();

        roles.add(roleRepository.findRoleByName(USER_ROLE));

        user.setRoles(roles);

        User savedUser = new User(userRepository.save(user));
        session.setAttribute(USER_ID, savedUser.getId());
        return savedUser;
    }

    /**
     * Function to login the User
     * @param user User
     * @return User
     */
    @PostMapping("/api/login")
    public User login(@RequestBody User user, HttpSession session) {
        User savedUser = userRepository.findUserByUsername(user.getUsername());

        if (savedUser == null) {
            throw new IllegalArgumentException("Incorrect Username!");
        }
        if (!bCryptPasswordEncoder.matches(user.getPassword(), savedUser.getPassword())) {
            throw new IllegalArgumentException("Incorrect Password!");
        }

        savedUser = new User(savedUser);
        session.setAttribute(USER_ID, savedUser.getId());
        return savedUser;
    }

    /**
     * @param userID user ID
     * @return USER
     */
    @GetMapping("/api/user/{userID}")
    public User getUserByID(@PathVariable Long userID) {
        Optional<User> savedUser = userRepository.findById(userID);
        User actualUser = savedUser.orElse(null);
        if (actualUser == null) {
            throw new IllegalArgumentException("User not found!");
        }
        return new User(actualUser);
    }

    /**
     * Returns the current User object
     * @return User
     */
    @GetMapping("/api/user/current")
    public User getCurrentUser(HttpSession session) {
        Long userID = (Long) session.getAttribute(USER_ID);
        Optional<User> savedUser = userRepository.findById(userID);
        User actualUser = savedUser.orElse(null);

        if (actualUser == null) {
            throw new IllegalArgumentException("User not found!");
        }
        return new User(actualUser);
    }

    /**
     * A mapping to fetch all users that are registered
     * to the system
     *
     * @return A list of all the users of the Database.
     */
    @GetMapping("/api/user")
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        for (User savedUser : userRepository.findAll()) {
            users.add(new User(savedUser));
        }
        return users;
    }

    /**
     * Deletes the user with the specified userID
     * @param userID Long
     * @return Status of deletion
     */
    @DeleteMapping("/api/user/{userID}")
    public Status deleteUser(@PathVariable Long userID) {
        try {
            userRepository.deleteById(userID);
        } catch (Exception exp) {
            return new Status(400, "User Could Not be Deleted!");
        }
        return new Status(200, "User Successfully Deleted! ");

    }

    /**
     * @param session current session
     * @return curret logged in user
     */
    @GetMapping("/api/isLoggedIn")
    public Boolean isLoggedIn(HttpSession session) {
        Long userID = null;
        if (session.getAttribute(USER_ID) != null)
            userID = (Long) session.getAttribute(USER_ID);
        return userID != null;
    }

    /**
     * @param session current session
     */
    @PostMapping("/api/logout")
    public void logout(HttpSession session) {
        session.removeAttribute(USER_ID);
        session.invalidate();
    }

}