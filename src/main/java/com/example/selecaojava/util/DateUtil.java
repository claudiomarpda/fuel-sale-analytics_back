package com.example.selecaojava.util;

import com.example.selecaojava.exception.InvalidInputException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public interface DateUtil {

    static LocalDate getLocalDate(String date) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }

    static String getDateForH2(String date) {
        try {
            LocalDate d = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return d.getYear() + "-" + d.getMonthValue() + "-" + d.getDayOfMonth();
        } catch(DateTimeParseException e) {
            throw new InvalidInputException("Formato " + date + " recebido inválido. Enviar no formato dd-mm-yyyy");
        }
    }
}
