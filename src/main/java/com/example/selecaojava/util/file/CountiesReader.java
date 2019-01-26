package com.example.selecaojava.util.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.selecaojava.util.file.FileRecords.COUNTIES_PATH;

public class CountiesReader implements ReadFile<CountyDto> {

    /**
     * Row format: codigo_ibge,nome,latitude,longitude,capital,codigo_uf
     */
    @Override
    public List<CountyDto> read(String fullPath) throws IOException {
        final String[] rows = ReadFile.readFileAsString(COUNTIES_PATH).split("\n");
        List<CountyDto> counties = new ArrayList<>(rows.length);

        for (String row : rows) {
            String[] columns = row.split(",");
            counties.add(new CountyDto(null, Integer.valueOf(columns[0]), columns[1], null, Integer.valueOf(columns[5])));
        }
        return counties;
    }

}
