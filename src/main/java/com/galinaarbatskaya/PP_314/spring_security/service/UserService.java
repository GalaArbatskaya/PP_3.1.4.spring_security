package com.galinaarbatskaya.PP_314.spring_security.service;

import com.galinaarbatskaya.PP_314.spring_security.models.Role;
import com.galinaarbatskaya.PP_314.spring_security.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void save(User user);
    void removeUserById(long id);

    List<User> getAllUsers();

    User getUserById(long id);

    void update(User user);
    Optional<User> findByUsername(String username);
    List<Role> getAllRoles();
}

