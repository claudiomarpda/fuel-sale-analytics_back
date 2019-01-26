package com.example.selecaojava.service.impl;

import com.example.selecaojava.exception.InvalidInputException;
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
}
