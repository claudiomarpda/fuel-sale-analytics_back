package com.mz.fuel_sale_analytics_back.util.file;

import com.mz.fuel_sale_analytics_back.model.Region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mz.fuel_sale_analytics_back.util.file.ReadFile.readFileAsString;

public class RegionsReader implements ReadFile<Region> {

    /**
     * Row format: code, name
     */
    @Override
    public List<Region> read(String fullPath) throws IOException {
        final String[] rows = readFileAsString(FileRecords.REGIONS_PATH).split("\n");
        List<Region> regions = new ArrayList<>(rows.length);

        for (String row : rows) {
            String[] columns = row.split(",");
            regions.add(new Region(null, columns[0], columns[1]));
        }
        return regions;
    }

}
