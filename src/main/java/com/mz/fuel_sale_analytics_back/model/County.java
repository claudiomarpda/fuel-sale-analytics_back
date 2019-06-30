package com.mz.fuel_sale_analytics_back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class County {

    public static final String PK = "county_id";

    @Id
    @GeneratedValue
    @Column(name = PK)
    private Integer id;

    @Column(unique = true)
    private int ibgeCode;

    @NotEmpty
    private String name;

    @OneToOne
    @JoinColumn(name = State.PK)
    private State state;

}
