package com.mz.fuel_sale_analytics_back.util.file;

import com.mz.fuel_sale_analytics_back.model.State;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatesReader implements ReadFile<State> {

    /**
     * Row format: uf code, uf name, name
     */
    @Override
    public List<State> read(String fullPath) throws IOException {
        final String[] rows = ReadFile.readFileAsString(FileRecords.STATES_PATH).split("\n");
        List<State> states = new ArrayList<>(rows.length);

        for (String row : rows) {
            String[] columns = row.split(",");
            states.add(new State(null, Integer.valueOf(columns[0]), columns[1], columns[2]));
        }
        return states;
    }

}
