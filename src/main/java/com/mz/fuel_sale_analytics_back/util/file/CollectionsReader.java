package com.mz.fuel_sale_analytics_back.util.file;

import com.mz.fuel_sale_analytics_back.model.Collect;
import com.mz.fuel_sale_analytics_back.repository.BannerRepository;
import com.mz.fuel_sale_analytics_back.repository.CountyRepository;
import com.mz.fuel_sale_analytics_back.repository.ProductRepository;
import com.mz.fuel_sale_analytics_back.repository.RegionRepository;

import java.io.IOException;
import java.util.List;

import static com.mz.fuel_sale_analytics_back.util.file.ReadFile.readFileAsString;

public class CollectionsReader implements ReadFile<Collect> {

    private final CountyRepository countyRepository;
    private final RegionRepository regionRepository;
    private final ProductRepository productRepository;
    private final BannerRepository bannerRepository;

    public CollectionsReader(CountyRepository cr, RegionRepository rr, ProductRepository pr, BannerRepository br) {
        this.countyRepository = cr;
        this.regionRepository = rr;
        this.productRepository = pr;
        this.bannerRepository = br;
    }

    /**
     * Row format: region code,uf,county name,reseller name,product name,date,purchase price,sale price,unit of measurement,banner name
     */
    public List<Collect> read(String fullPath) throws IOException {
        String s = readFileAsString(fullPath);
        return new CollectsReaderUtil(regionRepository, productRepository, bannerRepository, countyRepository).readCsv(s);
    }
}
