package com.example.selecaojava.util.file;

import com.example.selecaojava.model.Region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegionsReader implements ReadFile<Region> {

    /**
     * Row format: code, name
     */
    @Override
    public List<Region> read(String fullPath) throws IOException {
        final String[] rows = ReadFile.readFileAsString(FileRecords.REGIONS_PATH).split("\n");
        List<Region> regions = new ArrayList<>(rows.length);

        for (String row : rows) {
            String[] columns = row.split(",");
            regions.add(new Region(null, columns[0], columns[1]));
        }
        return regions;
    }

}
