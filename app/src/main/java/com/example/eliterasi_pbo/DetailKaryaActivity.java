package com.example.eliterasi_pbo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class DetailKaryaActivity extends AppCompatActivity {

    SharedPreferences keepPuisi;
    FirebaseDatabase database;
    DatabaseReference refdb;
    String idpuisi;
    TextView judultext, authortext, puisitext;
    Boolean clicked = false;

    //Text to Speech var
    private final int REQ_CODE_SPEECH_INPUT = 100;
    TextToSpeech textToSpeech;
    FloatingActionButton menuBtn, speechBtn, vrBtn;

    //Anim var
    Animation rotateOpen, rotateClose, fromBottom, toBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_karya);

        rotateOpen = AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(new Locale("id", "ID"));
                    textToSpeech.setLanguage(new Locale("id", "ID"));

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(DetailKaryaActivity.this, "Bahasa Device Tidak Support", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailKaryaActivity.this, "Device ini Support", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailKaryaActivity.this, "Gagal Melakukan Proses", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }

        keepPuisi = this.getSharedPreferences("Puisi", Context.MODE_PRIVATE);
        idpuisi = keepPuisi.getString("puisi", "missing");

        judultext = findViewById(R.id.jdlpuisi);
        authortext = findViewById(R.id.authorpuisi);
        puisitext = findViewById(R.id.isipuisi);

        //FAB Component
        speechBtn = findViewById(R.id.speechbtn);
        menuBtn = findViewById(R.id.menubtn);
        vrBtn = findViewById(R.id.vrbtn);

        String firstLetStr = idpuisi.substring(0, 1);
        String remLetStr = idpuisi.substring(1);
        firstLetStr = firstLetStr.toUpperCase();
        String judulkarya = firstLetStr + remLetStr;
        judultext.setText(judulkarya);
        database = FirebaseDatabase.getInstance();
        refdb = database.getReference();

        menuBtn.setOnClickListener(v -> {
            onAddButtonClicked();
        });

        vrBtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), VR.class));
        });

        speechBtn.setOnClickListener(v -> {
            refdb.child("Karya_Sastra").child(idpuisi).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataSnapshot dataSnapshot2 = dataSnapshot.child("kontenpuisi");
                    String isipuisi = (String) dataSnapshot2.getValue();
                    textToSpeech.speak(isipuisi, TextToSpeech.QUEUE_FLUSH, null);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(DetailKaryaActivity.this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                }
            });
        });

        speechBtn.setOnLongClickListener(v -> {
            textToSpeech.stop();
            textToSpeech.shutdown();
            return false;
        });

        refdb.child("Karya_Sastra").child(idpuisi).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot.child("judulpuisi");
                DataSnapshot dataSnapshot2 = dataSnapshot.child("kontenpuisi");
                DataSnapshot dataSnapshot3 = dataSnapshot.child("author");

                String isipuisi = (String) dataSnapshot2.getValue();
                String author = (String) dataSnapshot3.getValue();

                authortext.setText("Karya : " + author);
                puisitext.setText(isipuisi);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DetailKaryaActivity.this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void onAddButtonClicked() {
        setVisibel(clicked);
        setAnimation(clicked);
        setClick(clicked);
        clicked = !clicked;
    }

    @SuppressLint("RestrictedApi")
    private void setVisibel(Boolean clicked) {
        if (!clicked){
            vrBtn.setVisibility(View.VISIBLE);
            speechBtn.setVisibility(View.VISIBLE);
        } else {
            vrBtn.setVisibility(View.INVISIBLE);
            speechBtn.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnimation(Boolean clicked){
        if (!clicked){
            speechBtn.startAnimation(fromBottom);
            vrBtn.startAnimation(fromBottom);
            menuBtn.startAnimation(rotateOpen);
        } else {
            speechBtn.startAnimation(toBottom);
            vrBtn.startAnimation(toBottom);
            menuBtn.startAnimation(rotateClose);
        }
    }

    private void setClick(Boolean clicked){
        if (!clicked){
            speechBtn.setClickable(true);
            vrBtn.setClickable(true);
        } else {
            speechBtn.setClickable(false);
            vrBtn.setClickable(false);
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }



}
