package com.example.selecaojava.service;

import com.example.selecaojava.model.Collect;
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

}


