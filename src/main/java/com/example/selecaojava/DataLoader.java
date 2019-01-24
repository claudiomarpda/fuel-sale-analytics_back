package com.example.selecaojava;

import com.example.selecaojava.model.Role;
import com.example.selecaojava.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository rr) {
        this.roleRepository = rr;
    }

    @Override
    public void run(String... args) {

        roleRepository.saveAll(Arrays.asList(
                new Role(1, Role.ADMIN),
                new Role(2, Role.USER)));
    }
}
