package com.mz.fuel_sale_analytics_back.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String type, Long id) {
        super(type + " não encontrado(a) com id " + id);
    }

    public NotFoundException(String type, Integer id) {
        super(type + " não encontrado(a) com id " + id);
    }

    public NotFoundException(String s) {
        super(s);
    }
}
