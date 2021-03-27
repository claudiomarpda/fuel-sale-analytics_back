package com.mz.fuel_sale_analytics_back.util.file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public interface ReadFile<T> {

    static String readFileAsString(String fullPath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fullPath)), StandardCharsets.UTF_8).replaceAll("\r", "");
    }

    List<T> read(String fullPath) throws IOException;

}
