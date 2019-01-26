package com.example.selecaojava.controller;

import com.example.selecaojava.model.Collect;
import com.example.selecaojava.service.CollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/user/collections")
@Api(description = "Coleta de preço de combustível")
public class CollectController {

    private final CollectService collectService;

    public CollectController(CollectService cs) {
        this.collectService = cs;
    }

    /**
     * TODO: responder com detalhes do arquivo recebido: numero de registros, tamanho e nome do arquivo
     */
    @ApiOperation(value = "importCsv", notes = "Arquivo separado por ; (ponto e vírgula)")
    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importCsv(@RequestParam MultipartFile file) {
        collectService.importCsv(file);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Collect Collect) {
        Collect c = collectService.create(Collect);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{collectId}").buildAndExpand(c.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{collectId}")
    public Collect findById(@PathVariable long collectId) {
        return collectService.findById(collectId);
    }

    @PutMapping("/{collectId}")
    public Collect update(@PathVariable long collectId, @Valid @RequestBody Collect Collect) {
        return collectService.update(collectId, Collect);
    }

    @DeleteMapping("/{collectId}")
    public void deleteById(@PathVariable long collectId) {
        collectService.deleteById(collectId);
    }

}
