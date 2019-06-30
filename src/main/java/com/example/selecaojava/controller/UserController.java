package com.example.selecaojava.controller;

import com.example.selecaojava.model.User;
import com.example.selecaojava.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@Api(description = "Usuário")
public class UserController {

    private final UserService userService;

    public UserController(UserService us) {
        this.userService = us;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        User u = userService.create(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}").buildAndExpand(u.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{userId}")
    public User findById(@PathVariable long userId) {
        return userService.findById(userId);
    }

    @PutMapping("/{userId}")
    public User update(@PathVariable long userId, @Valid @RequestBody User user) {
        return userService.update(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void deleteById(@PathVariable long userId) {
        userService.deleteById(userId);
    }

    @GetMapping
    public Iterable<User> findAll() {
        return userService.findAll();
    }

}
