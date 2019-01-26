package com.example.selecaojava.controller;

import com.example.selecaojava.model.Role;
import com.example.selecaojava.model.RoleName;
import com.example.selecaojava.model.User;
import com.example.selecaojava.repository.RoleRepository;
import com.example.selecaojava.repository.UserRepository;
import com.example.selecaojava.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDetails adminDetails;

    private User adminUser;
    private User newUser;

    @Before
    public void setUp() {
        Role roleAdmin = new Role(1, RoleName.ROLE_ADMIN);
        Role roleUser = new Role(2, RoleName.ROLE_USER);
        roleRepository.saveAll(Arrays.asList(roleAdmin, roleUser));

        String hashPwd = passwordEncoder.encode("123");
        adminUser= userRepository.save(new User(null, "Admin", "admin_temp@email.com", hashPwd, Collections.singleton(roleAdmin), true));
        adminDetails = new UserDetailsImpl(adminUser);

        newUser = new User(null, "New User", "new@email.com", "123", Collections.singleton(roleUser), true);
    }

    @After
    public void after() {
        userRepository.deleteAll();
    }

    private void makePost(User user) throws Exception {
        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);
        mvc.perform(post("/admin/users")
                .with(user(adminDetails))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void createSucceeds() throws Exception {
        makePost(newUser);
        assertTrue(userRepository.findByEmail(newUser.getEmail()).isPresent());
    }

    @Test
    public void createWithExistentEmailFails() throws Exception {
        makePost(newUser);

        String json = objectMapper.writeValueAsString(newUser);
        mvc.perform(post("/admin/users")
                .with(user(adminDetails))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void findByIdFails() throws Exception {
        makePost(newUser);
        mvc.perform(get("/admin/users/" + 99)
                .with(user(adminDetails)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void findByIdSucceeds() throws Exception {
        makePost(newUser);
        newUser = userRepository.findByEmail(newUser.getEmail()).orElseThrow(RuntimeException::new);

        mvc.perform(get("/admin/users/" + newUser.getId())
                .with(user(adminDetails)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateSucceeds() throws Exception {
        makePost(newUser);
        newUser = userRepository.findByEmail(newUser.getEmail()).orElseThrow(RuntimeException::new);
        newUser.setPassword("123");
        newUser.setName("Updated Name");
        String json = objectMapper.writeValueAsString(newUser);
        System.out.println(json);

        mvc.perform(put("/admin/users/" + newUser.getId())
                .with(user(adminDetails))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk())
        .andExpect(content().string(containsString("Updated Name")));

        assertTrue(userRepository.findByEmail(newUser.getEmail()).isPresent());
    }

    @Test
    public void deleteByIdFails() throws Exception {
        makePost(newUser);
        mvc.perform(delete("/admin/users/" + 99)
                .with(user(adminDetails)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteByIdSucceeds() throws Exception {
        makePost(newUser);
        newUser = userRepository.findByEmail(newUser.getEmail()).orElseThrow(RuntimeException::new);

        mvc.perform(delete("/admin/users/" + newUser.getId())
                .with(user(adminDetails)))
                .andExpect(status().isOk());
    }

//    @Test
    public void noAdminAccessFails() throws Exception {
        mvc.perform(get("/admin/users/" + adminUser.getId()))
                .andExpect(status().is4xxClientError());
    }
}
