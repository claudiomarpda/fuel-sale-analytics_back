package com.mz.fuel_sale_analytics_back.model;

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

    @Id
    @Column(name = PK)
    private Integer id;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String name;
}
