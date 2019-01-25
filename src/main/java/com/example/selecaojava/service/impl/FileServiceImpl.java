package com.example.selecaojava.service.impl;

import com.example.selecaojava.repository.RegionRepository;
import com.example.selecaojava.service.FileService;
import com.example.selecaojava.util.FileHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    private final FileHelper fileHelper;
    private final RegionRepository regionRepository;

    public FileServiceImpl(RegionRepository rr, FileHelper fr) {
        this.regionRepository = rr;
        this.fileHelper = fr;
    }

    @Override
    public void loadFiles() {
        try {
            regionRepository.saveAll(fileHelper.readRegions());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
