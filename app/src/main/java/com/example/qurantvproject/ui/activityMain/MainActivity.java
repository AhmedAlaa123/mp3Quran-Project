package com.example.qurantvproject.ui.activityMain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.qurantvproject.Data.activityMainData.Language;
import com.example.qurantvproject.Data.activityMainData.LanguagePrain;
import com.example.qurantvproject.R;
import com.example.qurantvproject.ui.recitersActivity.RecitersActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements LanguageAdapter.OnItemClickListenr {

public static final String LANGUAGE_EXTRAS="com.example.qurantvproject.ui.activityMain.language_extras";
    private RecyclerView recyclerViewLanguages;
    LanguagePrain language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewLanguages=findViewById(R.id.recyclerViewLanguages);
        language=new LanguagePrain();
        LoadJson();
        LanguageAdapter adapter=new LanguageAdapter();
        adapter.setLanguages(language.getLanguageList());
        adapter.setItemClickListner(this);
        recyclerViewLanguages.setAdapter(adapter);

    }

    private void LoadJson()
    {
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("home.json");
            byte[] jsonText = new byte[inputStream.available()];
            inputStream.read(jsonText);
            inputStream.close();
            String str = new String(jsonText);
            Gson gson = new Gson();
            language = gson.fromJson(str, LanguagePrain.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onItemClick(Language language) {
        Intent intent =new Intent(this, RecitersActivity.class);
        intent.putExtra(MainActivity.LANGUAGE_EXTRAS,language);
        startActivity(intent);
    }
}