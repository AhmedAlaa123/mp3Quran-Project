package com.example.qurantvproject.Data.activityMainData;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LanguagePrain {
    @SerializedName("Languages")
  private   List<Language> languageList=new ArrayList<>();

    public LanguagePrain(List<Language> languageList) {
        this.languageList = languageList;
    }

    public LanguagePrain() {
    }

    public List<Language> getLanguageList() {
        return languageList;
    }

    public void setLanguageList(List<Language> languageList) {
        this.languageList = languageList;
    }
}
