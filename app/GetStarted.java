package id.sch.sman1pangkah.sptos.mazan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GetStarted extends AppCompatActivity {

    SharedPreferences namapref;
    TextView judul, judul2, deskripsi;
    ImageView gambar;
    CardView cardbg;
    CoordinatorLayout layoutStart;
    Button btnnext;
    View viewgm;
    boolean page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.abu2));
        }

        judul = findViewById(R.id.judul);
        judul2 = findViewById(R.id.judul2);
        deskripsi = findViewById(R.id.deskripsi);
        gambar= findViewById(R.id.gambar);
        cardbg = findViewById(R.id.cardbg);
        btnnext = findViewById(R.id.btnnext);
        viewgm = findViewById(R.id.viewgm);
        layoutStart = findViewById(R.id.layoutStart);

        namapref = this.getSharedPreferences("nama", Context.MODE_PRIVATE);
        page = false;
        boolean loginstatus = namapref.getBoolean("loginstatus", false);
        boolean disablystatus = namapref.getBoolean("disablystatus", false);

        if (loginstatus == true)
        {
            if (disablystatus == true){
                startActivity(new Intent(getApplicationContext(), Disability.class));
            } else {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            }
        }
        else
        {
            Toast.makeText(GetStarted.this,"Selamat Datang di Eliterasi", Toast.LENGTH_SHORT).show();
        }
    }

    public void nextreg(View view) {
        if (!page) {
            judul.setText("Mulailah Untuk Berkarya");
            judul2.setText("Kreatif");
            layoutStart.setBackgroundColor(Color.WHITE);
            deskripsi.setTextColor(Color.WHITE);
            viewgm.setBackgroundColor(Color.WHITE);
            judul2.setTextColor(Color.parseColor("#ffffff"));
            cardbg.setCardBackgroundColor(Color.parseColor("#559E74"));
            btnnext.setBackgroundResource(R.drawable.bground2);
            btnnext.setTextColor(Color.parseColor("#444444"));
            Glide.with(this).load(R.drawable.creatimg).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(gambar);
            page = true;
        } else {
            startActivity(new Intent(getApplicationContext(), Signup.class));
        }

    }
}
