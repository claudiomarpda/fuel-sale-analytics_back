package com.example.selecaojava.service;

import com.example.selecaojava.model.User;

public interface UserService {

    User create(User user);

    User findById(Long id);

    User update(Long id, User user);

    void deleteById(Long id);

}
