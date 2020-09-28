package com.example.qurantvproject.Data.activityMainData;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Language implements Serializable {
    private String id;
    private String language;
    @SerializedName("json")
    private String jsonUrl;

    public Language(String id, String language, String jsonUrl) {
        this.id = id;
        this.language = language;
        this.jsonUrl = jsonUrl;
    }

    public Language() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getJsonUrl() {
        return jsonUrl;
    }

    public void setJsonUrl(String jsonUrl) {
        this.jsonUrl = jsonUrl;
    }
}
