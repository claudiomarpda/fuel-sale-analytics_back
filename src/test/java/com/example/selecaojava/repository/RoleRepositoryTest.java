package com.example.selecaojava.repository;

import com.example.selecaojava.model.Role;
import com.example.selecaojava.model.RoleName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;
    private List<Role> roles;

    @Before
    public void setUp() {
        roles = Arrays.asList(
                new Role(1, RoleName.ROLE_ADMIN),
                new Role(2, RoleName.ROLE_USER));
    }

    @Test
    public void findByNameShouldFail() {
        assertFalse(roleRepository.findByName(RoleName.ROLE_ADMIN).isPresent());
        assertFalse(roleRepository.findByName(RoleName.ROLE_USER).isPresent());
    }

    @Test
    public void findByNameShouldSucceed() {
        roleRepository.saveAll(roles);

        assertTrue(roleRepository.findByName(RoleName.ROLE_ADMIN).isPresent());
        assertTrue(roleRepository.findByName(RoleName.ROLE_USER).isPresent());
    }
}
