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

    private final StateRepository stateRepository;

    public FileHelper(StateRepository sr) {
        this.stateRepository = sr;
    }

    private String readFileAsString(String fullPath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fullPath)));
    }

    /**
     * Row format: codigo_uf, uf, nome
     */
    public List<State> readStates() throws IOException {
        final String[] rows = readFileAsString(STATES_PATH).split("\n");
        List<State> states = new ArrayList<>(rows.length);

        for (String row : rows) {
            String[] columns = row.split(",");
            states.add(new State(null, Integer.valueOf(columns[0]), columns[1], columns[2]));
        }
        return states;
    }

    /**
     * Row format: codigo_ibge,nome,latitude,longitude,capital,codigo_uf
     */
    public List<County> readCounties() throws IOException {
        final String[] rows = readFileAsString(COUNTY_PATH).split("\n");
        List<County> counties = new ArrayList<>(rows.length);

        for (String row : rows) {
            String[] columns = row.split(",");
            State state = stateRepository.findByUfCode(Integer.valueOf(columns[5])).orElseThrow(RuntimeException::new);
            counties.add(new County(null, Integer.valueOf(columns[0]), columns[1], state));
        }
        return counties;
    }

    /**
     * Row format: code, name
     */
    public List<Region> readRegions() throws IOException {
        final String[] rows = readFileAsString(REGIONS_PATH).split("\n");
        List<Region> regions = new ArrayList<>(rows.length);

        for (String row : rows) {
            String[] columns = row.split(",");
            regions.add(new Region(null, columns[0], columns[1]));
        }
        return regions;
    }

    /**
     * Row format: name
     */
    public List<Product> readProducts() throws IOException {
        final String[] rows = readFileAsString(PRODUCTS_PATH).split("\n");
        List<Product> products = new ArrayList<>(rows.length);

        for (String row : rows) {
            products.add(new Product(null, row));
        }
        return products;
    }

}
