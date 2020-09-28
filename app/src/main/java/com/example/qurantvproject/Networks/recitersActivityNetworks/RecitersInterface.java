package com.example.qurantvproject.Networks.recitersActivityNetworks;

import com.example.qurantvproject.Data.recitersActivityData.Reciter;
import com.example.qurantvproject.Data.recitersActivityData.RecitersPrain;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RecitersInterface {
    @GET("{language}")
    Call<RecitersPrain> LoadReciters(@Path(value = "language") String language);

}
