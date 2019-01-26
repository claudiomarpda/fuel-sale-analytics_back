package com.example.selecaojava.service;

import org.springframework.web.multipart.MultipartFile;

public interface CollectService {

    void importCsv(MultipartFile file);

}
