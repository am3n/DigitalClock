package com.amirhosein.hello;

import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import junit.framework.AssertionFailedError;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DigitalClockActivity extends AppCompatActivity {

    TextView txt_hour, txt_minute, txt_second, txt_month, txt_day, txt_year, txt_weekday;
    RadioGroup rdb_gp;
    Calendar calendar;
    List<MediaPlayer> mediaPlayerList;
    SharedPreferences sh;

    int hour, minute, second;
    boolean oClock=false, h24=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digitalclock2);
        findViewsById();

        sh = getSharedPreferences("setting", MODE_PRIVATE);
        h24 = sh.getBoolean("h24", true);

        if (h24)
            rdb_gp.check(R.id.rdb_24h);
        else
            rdb_gp.check(R.id.rdb_12h);


        setTime();

        rdb_gp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rdb_24h:
                        h24 = true;
                        break;
                    case R.id.rdb_12h:
                        h24 = false;
                        break;
                }
                sh.edit().putBoolean("h24", h24).apply();
                setTime();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activitymain_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.time:
                setTime();
                break;
            case R.id.speak:
                setTime();
                sayTime_Women2();
                break;
        }
        return true;
    }

    //**********************************************************************************************

    private void findViewsById() {

        txt_hour = findViewById(R.id.hour);
        txt_minute = findViewById(R.id.minute);
        txt_second = findViewById(R.id.second);
        txt_month = findViewById(R.id.month);
        txt_day = findViewById(R.id.day);
        txt_year = findViewById(R.id.year);
        txt_weekday = findViewById(R.id.weekday);
        rdb_gp = findViewById(R.id.rdb_gp);

        RadioButton rdb_24h = findViewById(R.id.rdb_24h);
        RadioButton rdb_12h = findViewById(R.id.rdb_12h);
        Typeface font = Typeface.createFromAsset(getAssets(), "digital7.otf");
        rdb_24h.setTypeface(font);
        rdb_12h.setTypeface(font);
    }

    private void setTime() {
        calendar = Calendar.getInstance();
        txt_hour.setText(String.format(Locale.US, "%02d", h24 ? calendar.get(Calendar.HOUR_OF_DAY) : calendar.get(Calendar.HOUR)));
        txt_minute.setText(String.format(Locale.US, "%02d", calendar.get(Calendar.MINUTE)));
        txt_second.setText(String.valueOf(calendar.get(Calendar.SECOND)));
        txt_month.setText(String.format(Locale.US, "%tB", calendar));
        txt_day.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        txt_year.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        txt_weekday.setText(String.format(Locale.US, "%tA", calendar));
    }

    private void sayTime_Women2() {

        final List<String> playList = new ArrayList<>();

        if (h24)
            hour = calendar.get(Calendar.HOUR_OF_DAY);
        else
            hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        oClock = minute==0 && second==0;


        playList.add("saat.wav");

        if (h24) {
            if (hour>=1 && hour<=20) {
                playList.add(hour + (oClock?"":"-o") + ".wav");
            } else if ((hour>=21 && hour<=23) || (hour==24 && oClock)) {
                playList.add("20" + "-o" + ".wav");
                playList.add((hour%10) + (oClock?"":"-o") + ".wav");
            }
        } else {
            playList.add(hour + (oClock?"":"-o") + ".wav");
        }

        if (minute!=0) {
            if ((minute>=1 && minute<=20) || minute==30 || minute==40 || minute==50) {
                playList.add(minute + ".wav");
            } else if (minute!=0) {
                playList.add(((minute/10 % 10) * 10) + "-o" + ".wav");
                playList.add((minute%10) + ".wav");
            }
            playList.add("daghigheh" + (second==0?"":"-o") + ".wav");
        }

        if (second!=0) {
            if (second<=20 || second==30 || second==40 || second==50) {
                playList.add(second + ".wav");
            } else {
                playList.add(((second/10 % 10) * 10) + "-o" + ".wav");
                playList.add((second%10) + ".wav");
            }
            playList.add("sanieh.wav");
        }

        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            AssetFileDescriptor descriptor = getAssets().openFd(playList.get(0));
            mediaPlayer.setDataSource(descriptor.getFileDescriptor()
                    , descriptor.getStartOffset(), descriptor.getLength());
            playList.remove(0);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    try {
                        mediaPlayer.stop();
                        if (playList.size()>0) {
                            mediaPlayer.reset();
                            AssetFileDescriptor descriptor = getAssets().openFd(playList.get(0));
                            mediaPlayer.setDataSource(descriptor.getFileDescriptor()
                                    , descriptor.getStartOffset(), descriptor.getLength());
                            playList.remove(0);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
