package com.mz.fuel_sale_analytics_back.service.impl;

import com.mz.fuel_sale_analytics_back.exception.ConflictException;
import com.mz.fuel_sale_analytics_back.exception.NotFoundException;
import com.mz.fuel_sale_analytics_back.repository.RoleRepository;
import com.mz.fuel_sale_analytics_back.service.UserService;
import com.mz.fuel_sale_analytics_back.model.User;
import com.mz.fuel_sale_analytics_back.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository ur, PasswordEncoder pe, RoleRepository rr) {
        this.userRepository = ur;
        this.passwordEncoder = pe;
        this.roleRepository = rr;
    }

    @Override
    public User create(User user) {
        checkEmailExists(user);
        checkRolesExist(user);

        String hashPwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPwd);
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário", id));
    }

    @Override
    public User update(Long id, User user) {
        User dbUser = findById(id);
        user.setId(dbUser.getId());

        // Update email
        if (!dbUser.getEmail().equals(user.getEmail())) {
            checkEmailExists(user);
        }

        checkRolesExist(user);
        String hashPwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPwd);
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = findById(id);
        userRepository.deleteById(user.getId());
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    private void checkEmailExists(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email já existe");
        }
    }

    private void checkRolesExist(User user) {
        user.getRoles().forEach(r -> {
            if (!roleRepository.existsById(r.getId())) {
                throw new NotFoundException("Papel", r.getId());
            }
        });
    }
}
