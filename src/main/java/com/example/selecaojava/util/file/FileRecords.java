package com.example.selecaojava.util;

public interface FileRecords {

    String STATIC_PATH = "src/main/resources/static";
    String STATES_PATH = STATIC_PATH + "/estados.csv";
    String COUNTIES_PATH = STATIC_PATH + "/municipios.csv";
    String REGIONS_PATH = STATIC_PATH + "/regioes.csv";
    String PRODUCTS_PATH = STATIC_PATH + "/produtos.txt";
    String BANNERS_PATH = STATIC_PATH + "/bandeiras.csv";
    String COLLECTS_PATH = STATIC_PATH + "/coletas_2018-1_CA.csv";

    int STATES = 27;
    int COUNTIES = 5570;
    int REGIONS = 5;
    int PRODUCTS = 5;
    int BANNERS = 62;
}
