package com.example.selecaojava.util;

import com.example.selecaojava.model.County;
import com.example.selecaojava.model.Product;
import com.example.selecaojava.model.Region;
import com.example.selecaojava.model.State;
import com.example.selecaojava.repository.StateRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHelper {

    private static final String STATIC_PATH = "src/main/resources/static";
    private static final String STATES_PATH = STATIC_PATH + "/estados.csv";
    private static final String COUNTY_PATH = STATIC_PATH + "/municipios.csv";
    private static final String REGIONS_PATH = STATIC_PATH + "/regioes.csv";
    private static final String PRODUCTS_PATH = STATIC_PATH + "/produtos.txt";

    private String readFileAsString(String fullPath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fullPath)));
    }

    /**
     * Row format: code, name
     */
    public List<Region> readRegions() throws IOException {
        final String[] rows = readFileAsString(REGIONS_PATH).split("\n");
        List<Region> regions = new ArrayList<>(rows.length);

        for (String row : rows) {
            String[] columns = row.split(",");
            System.out.println(columns[1]);
            regions.add(new Region(null, columns[0], columns[1]));
        }
        return regions;
    }

}
