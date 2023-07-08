package id.sch.sman1pangkah.sptos.mazan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Disability extends AppCompatActivity {

    //data status
    SharedPreferences sharedPreferences;
    boolean getStatus;

    //speech recog var
    FrameLayout frameLayout;
    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;
    private String keeper = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disability);
        sharedPreferences = this.getSharedPreferences("nama", Context.MODE_PRIVATE);
        getStatus = sharedPreferences.getBoolean("disablystatus", false);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
        checkVoiceCommandPermission();
        Context context;
        frameLayout = findViewById(R.id.disFrame);
        speechRecognizer = speechRecognizer.createSpeechRecognizer(Disability.this);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matchesFound = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                if (matchesFound != null){
                    // Hasil Speech Recognition
                    keeper = matchesFound.get(0);
                    // Pemrosesan hasil menjadi command
                    switch (keeper) {
                        case "buka jelajahi":
                            startActivity(new Intent(getApplicationContext(), DiscoverActivity.class));
                            Toast.makeText(Disability.this, "Membuka Jelajahi", Toast.LENGTH_SHORT).show();
                            break;
                        case "buka aktivitas":
                            startActivity(new Intent(getApplicationContext(), Creavy.class));
                            Toast.makeText(Disability.this, "Membuka Aktivitas", Toast.LENGTH_SHORT).show();
                            break;
                        case "keluar":
                            Toast.makeText(Disability.this, "Keluar", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor  delExit = sharedPreferences.edit();
                            delExit.putBoolean("disablystatus", false);
                            delExit.apply();
                            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                            break;
                    }

                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(Disability.this, "Mendengarkan", Toast.LENGTH_SHORT).show();
                        speechRecognizer.startListening(speechRecognizerIntent);
                        keeper = "";
                        break;
                    case MotionEvent.ACTION_UP:
                        Toast.makeText(Disability.this, "Berhenti", Toast.LENGTH_SHORT).show();
                        speechRecognizer.stopListening();
                        break;
                }
                return false;
            }
        });
    }

    public void keluarBtn(View view) {
        SharedPreferences.Editor  delExit = sharedPreferences.edit();
        delExit.putBoolean("disablystatus", false);
        delExit.apply();
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
    }

    public void discovBtn(View view) {
        startActivity(new Intent(getApplicationContext(), DiscoverActivity.class));
    }

    public void creavyBtn(View view) {
        startActivity(new Intent(getApplicationContext(), Creavy.class));
    }


    // Check Voice Permission
    private void checkVoiceCommandPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(Disability.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }
    }
}