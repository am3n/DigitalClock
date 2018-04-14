package com.amirhosein.hello;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
    Calendar calendar;
    List<MediaPlayer> mediaPlayerList;

    int hour, minute, second;
    boolean oClock=false, h24=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digitalclock2);
        findViewsById();


        setTime();

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

    }

    private void setTime() {
        calendar = Calendar.getInstance();
        txt_hour.setText(String.format(Locale.US, "%02d", calendar.get(Calendar.HOUR_OF_DAY)));
        txt_minute.setText(String.format(Locale.US, "%02d", calendar.get(Calendar.MINUTE)));
        txt_second.setText(String.valueOf(calendar.get(Calendar.SECOND)));
        txt_month.setText(String.format(Locale.US, "%tB", calendar));
        txt_day.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        txt_year.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        txt_weekday.setText(String.format(Locale.US, "%tA", calendar));
    }


    /*
    private void sayTime_Me() {

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);

        MediaPlayer mdp_time, mdp_hour, mdp_o_hour, mdp_minute, mdp_minute_1, mdp_o_minute, mdp_minute_2, mdp_is;
        mdp_time = new MediaPlayer();
        mdp_hour = new MediaPlayer();
        mdp_o_hour = new MediaPlayer();
        mdp_minute = new MediaPlayer();
        mdp_minute_1 = new MediaPlayer();
        mdp_o_minute = new MediaPlayer();
        mdp_minute_2 = new MediaPlayer();
        mdp_is = new MediaPlayer();
        mediaPlayerList = new ArrayList<>();
        AssetFileDescriptor afd_time, afd_hour, afd_o_hour, afd_minute, afd_minute_1, afd_o_minute, afd_minute_2=null, afd_is;

        try {

            afd_time = getAssets().openFd("time.mp3");
            mdp_time.setDataSource(afd_time.getFileDescriptor()
                    , afd_time.getStartOffset(), afd_time.getLength());
            mdp_time.prepare();
            mediaPlayerList.add(mdp_time);

            if (hour>0) {
                afd_hour = getAssets().openFd(String.valueOf(hour) + ".mp3");
                mdp_hour.setDataSource(afd_hour.getFileDescriptor()
                        , afd_hour.getStartOffset(), afd_hour.getLength());
                mdp_hour.prepare();
                mediaPlayerList.add(mdp_hour);
            } else {
                afd_hour = getAssets().openFd("24.mp3");
                mdp_hour.setDataSource(afd_hour.getFileDescriptor()
                        , afd_hour.getStartOffset(), afd_hour.getLength());
                mdp_hour.prepare();
                mediaPlayerList.add(mdp_hour);
            }


            if (minute>0) {

                if (hour>0) {
                    afd_o_hour = getAssets().openFd("o.mp3");
                    mdp_o_hour.setDataSource(afd_o_hour.getFileDescriptor()
                            , afd_o_hour.getStartOffset(), afd_o_hour.getLength());
                    mdp_o_hour.prepare();
                    mediaPlayerList.add(mdp_o_hour);
                } else {
                    mediaPlayerList.remove(mediaPlayerList.size()-1);
                }

                if (minute<24) {
                    afd_minute = getAssets().openFd(String.valueOf(minute)+".mp3");
                    mdp_minute.setDataSource(afd_minute.getFileDescriptor()
                            , afd_minute.getStartOffset(), afd_minute.getLength());
                    mdp_minute.prepare();
                    mediaPlayerList.add(mdp_minute);

                } else {


                    if (minute/10 % 10 == 2) {
                        afd_minute_2 = getAssets().openFd("20.mp3");
                    } else if (minute/10 % 10 == 3) {
                        afd_minute_2 = getAssets().openFd("30.mp3");
                    } else if (minute/10 % 10 == 4) {
                        afd_minute_2 = getAssets().openFd("40.mp3");
                    } else if (minute/10 % 10 == 5) {
                        afd_minute_2 = getAssets().openFd("50.mp3");
                    }
                    mdp_minute_2.setDataSource(afd_minute_2.getFileDescriptor()
                            , afd_minute_2.getStartOffset(), afd_minute_2.getLength());
                    mdp_minute_2.prepare();
                    mediaPlayerList.add(mdp_minute_2);

                    int rem = minute % 10;
                    if (rem > 0) {

                        afd_o_minute = getAssets().openFd("o.mp3");
                        mdp_o_minute.setDataSource(afd_o_minute.getFileDescriptor()
                                , afd_o_minute.getStartOffset(), afd_o_minute.getLength());
                        mdp_o_minute.prepare();
                        mediaPlayerList.add(mdp_o_minute);

                        afd_minute_1 = getAssets().openFd(String.valueOf(rem)+".mp3");;
                        mdp_minute_1.setDataSource(afd_minute_1.getFileDescriptor()
                                , afd_minute_1.getStartOffset(), afd_minute_1.getLength());
                        mdp_minute_1.prepare();
                        mediaPlayerList.add(mdp_minute_1);
                    }
                }
            }

            if (hour==0 && minute>0) {
                afd_is = getAssets().openFd("daqiqeye_bamdad_mibashad.mp3");
                mdp_is.setDataSource(afd_is.getFileDescriptor()
                        , afd_is.getStartOffset(), afd_is.getLength());
                mdp_is.prepare();
                mediaPlayerList.add(mdp_is);

            } else if (hour>0 && minute>0) {
                afd_is = getAssets().openFd("daqiqe_mibashad.mp3");
                mdp_is.setDataSource(afd_is.getFileDescriptor()
                        , afd_is.getStartOffset(), afd_is.getLength());
                mdp_is.prepare();
                mediaPlayerList.add(mdp_is);
            } else if (minute==0) {
                afd_is = getAssets().openFd("mibashad.mp3");
                mdp_is.setDataSource(afd_is.getFileDescriptor()
                        , afd_is.getStartOffset(), afd_is.getLength());
                mdp_is.prepare();
                mediaPlayerList.add(mdp_is);
            }

            int duration = 0;
            for (final MediaPlayer mdp : mediaPlayerList) {

                if (duration>0)
                    duration-=200;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mdp.start();
                    }
                }, duration);

                duration += mdp.getDuration();

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sayTime_Women() {

        if (h24)
            hour = calendar.get(Calendar.HOUR_OF_DAY);
        else
            hour = calendar.get(Calendar.HOUR);

        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        oClock = minute==0 && second==0;
        // todo h24 = sh.getBoolean("24Hour", true);

        try {

            MediaPlayer mediaPlayerSaat = new MediaPlayer();
            mediaPlayerSaat.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    sayTime_Women_Hour();
                }
            });
            AssetFileDescriptor assetFileDescriptorSaat = getAssets().openFd("saat.wav");
            mediaPlayerSaat.setDataSource(assetFileDescriptorSaat.getFileDescriptor()
                    , assetFileDescriptorSaat.getStartOffset(), assetFileDescriptorSaat.getLength());
            mediaPlayerSaat.prepare();
            mediaPlayerSaat.start();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sayTime_Women_Hour() {
        try {

            final MediaPlayer mediaPlayerHour = new MediaPlayer();
            mediaPlayerHour.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (minute!=0) {
                        sayTime_Women_Minute();
                    } else if (second!=0) {
                        sayTime_Women_Second();
                    }
                }
            });


            if (h24) {

                if (hour>=1 && hour<=20) {

                    String name = hour + (oClock?"":"-o") + ".wav";
                    AssetFileDescriptor assetFileDescriptor = getAssets().openFd(name);
                    mediaPlayerHour.setDataSource(assetFileDescriptor.getFileDescriptor()
                            , assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                    mediaPlayerHour.prepare();
                    mediaPlayerHour.start();

                } else if ((hour>=21 && hour<=23) || (hour==24 && oClock)) {

                    String name1 = (hour%10) + (oClock?"":"-o") + ".wav";
                    AssetFileDescriptor assetFileDescriptor1 = getAssets().openFd(name1);
                    mediaPlayerHour.setDataSource(assetFileDescriptor1.getFileDescriptor()
                            , assetFileDescriptor1.getStartOffset(), assetFileDescriptor1.getLength());
                    mediaPlayerHour.prepare();

                    String name2 =  "20" + "-o" + ".wav";
                    AssetFileDescriptor assetFileDescriptor2 = getAssets().openFd(name2);
                    MediaPlayer mediaPlayer2 = new MediaPlayer();
                    mediaPlayer2.setDataSource(assetFileDescriptor2.getFileDescriptor()
                            , assetFileDescriptor2.getStartOffset(), assetFileDescriptor2.getLength());
                    mediaPlayer2.prepare();
                    mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mediaPlayerHour.start();
                        }
                    });
                    mediaPlayer2.start();

                } else {
                    if (minute!=0) {
                        sayTime_Women_Minute();
                    } else if (second!=0) {
                        sayTime_Women_Second();
                    }
                }

                //------------ 12 Hour -------------------------------------------------------------
            } else {



            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sayTime_Women_Minute() {

        try {

            String nameDaqiqeh = "daghigheh" + (second==0?"":"-o") + ".wav";
            AssetFileDescriptor assetFileDescriptorDaqiqeh = getAssets().openFd(nameDaqiqeh);
            final MediaPlayer mediaPlayerDaqiqeh = new MediaPlayer();
            mediaPlayerDaqiqeh.setDataSource(assetFileDescriptorDaqiqeh.getFileDescriptor(),
                    assetFileDescriptorDaqiqeh.getStartOffset(), assetFileDescriptorDaqiqeh.getLength());
            mediaPlayerDaqiqeh.prepare();
            mediaPlayerDaqiqeh.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (second!=0) {
                        sayTime_Women_Second();
                    }
                }
            });


            if ((minute>=1 && minute<=20) || minute==30 || minute==40 || minute==50) {

                String name = minute + ".wav";
                AssetFileDescriptor assetFileDescriptorMinute = getAssets().openFd(name);
                MediaPlayer mediaPlayerMinute = new MediaPlayer();
                mediaPlayerMinute.setDataSource(assetFileDescriptorMinute.getFileDescriptor()
                        , assetFileDescriptorMinute.getStartOffset(), assetFileDescriptorMinute.getLength());
                mediaPlayerMinute.prepare();
                mediaPlayerMinute.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayerDaqiqeh.start();
                    }
                });
                mediaPlayerMinute.start();

            } else if (minute!=0) {

                String nameMinute1 = (minute%10) + ".wav";
                AssetFileDescriptor assetFileDescriptorMinute1 = getAssets().openFd(nameMinute1);
                final MediaPlayer mediaPlayerMinute1 = new MediaPlayer();
                mediaPlayerMinute1.setDataSource(assetFileDescriptorMinute1.getFileDescriptor()
                        , assetFileDescriptorMinute1.getStartOffset(), assetFileDescriptorMinute1.getLength());
                mediaPlayerMinute1.prepare();
                mediaPlayerMinute1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayerDaqiqeh.start();
                    }
                });

                int m = (minute/10 % 10) * 10;
                String nameMinute2 = m + "-o" + ".wav";
                AssetFileDescriptor assetFileDescriptorMinute2 = getAssets().openFd(nameMinute2);
                MediaPlayer mediaPlayerMinute2 = new MediaPlayer();
                mediaPlayerMinute2.setDataSource(assetFileDescriptorMinute2.getFileDescriptor()
                        , assetFileDescriptorMinute2.getStartOffset(), assetFileDescriptorMinute2.getLength());
                mediaPlayerMinute2.prepare();
                mediaPlayerMinute2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayerMinute1.start();
                    }
                });
                mediaPlayerMinute2.start();

            } else {
                if (second!=0) {
                    sayTime_Women_Second();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void sayTime_Women_Second() {

        try {

            String nameSaniyeh = "sanieh" + ".wav";
            AssetFileDescriptor assetFileDescriptorSaniyeh = getAssets().openFd(nameSaniyeh);
            final MediaPlayer mediaPlayerSaniyeh = new MediaPlayer();
            mediaPlayerSaniyeh.setDataSource(assetFileDescriptorSaniyeh.getFileDescriptor(),
                    assetFileDescriptorSaniyeh.getStartOffset(), assetFileDescriptorSaniyeh.getLength());
            mediaPlayerSaniyeh.prepare();


            if (second<=20 || second==30 || second==40 || second==50) {

                String name = second + ".wav";
                AssetFileDescriptor assetFileDescriptorSecond = getAssets().openFd(name);
                MediaPlayer mediaPlayerSecond = new MediaPlayer();
                mediaPlayerSecond.setDataSource(assetFileDescriptorSecond.getFileDescriptor()
                        , assetFileDescriptorSecond.getStartOffset(), assetFileDescriptorSecond.getLength());
                mediaPlayerSecond.prepare();
                mediaPlayerSecond.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayerSaniyeh.start();
                    }
                });
                mediaPlayerSecond.start();

            } else {

                String nameSecond1 = (second%10) + ".wav";
                AssetFileDescriptor assetFileDescriptorSecond1 = getAssets().openFd(nameSecond1);
                final MediaPlayer mediaPlayerSecond1 = new MediaPlayer();
                mediaPlayerSecond1.setDataSource(assetFileDescriptorSecond1.getFileDescriptor()
                        , assetFileDescriptorSecond1.getStartOffset(), assetFileDescriptorSecond1.getLength());
                mediaPlayerSecond1.prepare();
                mediaPlayerSecond1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayerSaniyeh.start();
                    }
                });

                int s = (second/10 % 10) * 10;
                String nameSecond2 = s + "-o" + ".wav";
                AssetFileDescriptor assetFileDescriptorSecond2 = getAssets().openFd(nameSecond2);
                MediaPlayer mediaPlayerSecond2 = new MediaPlayer();
                mediaPlayerSecond2.setDataSource(assetFileDescriptorSecond2.getFileDescriptor()
                        , assetFileDescriptorSecond2.getStartOffset(), assetFileDescriptorSecond2.getLength());
                mediaPlayerSecond2.prepare();
                mediaPlayerSecond2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayerSecond1.start();
                    }
                });
                mediaPlayerSecond2.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /*MediaPlayer.OnCompletionListener listener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayerList.remove(0);
            if (mediaPlayerList.size()>0) {
                mediaPlayer = mediaPlayerList.get(0);
                mediaPlayer.setOnCompletionListener(listener);
                mediaPlayer.start();
            }
        }
    };*/


    private void sayTime_Women2() {

        final List<String> playList = new ArrayList<>();

        if (h24)
            hour = calendar.get(Calendar.HOUR_OF_DAY);
        else
            hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        oClock = minute==0 && second==0;
        // todo h24 = sh.getBoolean("24Hour", true);


        if (h24) {
            playList.add("saat.wav");
            if (hour>=1 && hour<=20) {
                playList.add(hour + (oClock?"":"-o") + ".wav");
            } else if ((hour>=21 && hour<=23) || (hour==24 && oClock)) {
                playList.add("20" + "-o" + ".wav");
                playList.add((hour%10) + (oClock?"":"-o") + ".wav");
            }
        } else {

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
