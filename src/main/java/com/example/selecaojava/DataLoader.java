package com.example.selecaojava;

import com.example.selecaojava.model.Role;
import com.example.selecaojava.model.User;
import com.example.selecaojava.repository.RoleRepository;
import com.example.selecaojava.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RoleRepository rr, UserRepository ur, PasswordEncoder pe) {
        this.roleRepository = rr;
        this.userRepository = ur;
        this.passwordEncoder = pe;
    }

    @Override
    public void run(String... args) {

        // Load roles
        roleRepository.saveAll(Arrays.asList(
                new Role(1, Role.ADMIN),
                new Role(2, Role.USER)));

        Set<Role> onlyAdmin = Collections.singleton(roleRepository.findByName(Role.ADMIN).orElseThrow(RuntimeException::new));
        Set<Role> onlyUser = Collections.singleton(roleRepository.findByName(Role.USER).orElseThrow(RuntimeException::new));

        String hashPwd = passwordEncoder.encode("123");

        // Load users
        // Saving password in plain text, for now
        userRepository.saveAll(Arrays.asList(
                new User(null, "User A", "email_a@email.com", hashPwd, onlyAdmin, true),
                new User(null, "User B", "email_b@email.com", hashPwd, onlyUser, true)
        ));
    }
}
