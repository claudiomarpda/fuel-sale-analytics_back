package com.example.selecaojava.controller;

import com.example.selecaojava.model.Role;
import com.example.selecaojava.model.RoleName;
import com.example.selecaojava.model.User;
import com.example.selecaojava.payload.SignInRequest;
import com.example.selecaojava.repository.RoleRepository;
import com.example.selecaojava.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private Role adminRole;
    private Role userRole;
    private String rawPwd;
    private String hashPwd;

    @Before
    public void setUp() {
        adminRole = new Role(1, RoleName.ROLE_ADMIN);
        userRole = new Role(2, RoleName.ROLE_USER);
        roleRepository.saveAll(Arrays.asList(adminRole, userRole));

        rawPwd = "123";
        hashPwd = passwordEncoder.encode("123");
    }

    @Test
    public void signInWithInvalidCredentialsFails() throws Exception {
        String json = objectMapper.writeValueAsString(new SignInRequest("invalid@email.com", "123"));
        mvc.perform(post("/auth/signIn")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    private void signInRequest(String json) throws Exception {
        mvc.perform(post("/auth/signIn")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("accessToken").exists())
                .andExpect(jsonPath("tokenType").exists());
    }

    private void signIn(User user) throws Exception {
        user = userRepository.save(user);
        SignInRequest signInRequest = new SignInRequest(user.getEmail(), rawPwd);
        String json = objectMapper.writeValueAsString(signInRequest);
        signInRequest(json);
    }

    @Test
    public void signInAsAdminSucceeds() throws Exception {
        user = new User(null, "Admin", "admin@email.com", hashPwd, Collections.singleton(adminRole), true);
        signIn(user);
    }

    @Test
    public void signInAsUserSucceeds() throws Exception {
        user = new User(null, "User", "user@email.com", hashPwd, Collections.singleton(userRole), true);
        signIn(user);
    }

}
