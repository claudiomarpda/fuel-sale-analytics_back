package com.example.selecaojava.controller;

import com.example.selecaojava.service.CollectService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user/collections/files")
public class CollectController {

    private final CollectService collectService;

    public CollectController(CollectService fs) {
        this.collectService = fs;
    }

    /**
     * TODO: responder com detalhes do arquivo recebido: numero de registros, tamanho e nome do arquivo
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importCsv(@RequestParam MultipartFile file) {
        collectService.importCsv(file);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).build();
    }

}
