package com.example.qurantvproject.ui.suraActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.qurantvproject.Data.recitersActivityData.Reciter;
import com.example.qurantvproject.Data.suraActivityData.Sura;
import com.example.qurantvproject.ui.PlaySoundActivity.PlaySoundActivity;
import com.example.qurantvproject.R;
import com.example.qurantvproject.ui.recitersActivity.RecitersActivity;

import java.util.ArrayList;
import java.util.List;

public class SurasActivity extends AppCompatActivity implements SuraAdapter.OnItemClickListner {

    public static final String SOUND_URL = "url";
    private Reciter reciter;
    private RecyclerView recyclerViewSuras;
    private SuraAdapter adapter;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suras);
        recyclerViewSuras = findViewById(R.id.recyclerView_suras);
        adapter = new SuraAdapter();
        adapter.setOnItemClickListner(this);
        recyclerViewSuras.setAdapter(adapter);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_ic);
        LoadSuras();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void LoadSuras() {
        reciter = (Reciter) getIntent().getSerializableExtra(RecitersActivity.RECITER_EXTRAS);
        String[] suras = reciter.getSuras().split(",");
        List<Sura> suraList = new ArrayList<>();
        for (int i = 0; i < suras.length; i++) {
            switch (suras[i].length()) {
                case 1:
                    suraList.add(new Sura("00" + suras[i]));
                    break;
                case 2:
                    suraList.add(new Sura("0" + suras[i]));
                    break;
                default:
                    suraList.add(new Sura(suras[i]));
                    break;

            }
        }
        adapter.setSuras(suraList);


    }

    @Override
    public void onBackPressed() {
        if (player != null) player.release();
        finish();
    }

    @Override
    protected void onStop() {
        if (player != null) player.release();
        super.onStop();
    }

    @Override
    public void onItemClick(Sura sura) {
        Intent intent = new Intent(this, PlaySoundActivity.class);
        intent.putExtra(SurasActivity.SOUND_URL, reciter.getServerUrl() + "/" + sura.getSuraNumber() + ".mp3");
        startActivity(intent);


    }

}