package com.example.selecaojava.service.impl;

import com.example.selecaojava.exception.InvalidInputException;
import com.example.selecaojava.exception.NotFoundException;
import com.example.selecaojava.model.Collect;
import com.example.selecaojava.repository.*;
import com.example.selecaojava.service.CollectService;
import com.example.selecaojava.util.file.CollectsReaderUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
    public void importCsv(MultipartFile file) {

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
            String fileAsString = new String(file.getBytes(), StandardCharsets.UTF_8);
            List<Collect> collects = new CollectsReaderUtil(regionRepository, productRepository, bannerRepository, countyRepository).readCsv(fileAsString);
            collectRepository.saveAll(collects);
        } catch (IOException e) {
            throw new InvalidInputException("Falha ao ler bytes de arquivo");
        }
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
        Double d = collectRepository.findAvgSalePriceByCountyName(county);
        System.out.println(d);
        return d;
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
}
