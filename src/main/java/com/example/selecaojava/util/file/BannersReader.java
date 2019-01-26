package com.example.selecaojava.util.file;

import com.example.selecaojava.model.Banner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.selecaojava.util.file.FileRecords.BANNERS_PATH;

public class BannersReader implements ReadFile<Banner> {

    /**
     * Row format: name
     */
    @Override
    public List<Banner> read(String fullPath) throws IOException {
        final String[] rows = ReadFile.readFileAsString(BANNERS_PATH).split("\n");
        List<Banner> banners = new ArrayList<>(rows.length);

        for (String row : rows) {
            banners.add(new Banner(null, row));
        }
        return banners;
    }
}
