package com.mz.fuel_sale_analytics_back.service;

import com.mz.fuel_sale_analytics_back.model.User;

public interface UserService {

    User create(User user);

    User findById(Long id);

    User update(Long id, User user);

    void deleteById(Long id);

    Iterable<User> findAll();

}
