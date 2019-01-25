package com.example.selecaojava.service.impl;

import com.example.selecaojava.repository.RegionRepository;
import com.example.selecaojava.repository.StateRepository;
import com.example.selecaojava.service.FileService;
import com.example.selecaojava.util.FileHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    private final FileHelper fileReader;
    private final RegionRepository regionRepository;
    private final StateRepository stateRepository;

    public FileServiceImpl(FileHelper fr, RegionRepository rr, StateRepository sr) {
        this.fileReader = fr;
        this.regionRepository = rr;
        this.stateRepository = sr;
    }

    @Override
    public void loadFiles() {
        try {
            regionRepository.saveAll(fileReader.readRegions());
            stateRepository.saveAll(fileReader.readStates());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
