package com.galinaarbatskaya.PP_314.spring_security.service;

import com.galinaarbatskaya.PP_314.spring_security.models.Role;
import com.galinaarbatskaya.PP_314.spring_security.models.User;
import com.galinaarbatskaya.PP_314.spring_security.repositiries.RoleRepository;
import com.galinaarbatskaya.PP_314.spring_security.repositiries.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }


    @Override
    @Transactional
    public void removeUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.orElse(null);
    }

    @Override
    @Transactional
    public void update(User updateUser) {
        Optional<User> user = userRepository.findById(updateUser.getId());
        String oldPassword = "";
            if (user.isPresent()) {
            oldPassword = user.get().getPassword();
            if(updateUser.getRoles()==null){
            updateUser.setRoles(user.get().getRoles());}
       }
        if (!(oldPassword.equals(updateUser.getPassword()))) {
            updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }

        userRepository.save(updateUser);
    }


    @Override
    public Optional<User> findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User %s doesn't exists", email)));
        return user;
    }
}
