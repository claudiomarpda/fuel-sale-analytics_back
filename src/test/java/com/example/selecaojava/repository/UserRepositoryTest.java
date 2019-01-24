package com.example.selecaojava.repository;

import com.example.selecaojava.model.Role;
import com.example.selecaojava.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        List<Role> roles = Arrays.asList(
                new Role(1, Role.ADMIN),
                new Role(2, Role.USER));

        roleRepository.saveAll(roles);
    }

    @Test
    public void createUsersSucceeds() {
        // Find role
        Set<Role> onlyAdmin = Collections.singleton(roleRepository.findByName(Role.ADMIN).orElseThrow(RuntimeException::new));
        // Create user
        User admin = userRepository.save(new User(null, "Admin", "admin@email.com", "123", onlyAdmin, true));
        // Admin exists
        assertTrue(userRepository.findById(admin.getId()).isPresent());
        // Admin has role ADMIN
        assertTrue(admin.getRoles().containsAll(onlyAdmin));

        Set<Role> onlyUser = Collections.singleton(roleRepository.findByName(Role.USER).orElseThrow(RuntimeException::new));
        User user = userRepository.save(new User(null, "User", "user@email.com", "123", onlyUser, true));
        assertTrue(userRepository.findById(user.getId()).isPresent());
        assertTrue(user.getRoles().containsAll(onlyUser));
    }

}
