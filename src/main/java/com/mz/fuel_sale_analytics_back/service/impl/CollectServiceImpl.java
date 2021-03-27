package com.mz.fuel_sale_analytics_back.service.impl;

import com.mz.fuel_sale_analytics_back.exception.InvalidInputException;
import com.mz.fuel_sale_analytics_back.exception.NotFoundException;
import com.mz.fuel_sale_analytics_back.model.Collect;
import com.mz.fuel_sale_analytics_back.repository.*;
import com.mz.fuel_sale_analytics_back.util.DateUtil;
import com.mz.fuel_sale_analytics_back.service.CollectService;
import com.mz.fuel_sale_analytics_back.util.file.CollectsReaderUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectServiceImpl implements CollectService {

    private final RegionRepository regionRepository;
    private final ProductRepository productRepository;
    private final BannerRepository bannerRepository;
    private final CountyRepository countyRepository;
    private final CollectRepository collectRepository;

    public CollectServiceImpl(RegionRepository rr, ProductRepository pr, BannerRepository br, CountyRepository cr, CollectRepository cor) {
        this.regionRepository = rr;
        this.productRepository = pr;
        this.bannerRepository = br;
        this.countyRepository = cr;
        this.collectRepository = cor;
    }

    @Override
    public Collect create(Collect collect) {
        checkNestedEntitiesExist(collect);
        return collectRepository.save(collect);
    }

    @Override
    public Collect findById(Long id) {
        return collectRepository.findById(id).orElseThrow(() -> new NotFoundException("Coleta", id));
    }

    @Override
    public Collect update(Long id, Collect collect) {
        checkNestedEntitiesExist(collect);
        Collect c = findById(id);
        collect.setId(c.getId());
        return collectRepository.save(collect);
    }

    @Override
    public void deleteById(Long id) {
        collectRepository.deleteById(findById(id).getId());
    }

    @Override
    public Double getAvgSalePriceByCounty(String county) {
        return collectRepository.getAvgSalePriceByCountyName(county);
    }

    @Override
    public void importCsv(MultipartFile file) {
        collectRepository.saveAll(getCollectionsFromCsv(file));
    }

    private List<Collect> getCollectionsFromCsv(MultipartFile file) {
        // Normalize the path by suppressing sequences like "path/.." and inner simple dots.
        String name = StringUtils.cleanPath(file.getOriginalFilename());

        // If contains invalid characters
        if (name.contains("..")) {
            throw new InvalidInputException("Nome do arquivo " + name + " contém caracteres inválidos.");
        }
        else if(!name.contains(".csv")) {
            throw new InvalidInputException("Extensão csv não encontrada no nome do arquivo: " + name);
        }

        try {
            String fileAsString = new String(file.getBytes(), StandardCharsets.UTF_8).replaceAll("\r", "");
            return new CollectsReaderUtil(regionRepository, productRepository, bannerRepository, countyRepository).readCsv(fileAsString);
        } catch (IOException e) {
            throw new InvalidInputException("Falha ao ler bytes de arquivo");
        }
    }

    @Override
    public List<Collect> getAllFromImportedCsvByRegionCode(MultipartFile file, String regionCode) {
        return getCollectionsFromCsv(file).stream().filter(c -> c.getRegion().getCode().equals(regionCode)).collect(Collectors.toList());
    }

    private void checkNestedEntitiesExist(Collect collect) {
        // Region
        regionRepository.findById(collect.getRegion().getId()).orElseThrow(() -> new NotFoundException("Região", collect.getRegion().getId()));
        // Product
        productRepository.findById(collect.getProduct().getId()).orElseThrow(() -> new NotFoundException("Produto", collect.getProduct().getId()));
        // County
        countyRepository.findById(collect.getCounty().getId()).orElseThrow(() -> new NotFoundException("County", collect.getCounty().getId()));
        // Banner
        bannerRepository.findById(collect.getBanner().getId()).orElseThrow(() -> new NotFoundException("Banner", collect.getBanner().getId()));
    }

    @Override
    public Page<Collect> findAllByBannerName(String name, Pageable pageable) {
        return collectRepository.findAllByBanner_Name(name, pageable);
    }

    @Override
    public Page<Collect> findAllByDate(String date, Pageable pageable) {
        return collectRepository.findAllByDate(DateUtil.getDateForH2(date), pageable);
    }

    @Override
    public Double getAvgSaleAndPurchasePriceByCounty(String countyName) {
        return (collectRepository.getAvgSalePriceByCountyName(countyName) +
                collectRepository.getAvgPurchasePriceByCountyName(countyName)) / 2.0;
    }

    @Override
    public Double getAvgSaleAndPurchasePriceByBanner(String bannerName) {
        return (collectRepository.getAvgSalePriceByBannerName(bannerName) +
                collectRepository.getAvgPurchasePriceByBannerName(bannerName)) / 2.0;
    }
}
