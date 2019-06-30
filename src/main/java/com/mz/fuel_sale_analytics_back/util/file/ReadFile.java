package com.mz.fuel_sale_analytics_back.util.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public interface ReadFile<T> {

    static String readFileAsString(String fullPath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fullPath)));
    }

    List<T> read(String fullPath) throws IOException;

}
