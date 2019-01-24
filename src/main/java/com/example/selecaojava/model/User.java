package com.example.selecaojava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {

    public static final String PK = "user_id";

    @Id
    @GeneratedValue
    @Column(name = PK)
    private Long id;

    @NotEmpty
    private String name;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @NotEmpty
    private String password;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = User.PK),
            inverseJoinColumns = @JoinColumn(name = Role.PK))
    @Column(nullable = false)
    private Set<Role> roles = new HashSet<>();

    private boolean active;

}
