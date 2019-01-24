package com.example.selecaojava.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Role {

    protected static final String PK = "role_id";

    public static final String ADMIN = "ROLE_Administrador";
    public static final String USER = "ROLE_Usuário";

    @Id
    @Column(name = PK)
    private Integer id;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String name;
}
