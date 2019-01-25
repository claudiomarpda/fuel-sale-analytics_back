package com.example.selecaojava.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Region {

    public static final String PK = "region_id";

    @Id
    @GeneratedValue
    @Column(name = PK)
    private Integer id;

    @NotEmpty
    private String code;

    @NotEmpty
    private String name;

}
