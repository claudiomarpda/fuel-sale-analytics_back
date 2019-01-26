package com.example.selecaojava.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Collect {

    public static final String PK = "collect_id";

    @Id
    @GeneratedValue
    @Column(name = PK)
    private Long id;

    @OneToOne
    @JoinColumn(name = Region.PK)
    private Region region;

    @OneToOne
    @JoinColumn(name = County.PK)
    private County county;

    @OneToOne
    @JoinColumn(name = Product.PK)
    private Product product;

    @NotEmpty
    private String reseller;

    private LocalDate date;

    private Double purchasePrice;

    private Double salePrice;

    @NotEmpty
    private String measurementUnit;

    @OneToOne
    @JoinColumn(name = Banner.PK)
    private Banner banner;

}
