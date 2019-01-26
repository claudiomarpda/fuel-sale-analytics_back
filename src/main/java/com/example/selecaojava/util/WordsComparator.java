package com.example.selecaojava.util;

import java.text.Collator;
import java.util.Locale;

/**
 * Utility class to compare words without considering accents and sensitive case
 */
public interface WordsComparator {

    static boolean equals(String s1, String s2) {
        Collator collator = Collator.getInstance(new Locale("pt", "BR"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(s1, s2) == 0;
    }

}
