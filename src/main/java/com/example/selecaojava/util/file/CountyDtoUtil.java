package com.example.selecaojava.util.file;

import com.example.selecaojava.exception.NotFoundException;
import com.example.selecaojava.model.County;
import com.example.selecaojava.model.State;
import com.example.selecaojava.repository.StateRepository;

import java.util.ArrayList;
import java.util.List;

public interface CountyDtoUtil {

    /**
     * Get ufCode from County, find State and relate them
     */
    static List<County> getFromDto(List<CountyDto> dtoList, StateRepository stateRepository) {
        List<County> counties = new ArrayList<>(dtoList.size());

        dtoList.forEach(c -> {
            State state = stateRepository.findByUfCode(c.getUfCode()).orElseThrow(() -> new NotFoundException("Estado não encontrado: " + c.getUfCode()));
            counties.add(new County(null, c.getIbgeCode(), c.getName(), state));
        });

        return counties;
    }

}
