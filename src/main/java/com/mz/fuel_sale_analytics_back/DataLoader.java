package com.mz.fuel_sale_analytics_back;

import com.mz.fuel_sale_analytics_back.repository.*;
import com.mz.fuel_sale_analytics_back.util.file.*;
import com.mz.fuel_sale_analytics_back.model.Role;
import com.mz.fuel_sale_analytics_back.model.RoleName;
import com.mz.fuel_sale_analytics_back.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
 * This class loads data mainly for testing
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StateRepository stateRepository;
    private final CountyRepository countyRepository;
    private final RegionRepository regionRepository;
    private final ProductRepository productRepository;
    private final BannerRepository bannerRepository;

    public DataLoader(RoleRepository rr, UserRepository ur, PasswordEncoder pe, StateRepository sr, CountyRepository cr, RegionRepository rer, ProductRepository pr, BannerRepository br) {
        this.roleRepository = rr;
        this.userRepository = ur;
        this.passwordEncoder = pe;
        this.stateRepository = sr;
        this.countyRepository = cr;
        this.regionRepository = rer;
        this.productRepository = pr;
        this.bannerRepository = br;
    }

    @Override
    public void run(String... args) {

        // Load roles
        roleRepository.saveAll(Arrays.asList(
                new Role(1, RoleName.ROLE_ADMIN),
                new Role(2, RoleName.ROLE_USER)));

        Set<Role> onlyAdmin = Collections.singleton(roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(RuntimeException::new));
        Set<Role> onlyUser = Collections.singleton(roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(RuntimeException::new));

        String hashPwd = passwordEncoder.encode("123");

        // Load users
        // Saving password in plain text, for now
        userRepository.saveAll(Arrays.asList(
                new User(null, "User A", "email_a@email.com", hashPwd, onlyAdmin, true),
                new User(null, "User B", "email_b@email.com", hashPwd, onlyUser, true)
        ));

        loadFiles();
    }

    private void loadFiles() {
        try {
            regionRepository.saveAll(new RegionsReader().read(FileRecords.REGIONS_PATH));
            stateRepository.saveAll(new StatesReader().read(FileRecords.STATES_PATH));
            countyRepository.saveAll(CountyDtoUtil.getFromDto(new CountiesReader().read(FileRecords.COUNTIES_PATH), stateRepository));
            productRepository.saveAll(new ProductsReader().read(FileRecords.PRODUCTS_PATH));
            bannerRepository.saveAll(new BannersReader().read(FileRecords.BANNERS_PATH));

        } catch (IOException e) {
            System.out.println("Check files loading");
            e.printStackTrace();
        }
    }
}
