package com.mz.fuel_sale_analytics_back.util.file;

import com.mz.fuel_sale_analytics_back.exception.NotFoundException;
import com.mz.fuel_sale_analytics_back.model.*;
import com.mz.fuel_sale_analytics_back.repository.BannerRepository;
import com.mz.fuel_sale_analytics_back.util.DateUtil;
import com.mz.fuel_sale_analytics_back.util.WordsComparator;
import com.mz.fuel_sale_analytics_back.repository.CountyRepository;
import com.mz.fuel_sale_analytics_back.repository.ProductRepository;
import com.mz.fuel_sale_analytics_back.repository.RegionRepository;

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
                collect.setDate(DateUtil.getLocalDate(columns[5]));
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
