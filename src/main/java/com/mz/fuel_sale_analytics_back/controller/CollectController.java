package com.mz.fuel_sale_analytics_back.controller;

import com.mz.fuel_sale_analytics_back.model.Collect;
import com.mz.fuel_sale_analytics_back.service.CollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user/collections")
@Api(description = "Coleta de preço de combustível")
public class CollectController {

    private final CollectService collectService;

    public CollectController(CollectService cs) {
        this.collectService = cs;
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

    @GetMapping(value = "/avgSalePrice", params = "countyName")
    public Double getAvgSalePriceByCounty(@RequestParam String countyName) {
        return collectService.getAvgSalePriceByCounty(countyName);
    }

    /**
     * TODO: responder com detalhes do arquivo recebido: numero de registros, tamanho e nome do arquivo
     */
    @ApiOperation(value = "importCsv", notes = "SE;SP;TATUI;AUTO POSTO MORRO DE TATUI LTDA;GASOLINA;24/04/2018;3,559000;4,149000;R$ / litro;RAIZEN")
    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importCsv(@RequestParam MultipartFile file) {
        collectService.importCsv(file);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "getAllFromCsvByRegionCode", notes = "SE;SP;TATUI;AUTO POSTO MORRO DE TATUI LTDA;GASOLINA;24/04/2018;3,559000;4,149000;R$ / litro;RAIZEN")
    @PostMapping(value = "/allFromCsv", params = "regionCode", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Collect> getAllFromCsvByRegionCode(@RequestParam MultipartFile file, @RequestParam String regionCode) {
        return collectService.getAllFromImportedCsvByRegionCode(file, regionCode);
    }

    @ApiOperation(value = "findAllByBannerName", notes = "Retorna coletas por nome da distribuidora", produces = "Paginação de Coleta")
    @GetMapping(params = "bannerName")
    public Page<Collect> findAllByBannerName(@RequestParam String bannerName, Pageable pageable) {
        return collectService.findAllByBannerName(bannerName, pageable);
    }

    @ApiOperation(value = "findAllByDate", notes = "Retorna coletas por data dd-mm-yyyy", produces = "Paginação de Coleta")
    @GetMapping(params = "date")
    public Page<Collect> findAllByDate(@RequestParam String date, Pageable pageable) {
        return collectService.findAllByDate(date, pageable);
    }

    @ApiOperation(value = "getAvgSaleAndPurchasePriceByCountyName", notes = "Retorna o valor médio do valor da compra e do valor da venda por município")
    @GetMapping(value = "/avgSaleAndPurchasePrice", params = "countyName")
    public Double getAvgSaleAndPurchasePriceByCountyName(@RequestParam String countyName) {
        return collectService.getAvgSaleAndPurchasePriceByCounty(countyName);
    }

    @ApiOperation(value = "getAvgSaleAndPurchasePriceByBannerName", notes = "Retorna o valor médio do valor da compra e do valor da venda por bandeira")
    @GetMapping(value = "/avgSaleAndPurchasePrice", params = "bannerName")
    public Double getAvgSaleAndPurchasePriceByBannerName(@RequestParam String bannerName) {
        return collectService.getAvgSaleAndPurchasePriceByBanner(bannerName);
    }

}
