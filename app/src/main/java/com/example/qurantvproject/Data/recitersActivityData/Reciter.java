package com.example.qurantvproject.Data.recitersActivityData;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Reciter implements Serializable {
    private String id;
    private String name;
    @SerializedName("Server")
    private String ServerUrl;
    private String rewaya;
    private String count;
    private String letter;
    private String suras;

    public Reciter(String id, String name, String serverUrl, String rewaya, String count, String letter, String suras) {
        this.id = id;
        this.name = name;
        ServerUrl = serverUrl;
        this.rewaya = rewaya;
        this.count = count;
        this.letter = letter;
        this.suras = suras;
    }

    public Reciter() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerUrl() {
        return ServerUrl;
    }

    public void setServerUrl(String serverUrl) {
        ServerUrl = serverUrl;
    }

    public String getRewaya() {
        return rewaya;
    }

    public void setRewaya(String rewaya) {
        this.rewaya = rewaya;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getSuras() {
        return suras;
    }

    public void setSuras(String suras) {
        this.suras = suras;
    }
}
