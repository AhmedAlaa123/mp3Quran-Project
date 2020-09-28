package com.example.qurantvproject.Data.suraActivityData;

import java.io.Serializable;

public class Sura implements Serializable {
    private String suraNumber;

    public Sura(String suraNumber) {
        this.suraNumber = suraNumber;
    }

    public String getSuraNumber() {
        return suraNumber;
    }
}
