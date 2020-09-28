package com.example.qurantvproject.ui.PlaySoundActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.qurantvproject.R;
import com.example.qurantvproject.ui.suraActivity.SurasActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PlaySoundActivity extends AppCompatActivity implements View.OnClickListener {

    private SeekBar seekBar;
    private MediaPlayer player;
    private Handler handler;
    private Runnable runnable;
    private ImageView imageView_play_button;
    private ImageView imageView_play_next;
    private ImageView imageView_play_back;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;

    private boolean playerIsPause = false;
    private int seconds = 0;
    private int minuts = 0;

    public static final int PERMMISSION_CODE = 155;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_sound);
        seekBar = findViewById(R.id.seekBar2);
        imageView_play_button = findViewById(R.id.imageView_start_pause);
        imageView_play_next = findViewById(R.id.imageView_next);
        imageView_play_back = findViewById(R.id.imageView_back);
        progressBar = findViewById(R.id.progressBar_play_sound);
        imageView_play_button.setOnClickListener(this);
        imageView_play_next.setOnClickListener(this);
        imageView_play_back.setOnClickListener(this);
        handler = new Handler();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_ic);
        LoadSound();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    // checking for write on storage permission for sdk >=M

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMMISSION_CODE);
            }
        }
        startingDownload();

    }


    // checking for write on storage permission for sdk >=M
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startingDownload();
        } else {
            checkPermission();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.playsound_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();

        checkPermission();
        return true;
    }

    private void startingDownload() {
        final DownloadAsync downloadAsync = new DownloadAsync();
        progressDialog = new ProgressDialog(PlaySoundActivity.this);
        progressDialog.setTitle("Download is started");
        progressDialog.setMessage("Downloading ...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadAsync.cancel(true);
            }
        });

        downloadAsync.execute("http://server8.mp3quran.net/ahmad_huth/112.mp3");
    }

    private void LoadSound() {
        if (player != null)
            player.release();
        String imageurl = getIntent().getStringExtra(SurasActivity.SOUND_URL);
        Uri uri = Uri.parse(imageurl);
        player = new MediaPlayer();

        try {
            player.setDataSource(this, uri);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        player.prepare();
                    } catch (IOException e) {
                        Toast.makeText(PlaySoundActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }).start();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    seekBar.setMax(player.getDuration());
                    seconds = player.getDuration() / 1000;
                    minuts = seconds / 60;
                    Log.d("TAG", "onPrepared: " + seconds);
                    Log.d("TAG", "onPrepared: " + minuts);

                    player.start();
                    playCycle();
                    progressBar.setVisibility(View.GONE);
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser)
                                player.seekTo(progress);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                }

            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playCycle() {
        seekBar.setProgress(player.getCurrentPosition());

        if (player.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCycle();


                }
            };
            handler.postDelayed(runnable, 1000);
        }
        if (!player.isPlaying() && !playerIsPause) {
            seekBar.setProgress(0);
            imageView_play_button.setImageResource(R.drawable.ic_play);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_start_pause: {
                if (player.isPlaying()) {
                    player.pause();
                    playerIsPause = true;
                    imageView_play_button.setImageResource(R.drawable.ic_play);
                } else {
                    player.start();
                    playerIsPause = false;
                    imageView_play_button.setImageResource(R.drawable.ic_pause);
                    playCycle();
                }
            }
            break;
            case R.id.imageView_next:
                if (player != null && player.getCurrentPosition() + 1000 < player.getDuration()) {
                    player.seekTo(player.getCurrentPosition() + 1000);
                } else player.seekTo(player.getDuration());
                seekBar.setProgress(player.getCurrentPosition());

                break;
            case R.id.imageView_back:
                if (player != null && player.getCurrentPosition() - 1000 > 0) {
                    player.seekTo(player.getCurrentPosition() - 1000);
                } else player.seekTo(0);
                seekBar.setProgress(player.getCurrentPosition());
                break;

        }
    }

    private class DownloadAsync extends AsyncTask<String, Integer, Void> {
        File storageDir;
        File outPutFile;

        @Override
        protected Void doInBackground(String... urls) {
            InputStream input = null;
            OutputStream outputStream = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(PlaySoundActivity.this, "Server returned Http " +
                            connection.getResponseCode() + "  " + connection.getResponseMessage(), Toast.LENGTH_LONG).show();
                    return null;
                }
                String[] filen = urls[0].split("/");
                input = connection.getInputStream();
                //outPutFile = new File("/SDCARD/", filen[filen.length - 1]);
              //  Log.d("TAG2", "doInBackground: " + outPutFile);
                outputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + filen[filen.length - 1]);
                //Log.d("TAG2", "doInBackground: " + getFilesDir());

                byte[] buffer = new byte[1024];
                int fileSize = connection.getContentLength();
                Log.d("TAG2", "doInBackground: " + fileSize);
                int count;
                int total = 0;


                while ((count = input.read(buffer)) != -1) {

                    outputStream.write(buffer, 0, count);
                    total += count;
                    publishProgress((total / fileSize) * 100);
                }


                //TODO Completing Download File
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            storageDir = Environment.getExternalStorageDirectory();
            Log.d("TAG2", "onPreExecute: " + storageDir);
            //boolean f=  new File(storageDir+"/Quran").mkdirs();
            // Log.d("TAG2", "onPreExecute: "+f);

            progressDialog.show();


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.cancel();
            Toast.makeText(PlaySoundActivity.this, "File Downloaded", Toast.LENGTH_LONG).show();
        }

        @Override

        protected void onProgressUpdate(Integer... values) {

            progressDialog.setProgress(values[0]);
        }
    }

}
