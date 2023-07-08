package id.sch.sman1pangkah.sptos.mazan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Artikel extends AppCompatActivity {

    TextInputEditText judulartikel, kontenartikel;
    DatabaseReference artikeluser, refdb;
    FirebaseDatabase database;
    SharedPreferences namapref;
    String status, name, poininput, expinput, nama;
    int varfixed, expfixed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel);
        varfixed = 1;
        expfixed = 5;
        namapref = getSharedPreferences("nama", Context.MODE_PRIVATE);
        nama = namapref.getString("namauser","missing");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }


        judulartikel = findViewById(R.id.judulartikel);
        kontenartikel = findViewById(R.id.kontenartikel);
        status = "dalam proses";
        database = FirebaseDatabase.getInstance();
        artikeluser = database.getReference().child("Karya_Sastra").child("Artikel_" + nama);
        refdb = database.getReference();

        Insertpoin();

    }

    public void kirimbtn(View view) {
        proseskirim();
    }

    private void proseskirim() {


        if (judulartikel.getText().toString().length()>50) {

            judulartikel.setError("Judul artikel tidak boleh melebihi 50 huruf");
            finish();
        } if (kontenartikel.getText().toString().length()>5000) {
            kontenartikel.setError("Konten artikel tidak boleh melebihi 5000 huruf");
        } else {

            try {
                refdb.child("Users").child(nama).child("poin").setValue(poininput);
                refdb.child("Users").child(nama).child("exp").setValue(expinput);
            } catch (Exception e) {
                e.printStackTrace();
            }

            final Article article = new Article(judulartikel.getText().toString(), kontenartikel.getText().toString(), status );
            artikeluser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(article.getJudulartikel()).exists())
                        Toast.makeText(Artikel.this,"Judul sudah pernah ada!", Toast.LENGTH_SHORT).show();
                    else {
                        artikeluser.child(article.getJudulartikel()).setValue(article);
                        Toast.makeText(Artikel.this,"Proses pengiriman sukses, silakan tunggu konfirmasi dari kami" , Toast.LENGTH_SHORT).show();
                        assert getFragmentManager() != null;
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            judulartikel.setText("");
            kontenartikel.setText("");
        }

    }

    private void Insertpoin() {
        refdb.child("Users").child(nama).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot.child("poin");
                DataSnapshot dataSnapshot2 = dataSnapshot.child("exp");
                String em = (String) dataSnapshot1.getValue();
                String em2 = (String) dataSnapshot2.getValue();

                assert em != null;
                int emint = Integer.parseInt(em);
                int poinsum = varfixed+emint;
                poininput = Integer.toString(poinsum);

                assert em2 != null;
                int expint = Integer.parseInt(em2);
                int expsum = expfixed+expint;
                expinput = Integer.toString(expsum);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

