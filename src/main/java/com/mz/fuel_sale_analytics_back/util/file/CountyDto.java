package com.mz.fuel_sale_analytics_back.util.file;

import com.mz.fuel_sale_analytics_back.model.County;
import com.mz.fuel_sale_analytics_back.model.State;
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
