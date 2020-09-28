package com.example.qurantvproject.ui.recitersActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.qurantvproject.Data.activityMainData.Language;
import com.example.qurantvproject.Data.recitersActivityData.Reciter;
import com.example.qurantvproject.Data.recitersActivityData.RecitersPrain;
import com.example.qurantvproject.Networks.recitersActivityNetworks.RecitersClient;
import com.example.qurantvproject.R;
import com.example.qurantvproject.ui.suraActivity.SurasActivity;
import com.example.qurantvproject.ui.activityMain.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecitersActivity extends AppCompatActivity
        implements RecitersAdapter.OnItemClickListner, View.OnClickListener {

    public static final String RECITER_EXTRAS = "com.example.qurantvproject.ui.recitersActivity.reciter_extras";
    private RecyclerView recyclerViewReciters;
    private ProgressBar progressBarReciters;
    private RecitersAdapter adapter;
    public static final int CODE_REQUEST = 100;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciters);
        recyclerViewReciters = findViewById(R.id.recyclerView_reciters);
        progressBarReciters = findViewById(R.id.progressBar_reciters);
        btnRetry = findViewById(R.id.btn_retry);
        btnRetry.setOnClickListener(this);
        adapter = new RecitersAdapter();
        adapter.setOnItemClickListner(this);
        recyclerViewReciters.setAdapter(adapter);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_ic);
        checkPermission();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void LoadData() {
        progressBarReciters.setVisibility(View.VISIBLE);
        Language language = (Language) getIntent().getSerializableExtra(MainActivity.LANGUAGE_EXTRAS);
        final RecitersClient recitersClient = new RecitersClient(language);
        recitersClient.LoadReciters().enqueue(new Callback<RecitersPrain>() {
            @Override
            public void onResponse(Call<RecitersPrain> call, Response<RecitersPrain> response) {
                progressBarReciters.setVisibility(View.GONE);
                adapter.setReciters(response.body());

            }

            @Override
            public void onFailure(Call<RecitersPrain> call, Throwable t) {
                progressBarReciters.setVisibility(View.GONE);
                btnRetry.setVisibility(View.VISIBLE);
                Toast.makeText(RecitersActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.INTERNET}, CODE_REQUEST);
                return;
            }
        }
        LoadData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            LoadData();
        } else {
            checkPermission();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onItemClick(Reciter reciter) {
        Intent intent = new Intent(RecitersActivity.this, SurasActivity.class);
        intent.putExtra(RecitersActivity.RECITER_EXTRAS, reciter);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        //progressBarReciters.setVisibility(View.GONE);
        checkPermission();
        btnRetry.setVisibility(View.INVISIBLE);

    }

}