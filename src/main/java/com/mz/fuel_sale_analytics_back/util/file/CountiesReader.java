package com.mz.fuel_sale_analytics_back.util.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CountiesReader implements ReadFile<CountyDto> {

    /**
     * Row format: codigo_ibge,nome,latitude,longitude,capital,codigo_uf
     */
    @Override
    public List<CountyDto> read(String fullPath) throws IOException {
        final String[] rows = ReadFile.readFileAsString(FileRecords.COUNTIES_PATH).split("\n");
        List<CountyDto> counties = new ArrayList<>(rows.length);

        for (String row : rows) {
            String[] columns = row.split(",");
            counties.add(new CountyDto(null, Integer.valueOf(columns[0]), columns[1], null, Integer.valueOf(columns[5])));
        }
        return counties;
    }

}
