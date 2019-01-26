package com.example.selecaojava.util.file;

import com.example.selecaojava.model.County;
import com.example.selecaojava.model.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountyDto extends County {

    private Integer ufCode;

    CountyDto(Integer id, int ibgeCode, String name, State state, Integer ufCode) {
        super(id, ibgeCode, name, state);
        this.ufCode = ufCode;
    }
}
