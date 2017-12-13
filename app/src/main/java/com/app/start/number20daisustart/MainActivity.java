package com.app.start.number20daisustart;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private NumberPicker NumberPicker1, NumberPicker2, NumberPicker3, NumberPicker4, NumberPicker5, NumberPicker6, NumberPicker7,
            NumberPicker8, NumberPicker9, NumberPicker10, NumberPicker11, NumberPicker12, NumberPicker13, NumberPicker14, NumberPicker15, NumberPicker16, NumberPicker17, NumberPicker18,
            NumberPicker19, NumberPicker20;
    int count[] = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    private InterstitialAd interstitial;
    private AdView mAdView;
    private AdRequest adRequest = new AdRequest.Builder().build();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // インタースティシャルを作成する。
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-4788419399315285/6103143854");
        // 広告リクエストを作成する。
        AdRequest adRequestI = new AdRequest.Builder().build();
        // インタースティシャルの読み込みを開始する。
        interstitial.loadAd(adRequestI);

        //バナー広告//
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.loadAd(adRequest);
        //バナー広告終わり//

        //レビューの広告//
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int s = sp.getInt("START", 0);
        sp.edit().putInt("START", (s + 1)).commit();
        s = sp.getInt("START", 0);

        preference = getSharedPreferences("Preference Name", MODE_PRIVATE);
        editor = preference.edit();

        if (preference.getBoolean("Launched", false) == false) {
            //2回に1回表示
            if (s % 2 == 0) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(
                        MainActivity.this);
                dialog.setTitle("ありがとうございます！");
                dialog.setMessage("このアプリをインストールしていただき、ありがとうございます！もしよろしければ、レビューを書いていただけると嬉しいです！(広告の仕様を少し変更しました。)");
                dialog.setPositiveButton("レビューを書く",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editor.putBoolean("Launched", true);
                                editor.commit();
                                // TODO 自動生成されたメソッド・スタブ
                                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.app.start.number20daisustart");
                                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(i);
                            }
                        });
                dialog.setNegativeButton("また後で",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO 自動生成されたメソッド・スタブ
                            }
                        });
                dialog.setNegativeButton("もう表示しない", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // NGボタン押下時の処理
                        editor.putBoolean("Launched", true);
                        editor.commit();
                    }
                });


                dialog.show();
            }
        } else {

        }

    }
    @Override
    public void onStart() {
        super.onStart();
        Tracker t = ((AnalyticsApplication)getApplication()).getTracker(AnalyticsApplication.TrackerName.APP_TRACKER);
        t.setScreenName(this.getClass().getSimpleName());
        t.send(new HitBuilders.AppViewBuilder().build());
    }
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
    //レビューの広告終わり//
    public void onClick(View view) {
        //ここはサイコロを振るボタン//
        switch (view.getId()) {
            case R.id.mainbutton1:
                diceroll(view);
                break;

            //これは確率変動画面にかんいするためのボタン//
            case R.id.mainbutton2:
                setContentView(R.layout.tuning);
                AdRequest adRequest = new AdRequest.Builder().build();
                AdView mAdView = (AdView) findViewById(R.id.adView);
                mAdView.loadAd(adRequest);
                interstitial.show();

                TextView t2 = (TextView) findViewById(R.id.counta);
                t2.setText("合計確立を100%にしてください");
                t2.setTextSize(30.0f);
                break;
            //ここから確率変動ボタン処理//
            /* case R.id.buttonX:
                View rinkX = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPickerX = (NumberPicker)rinkX.findViewById(R.id.numberPicker);
                NumberPickerX.setMaxValue(100);
                NumberPickerX.setMinValue(0);
                NumberPickerX.setValue(count[X-1]);
                new AlertDialog.Builder(this)
                      .setView(rinkX)
                      .show();
                count[X-1] =NumberPickerX.getValue();
                NumberPickerX.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[X-1] = NumberPickerX.getValue();
                    }
                });
                break;*/
            //1の確率//
            case R.id.button1:
                View rink1 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker1 = (NumberPicker)rink1.findViewById(R.id.numberPicker);
                NumberPicker1.setMaxValue(100);
                NumberPicker1.setMinValue(0);
                NumberPicker1.setValue(count[0]);
                new AlertDialog.Builder(this)
                        .setView(rink1)
                        .show();
                count[0] =NumberPicker1.getValue();
                NumberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[0] = NumberPicker1.getValue();
                        //合計確率の表示//
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                //
                break;
            //2の確率//
            case R.id.button2:
                View rink2 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker2 = (NumberPicker)rink2.findViewById(R.id.numberPicker);
                NumberPicker2.setMaxValue(100);
                NumberPicker2.setMinValue(0);
                NumberPicker2.setValue(count[1]);
                new AlertDialog.Builder(this)
                        .setView(rink2)
                        .show();
                count[1] =NumberPicker2.getValue();
                NumberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[1] = NumberPicker2.getValue();
                        //合計確率の表示//
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //3の確率//
            case R.id.button3:
                View rink3 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker3 = (NumberPicker)rink3.findViewById(R.id.numberPicker);
                NumberPicker3.setMaxValue(100);
                NumberPicker3.setMinValue(0);
                NumberPicker3.setValue(count[2]);
                new AlertDialog.Builder(this)
                        .setView(rink3)
                        .show();
                count[2] =NumberPicker3.getValue();
                NumberPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[2] = NumberPicker3.getValue();
                        //合計確率の表示//
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //4の確率//
            case R.id.button4:
                View rink4 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker4 = (NumberPicker)rink4.findViewById(R.id.numberPicker);
                NumberPicker4.setMaxValue(100);
                NumberPicker4.setMinValue(0);
                NumberPicker4.setValue(count[3]);
                new AlertDialog.Builder(this)
                        .setView(rink4)
                        .show();
                count[3] =NumberPicker4.getValue();
                NumberPicker4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[3] = NumberPicker4.getValue();
                        //合計確率の表示//
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //5の確率//
            case R.id.button5:
                View rink5 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker5 = (NumberPicker)rink5.findViewById(R.id.numberPicker);
                NumberPicker5.setMaxValue(100);
                NumberPicker5.setMinValue(0);
                NumberPicker5.setValue(count[4]);
                new AlertDialog.Builder(this)
                        .setView(rink5)
                        .show();
                count[4] =NumberPicker5.getValue();
                NumberPicker5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[4] = NumberPicker5.getValue();
                        //合計確率の表示//
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //6の確率//
            case R.id.button6:
                View rink6 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker6 = (NumberPicker)rink6.findViewById(R.id.numberPicker);
                NumberPicker6.setMaxValue(100);
                NumberPicker6.setMinValue(0);
                NumberPicker6.setValue(count[5]);
                new AlertDialog.Builder(this)
                        .setView(rink6)
                        .show();
                count[5] =NumberPicker6.getValue();
                NumberPicker6.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[5] = NumberPicker6.getValue();
                        //合計確率の表示//
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //7の確率//
            case R.id.button7:
                View rink7 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker7 = (NumberPicker)rink7.findViewById(R.id.numberPicker);
                NumberPicker7.setMaxValue(100);
                NumberPicker7.setMinValue(0);
                NumberPicker7.setValue(count[6]);
                new AlertDialog.Builder(this)
                        .setView(rink7)
                        .show();
                count[6] =NumberPicker7.getValue();
                NumberPicker7.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[6] = NumberPicker7.getValue();
                        //合計確率の表示//
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //8の確率//
            case R.id.button8:
                View rink8 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker8 = (NumberPicker)rink8.findViewById(R.id.numberPicker);
                NumberPicker8.setMaxValue(100);
                NumberPicker8.setMinValue(0);
                NumberPicker8.setValue(count[7]);
                new AlertDialog.Builder(this)
                        .setView(rink8)
                        .show();
                count[7] =NumberPicker8.getValue();
                NumberPicker8.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[7] = NumberPicker8.getValue();
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //9の確率//
            case R.id.button9:
                View rink9 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker9 = (NumberPicker)rink9.findViewById(R.id.numberPicker);
                NumberPicker9.setMaxValue(100);
                NumberPicker9.setMinValue(0);
                NumberPicker9.setValue(count[8]);
                new AlertDialog.Builder(this)
                        .setView(rink9)
                        .show();
                count[8] =NumberPicker9.getValue();
                NumberPicker9.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[8] = NumberPicker9.getValue();
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //10の確率//
            case R.id.button10:
                View rink10 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker10 = (NumberPicker)rink10.findViewById(R.id.numberPicker);
                NumberPicker10.setMaxValue(100);
                NumberPicker10.setMinValue(0);
                NumberPicker10.setValue(count[9]);
                new AlertDialog.Builder(this)
                        .setView(rink10)
                        .show();
                count[9] =NumberPicker10.getValue();
                NumberPicker10.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[9] = NumberPicker10.getValue();
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //11の確率//
            case R.id.button11:
                View rink11 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker11 = (NumberPicker)rink11.findViewById(R.id.numberPicker);
                NumberPicker11.setMaxValue(100);
                NumberPicker11.setMinValue(0);
                NumberPicker11.setValue(count[10]);
                new AlertDialog.Builder(this)
                        .setView(rink11)
                        .show();
                count[10] =NumberPicker11.getValue();
                NumberPicker11.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[10] = NumberPicker11.getValue();
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //12の確率//
            case R.id.button12:
                View rink12 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker12 = (NumberPicker)rink12.findViewById(R.id.numberPicker);
                NumberPicker12.setMaxValue(100);
                NumberPicker12.setMinValue(0);
                NumberPicker12.setValue(count[11]);
                new AlertDialog.Builder(this)
                        .setView(rink12)
                        .show();
                count[11] =NumberPicker12.getValue();
                NumberPicker12.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[11] = NumberPicker12.getValue();
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //13の確率//
            case R.id.button13:
                View rink13 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker13 = (NumberPicker)rink13.findViewById(R.id.numberPicker);
                NumberPicker13.setMaxValue(100);
                NumberPicker13.setMinValue(0);
                NumberPicker13.setValue(count[12]);
                new AlertDialog.Builder(this)
                        .setView(rink13)
                        .show();
                count[12] =NumberPicker13.getValue();
                NumberPicker13.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[12] = NumberPicker13.getValue();
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //14の確率//
            case R.id.button14:
                View rink14 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker14 = (NumberPicker)rink14.findViewById(R.id.numberPicker);
                NumberPicker14.setMaxValue(100);
                NumberPicker14.setMinValue(0);
                NumberPicker14.setValue(count[13]);
                new AlertDialog.Builder(this)
                        .setView(rink14)
                        .show();
                count[13] =NumberPicker14.getValue();
                NumberPicker14.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[13] = NumberPicker14.getValue();
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //15の確率//
            case R.id.button15:
                View rink15 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker15 = (NumberPicker)rink15.findViewById(R.id.numberPicker);
                NumberPicker15.setMaxValue(100);
                NumberPicker15.setMinValue(0);
                NumberPicker15.setValue(count[14]);
                new AlertDialog.Builder(this)
                        .setView(rink15)
                        .show();
                count[14] =NumberPicker15.getValue();
                NumberPicker15.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[14] = NumberPicker15.getValue();
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //16の確率//
            case R.id.button16:
                View rink16 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker16 = (NumberPicker)rink16.findViewById(R.id.numberPicker);
                NumberPicker16.setMaxValue(100);
                NumberPicker16.setMinValue(0);
                NumberPicker16.setValue(count[15]);
                new AlertDialog.Builder(this)
                        .setView(rink16)
                        .show();
                count[15] =NumberPicker16.getValue();
                NumberPicker16.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[15] = NumberPicker16.getValue();
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //17の確率//
            case R.id.button17:
                View rink17 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker17 = (NumberPicker)rink17.findViewById(R.id.numberPicker);
                NumberPicker17.setMaxValue(100);
                NumberPicker17.setMinValue(0);
                NumberPicker17.setValue(count[16]);
                new AlertDialog.Builder(this)
                        .setView(rink17)
                        .show();
                count[16] =NumberPicker17.getValue();
                NumberPicker17.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[16] = NumberPicker17.getValue();
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //18の確率//
            case R.id.button18:
                View rink18 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker18 = (NumberPicker)rink18.findViewById(R.id.numberPicker);
                NumberPicker18.setMaxValue(100);
                NumberPicker18.setMinValue(0);
                NumberPicker18.setValue(count[17]);
                new AlertDialog.Builder(this)
                        .setView(rink18)
                        .show();
                count[17] =NumberPicker18.getValue();
                NumberPicker18.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[17] = NumberPicker18.getValue();
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //19の確率//
            case R.id.button19:
                View rink19 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker19 = (NumberPicker)rink19.findViewById(R.id.numberPicker);
                NumberPicker19.setMaxValue(100);
                NumberPicker19.setMinValue(0);
                NumberPicker19.setValue(count[18]);
                new AlertDialog.Builder(this)
                        .setView(rink19)
                        .show();
                count[18] =NumberPicker19.getValue();
                NumberPicker19.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[18] = NumberPicker19.getValue();
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //20の確率//
            case R.id.button20:
                View rink20 = this.getLayoutInflater().inflate(R.layout.numpick, null);
                NumberPicker20 = (NumberPicker)rink20.findViewById(R.id.numberPicker);
                NumberPicker20.setMaxValue(100);
                NumberPicker20.setMinValue(0);
                NumberPicker20.setValue(count[19]);
                new AlertDialog.Builder(this)
                        .setView(rink20)
                        .show();
                count[19] =NumberPicker20.getValue();
                NumberPicker20.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        count[19] = NumberPicker20.getValue();
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText(String.valueOf(count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                                +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]));
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                });
                break;
            //リセットボタン
            case R.id.buttonre:
                AlertDialog.Builder dialog = new AlertDialog.Builder(
                        MainActivity.this);
                dialog.setTitle("注意");
                dialog.setMessage("出目の確率をリセットしてもよろしいですか？");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // OK button pressed
                        count[0] =5;
                        count[1] =5;
                        count[2] =5;
                        count[3] =5;
                        count[4] =5;
                        count[5] =5;
                        count[6] =5;
                        count[7] =5;
                        count[8] =5;
                        count[9] =5;
                        count[10] =5;
                        count[11] =5;
                        count[12] =5;
                        count[13] =5;
                        count[14] =5;
                        count[15] =5;
                        count[16] =5;
                        count[17] =5;
                        count[18] =5;
                        count[19] =5;
                        TextView t2 = (TextView) findViewById(R.id.counta);
                        t2.setText("100");
                        t2.setTextSize(50.0f);
                        t2.setGravity(Gravity.CENTER);
                    }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                break;
            //ダイス画面に戻るためのボタン//
            case R.id.button0:
                if(100!=count[0]+count[1]+count[2]+count[3]+count[4]+count[5]+count[6]+count[7]+count[8]+count[9]
                        +count[10]+count[11]+count[12]+count[13]+count[14]+count[15]+count[16]+count[17]+count[18]+count[19]) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("エラー")
                            .setMessage("合計確率を100%にしてください")
                            .setPositiveButton("OK", null)
                            .show();
                    break;
                }
                setContentView(R.layout.activity_main);
        }
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.loadAd(adRequest);
    }

    public  void diceroll(View View) {
        int z;
        Random y = new Random();
        z = y.nextInt(100);
        z = z+1;
        int a=count[0];
        int b=count[1];
        int c=count[2];
        int d=count[3];
        int e=count[4];
        int f=count[5];
        int g=count[6];
        int h=count[7];
        int i=count[8];
        int j=count[9];
        int k=count[10];
        int l=count[11];
        int m=count[12];
        int n=count[13];
        int o=count[14];
        int p=count[15];
        int q=count[16];
        int r=count[17];
        int s=count[18];
        int t=count[19];
        if(100!=a+b+c+d+e+f+g+h+i+j+k+l+m+n+o+p+q+r+s+t){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("エラー")
                    .setMessage("合計確率を100%にしてください")
                    .setPositiveButton("OK", null)
                    .show();
        }
        else if(z<=a){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("1");
        }
        else if (z<=a+b){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("2");
        }
        else if (z<=a+b+c){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("3");
        }
        else if (z<=a+b+c+d){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("4");
        }
        else if (z<=a+b+c+d+e){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("5");
        }
        else if (z<=a+b+c+d+e+f){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("6");
        }
        else if (z<=a+b+c+d+e+f+g){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("7");
        }
        else if (z<=a+b+c+d+e+f+g+h){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("8");
        }
        else if (z<=a+b+c+d+e+f+g+h+i){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("9");
        }
        else if (z<=a+b+c+d+e+f+g+h+i+j){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("10");
        }
        else if (z<=a+b+c+d+e+f+g+h+i+j+k){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("11");
        }
        else if (z<=a+b+c+d+e+f+g+h+i+j+k+l){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("12");
        }
        else if (z<=a+b+c+d+e+f+g+h+i+j+k+l+m){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("13");
        }
        else if (z<=a+b+c+d+e+f+g+h+i+j+k+l+m+n){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("14");
        }
        else if (z<=a+b+c+d+e+f+g+h+i+j+k+l+m+n+o){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("15");
        }
        else if (z<=a+b+c+d+e+f+g+h+i+j+k+l+m+n+o+p){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("16");
        }
        else if (z<=a+b+c+d+e+f+g+h+i+j+k+l+m+n+o+p+q){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("17");
        }
        else if (z<=a+b+c+d+e+f+g+h+i+j+k+l+m+n+o+p+q+r){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("18");
        }
        else if (z<=a+b+c+d+e+f+g+h+i+j+k+l+m+n+o+p+q+r+s){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("19");
        }
        else if (z<=a+b+c+d+e+f+g+h+i+j+k+l+m+n+o+p+q+r+s+t){
            TextView t1 = (TextView) this.findViewById(R.id.start);
            t1.setText("20");
        }
    }
}