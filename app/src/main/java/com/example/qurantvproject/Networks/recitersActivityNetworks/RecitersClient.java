package com.example.qurantvproject.Networks.recitersActivityNetworks;

import com.example.qurantvproject.Data.activityMainData.Language;
import com.example.qurantvproject.Data.recitersActivityData.Reciter;
import com.example.qurantvproject.Data.recitersActivityData.RecitersPrain;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecitersClient {
    private RecitersInterface recitersInterface;
    private Language language;

    public RecitersClient(Language language) {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("https://www.mp3quran.net/api/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        recitersInterface = retrofit.create(RecitersInterface.class);
        this.language=language;
    }

    public synchronized Call<RecitersPrain> LoadReciters() {
        return this.recitersInterface.LoadReciters(language.getJsonUrl());
    }
}
