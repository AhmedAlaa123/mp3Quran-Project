package com.example.qurantvproject.Data.recitersActivityData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecitersPrain {
    @SerializedName("reciters")
    private List<Reciter>reciters;

    public RecitersPrain(List<Reciter> reciters) {
        this.reciters = reciters;
    }

    public List<Reciter> getReciters() {
        return reciters;
    }

    public void setReciters(List<Reciter> reciters) {
        this.reciters = reciters;
    }
}
