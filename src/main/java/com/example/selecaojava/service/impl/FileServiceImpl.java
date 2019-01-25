package com.example.selecaojava.service.impl;

import com.example.selecaojava.repository.*;
import com.example.selecaojava.service.FileService;
import com.example.selecaojava.util.FileHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    private final FileHelper fileReader;
    private final RegionRepository regionRepository;
    private final StateRepository stateRepository;
    private final CountyRepository countyRepository;
    private final ProductRepository productRepository;
    private final BannerRepository bannerRepository;

    public FileServiceImpl(RegionRepository rr, StateRepository sr, CountyRepository cr, FileHelper fr, ProductRepository pr, BannerRepository br) {
        this.fileReader = fr;
        this.regionRepository = rr;
        this.stateRepository = sr;
        this.countyRepository = cr;
        this.productRepository = pr;
        this.bannerRepository = br;
    }

    @Override
    public void loadFiles() {
        try {
            regionRepository.saveAll(fileReader.readRegions());
            stateRepository.saveAll(fileReader.readStates());
            countyRepository.saveAll(fileReader.readCounties());
            productRepository.saveAll(fileReader.readProducts());
            bannerRepository.saveAll(fileReader.readBanners());
        } catch (IOException e) {
            System.out.println("Check files loading");
            e.printStackTrace();
        }
    }
}
