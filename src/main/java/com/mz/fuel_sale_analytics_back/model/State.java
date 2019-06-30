package com.mz.fuel_sale_analytics_back.model;

import lombok.*;

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
public class State {

    public static final String PK = "state_id";

    @Id
    @GeneratedValue
    @Column(name = PK)
    private Integer id;

    @Column(unique = true)
    private int ufCode;

    @Column(unique = true)
    @NotEmpty
    private String uf;

    @Column(unique = true)
    @NotEmpty
    private String ufName;
}
