package com.mz.fuel_sale_analytics_back.service;

import com.mz.fuel_sale_analytics_back.model.Collect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CollectService {

    void importCsv(MultipartFile file);

    Collect create(Collect collect);

    Collect findById(Long id);

    Collect update(Long id, Collect collect);

    void deleteById(Long id);

    Double getAvgSalePriceByCounty(String county);

    List<Collect> getAllFromImportedCsvByRegionCode(MultipartFile file, String regionCode);

    Page<Collect> findAllByBannerName(String name, Pageable pageable);

    Page<Collect> findAllByDate(String date, Pageable pageable);

    Double getAvgSaleAndPurchasePriceByCounty(String countyName);

    Double getAvgSaleAndPurchasePriceByBanner(String bannerName);

}


