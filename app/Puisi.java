package id.sch.sman1pangkah.sptos.mazan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Puisi extends AppCompatActivity {

    TextInputEditText judulpuisi, kontenpuisi;
    DatabaseReference puisiuser, refdb;
    FirebaseDatabase database;
    SharedPreferences namapref;
    String status, poininput, expinput, nama, score, imgurl, lovemeter, jenis;
    int varfixed, expfixed;

    View dialogView;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puisi);

        varfixed = 1;
        expfixed = 5;
        score = "0";
        imgurl = "http://";
        lovemeter = "0";
        jenis = "Puisi";
        namapref = getSharedPreferences("nama", Context.MODE_PRIVATE);
        nama = namapref.getString("namauser","missing");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
        judulpuisi = findViewById(R.id.judulpuisi);
        kontenpuisi = findViewById(R.id.kontenpuisi);
        status = "dalam proses";
        database = FirebaseDatabase.getInstance();
        puisiuser = database.getReference().child("Karya_Sastra");
        refdb = database.getReference();

        insertPoin();
    }

    private void insertPoin() {
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

    public void kirimbtn(View view) {
        proseskirim();
    }

    private void proseskirim() {
        if (judulpuisi.getText().toString().length()>50) {

            judulpuisi.setError("Judul puisi tidak boleh melebihi 50 huruf");
            finish();
        } if (kontenpuisi.getText().toString().length()>1000) {
            kontenpuisi.setError("Konten puisi tidak boleh melebihi 1000 huruf");
        } else {
            try {
                refdb.child("Users").child(nama).child("poin").setValue(poininput);
                refdb.child("Users").child(nama).child("exp").setValue(expinput);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final Poem poem = new Poem(judulpuisi.getText().toString(), kontenpuisi.getText().toString(), status, score, imgurl, lovemeter, nama, jenis );
            puisiuser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(poem.getJudulpuisi()).exists())
                        Toast.makeText(Puisi.this,"Judul sudah pernah ada!", Toast.LENGTH_SHORT).show();
                    else {
                        puisiuser.child(poem.getJudulpuisi()).setValue(poem);
                        Toast.makeText(Puisi.this,"Proses pengiriman sukses, silakan tunggu konfirmasi dari kami", Toast.LENGTH_SHORT).show();
                        assert getFragmentManager() != null;
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.grey));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            judulpuisi.setText("");
            kontenpuisi.setText("");
        }
    }

    public void kompetisibtn(View view) {

        Toast.makeText(Puisi.this,"Belum ada kompetisi yang berlangsung", Toast.LENGTH_SHORT).show();

    }

    public void envybtn(View view) {
        dialog = new AlertDialog.Builder(Puisi.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.envy, null);
        dialog.setView(dialogView);
        dialog.show();

    }
}

