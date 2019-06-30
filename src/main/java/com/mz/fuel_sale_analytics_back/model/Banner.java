package com.mz.fuel_sale_analytics_back.model;

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
public class Banner {

    public static final String PK = "banner_id";

    @Id
    @GeneratedValue
    @Column(name = PK)
    private Integer id;

    @NotEmpty
    private String name;

}
