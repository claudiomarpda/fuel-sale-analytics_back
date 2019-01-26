package com.example.selecaojava.util.file;

import com.example.selecaojava.exception.NotFoundException;
import com.example.selecaojava.model.*;
import com.example.selecaojava.repository.BannerRepository;
import com.example.selecaojava.repository.CountyRepository;
import com.example.selecaojava.repository.ProductRepository;
import com.example.selecaojava.repository.RegionRepository;
import com.example.selecaojava.util.WordsComparator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CollectsReaderUtil {

    private final RegionRepository regionRepository;
    private final ProductRepository productRepository;
    private final BannerRepository bannerRepository;
    private final CountyRepository countyRepository;

    public CollectsReaderUtil(RegionRepository rr, ProductRepository pr, BannerRepository br, CountyRepository cr) {
        this.regionRepository = rr;
        this.productRepository = pr;
        this.bannerRepository = br;
        this.countyRepository = cr;
    }

    /**
     * Row format: region code,uf,county name,reseller name,product name,date,purchase price,sale price,unit of measurement,banner name
     */
    public List<Collect> readCsv(String fileAsString) {
        final String[] rows = fileAsString.split("\n");
        List<Collect> collects = new ArrayList<>(rows.length);

        List<County> counties = (List<County>) countyRepository.findAll();
        int invalidRecords = 0;

        for (String row : rows) {
            try {
                String[] columns = row.split(";");

                Collect collect = new Collect();
                // Region
                collect.setRegion(findRegion(columns[0]));
                // County
                collect.setCounty(findCounty(counties, columns[2]));
                // Reseller
                collect.setReseller(columns[3]);
                // Product
                collect.setProduct(findProduct(columns[4]));
                // Date
                collect.setDate(getLocalDate(columns[5]));
                // Purchase price
                collect.setPurchasePrice(getPrice(columns[6]));
                // Sale price
                collect.setSalePrice(getPrice(columns[7]));
                // Unit
                collect.setMeasurementUnit(columns[8]);
                // Banner
                collect.setBanner(findBanner(columns[9]));

                collects.add(collect);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                invalidRecords++;
            }
        }
        System.out.println("Registros inválidos: " + invalidRecords);
        return collects;
    }

    private Region findRegion(String code) {
        return regionRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Região não encontrada: " + code));
    }

    private County findCounty(List<County> counties, String name) {
        for (County c : counties) {
            if (WordsComparator.equals(c.getName(), name)) {
                return c;
            }
        }
        throw new NotFoundException("Município não encontrado: " + name);
    }

    private Product findProduct(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new RuntimeException("Produto não encontrado: " + name));
    }

    private LocalDate getLocalDate(String date) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }

    private Banner findBanner(String name) {
        return bannerRepository.findByName(name).orElseThrow(() -> new RuntimeException("Banner não encontrado: " + name));
    }

    private Double getPrice(String column) {
        column = column.replace(",", ".");
        try {
            return Double.valueOf(column);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
