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
public class Product {

    public static final String PK = "product_id";

    @Id
    @GeneratedValue
    @Column(name = PK)
    private Long id;

    @NotEmpty
    private String name;

}
