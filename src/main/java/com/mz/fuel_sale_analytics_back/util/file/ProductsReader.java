package com.mz.fuel_sale_analytics_back.util.file;

import com.mz.fuel_sale_analytics_back.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mz.fuel_sale_analytics_back.util.file.FileRecords.PRODUCTS_PATH;

public class ProductsReader implements ReadFile<Product> {

    /**
     * Row format: name
     */
    @Override
    public List<Product> read(String fullPath) throws IOException {
        final String[] rows = ReadFile.readFileAsString(PRODUCTS_PATH).split("\n");
        List<Product> products = new ArrayList<>(rows.length);

        for (String row : rows) {
            products.add(new Product(null, row));
        }
        return products;
    }
}
