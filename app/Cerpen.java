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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cerpen extends AppCompatActivity {

    TextInputEditText judulcerpen;
    EditText kontencerpen;
    DatabaseReference cerpenuser, refdb;
    FirebaseDatabase database;
    int jdlcerpen, isicrpn, varfixed, expfixed;
    SharedPreferences namapref;
    String status, poininput, expinput, nama;
    TextInputLayout judullayout;
    Button nextbtn, kirimbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerpen);
        varfixed = 1;
        expfixed = 5;
        namapref = getSharedPreferences("nama", Context.MODE_PRIVATE);
        nama = namapref.getString("namauser","missing");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }



        judulcerpen = findViewById(R.id.judulcerpen);
        kontencerpen = findViewById(R.id.kontencerpen);
        status = "dalam proses";
        database = FirebaseDatabase.getInstance();
        cerpenuser = database.getReference().child("Karya_Sastra").child("Cerpen_" + nama);
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
        jdlcerpen = judulcerpen.getText().toString().length();
        isicrpn = kontencerpen.getText().toString().length();
        proseskirim();

    }

    private void proseskirim() {
        if (jdlcerpen>5) {
            if (jdlcerpen<50) {
                if(isicrpn<10000) {
                    if (isicrpn>100) {

                        try {
                            refdb.child("Users").child(nama).child("poin").setValue(poininput);
                            refdb.child("Users").child(nama).child("exp").setValue(expinput);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        final Story story = new Story(judulcerpen.getText().toString(), kontencerpen.getText().toString(), status );
                        cerpenuser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child(story.getJudulcerpen()).exists())
                                    Toast.makeText(Cerpen.this,"Judul sudah pernah ada!", Toast.LENGTH_SHORT).show();
                                else {
                                    cerpenuser.child(story.getJudulcerpen()).setValue(story);
                                    Toast.makeText(Cerpen.this,"Proses pengiriman sukses, silakan tunggu konfirmasi dari kami", Toast.LENGTH_SHORT).show();
                                    assert getFragmentManager() != null;
                                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        judulcerpen.setText("");
                        kontencerpen.setText("");
                    } else {
                        kontencerpen.setError("Isi cerpen harus melebihi 100 huruf");
                    }
                } else {
                   // kontencerpen.setError("Isi cerpen tidak boleh melebihi 10.000 huruf");
                }

            } else {
                judulcerpen.setError("Judul cerpen tidak boleh melebihi 50 huruf");
            }

        }  else {
            judulcerpen.setError("Judul cerpen harus melebihi 10 huruf");
        }
    }


}


