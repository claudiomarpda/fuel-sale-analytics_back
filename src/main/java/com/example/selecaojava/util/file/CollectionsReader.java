package com.example.selecaojava.util.file;

import com.example.selecaojava.model.Collect;
import com.example.selecaojava.repository.BannerRepository;
import com.example.selecaojava.repository.CountyRepository;
import com.example.selecaojava.repository.ProductRepository;
import com.example.selecaojava.repository.RegionRepository;

import java.io.IOException;
import java.util.List;

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
        String s = ReadFile.readFileAsString(fullPath);
        return new CollectsReaderUtil(regionRepository, productRepository, bannerRepository, countyRepository).readCsv(s);
    }
}
