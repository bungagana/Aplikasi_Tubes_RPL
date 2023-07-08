package com.example.eliterasi_pbo.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.sch.sman1pangkah.sptos.mazan.Login;
import id.sch.sman1pangkah.sptos.mazan.PrefConfig;
import id.sch.sman1pangkah.sptos.mazan.R;
import id.sch.sman1pangkah.sptos.mazan.ecoinActivity;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    public static PrefConfig prefConfig;
    TextView namauser, poinuser, biouser, levelid, titleid, gradeid;
    SharedPreferences namapref;
    DatabaseReference refdb;
    FirebaseDatabase database;
    int expint, batasint, levelup;
    ProgressBar progressBar;
    ImageView gradebg;
    String biodata, lvlup;
    CardView ecoinBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.ungu));
        }

        Button signout = root.findViewById(R.id.kluarbtn);
        namapref = getActivity().getSharedPreferences("nama", Context.MODE_PRIVATE);

        ecoinBtn = root.findViewById(R.id.ecoinBtn);
        namauser = root.findViewById(R.id.namauser);
        levelid = root.findViewById(R.id.levelid);
        gradeid = root.findViewById(R.id.gradetxt);
        poinuser = root.findViewById(R.id.poinuser);
        biouser = root.findViewById(R.id.biouser);
        gradebg = root.findViewById(R.id.gradebg);
        titleid = root.findViewById(R.id.titleid);
        progressBar = root.findViewById(R.id.progreslevel);
        database = FirebaseDatabase.getInstance();
        refdb = database.getReference();
        final String nama = namapref.getString("namauser","missing");

        namauser.setText(nama);

        refdb.child("Users").child(nama).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot.child("poin");
                DataSnapshot dataSnapshot2 = dataSnapshot.child("level");
                DataSnapshot dataSnapshot3 = dataSnapshot.child("exp");
                DataSnapshot dataSnapshot4 = dataSnapshot.child("title");
                DataSnapshot dataSnapshot5 = dataSnapshot.child("bio");
                String em = (String) dataSnapshot1.getValue();
                String em1 = (String) dataSnapshot2.getValue();
                String em2 = (String) dataSnapshot3.getValue();
                String title = (String) dataSnapshot4.getValue();
                String bioprofil = (String) dataSnapshot5.getValue();
                biouser.setText(bioprofil);
                levelid.setText("Level " + em1);
                poinuser.setText("E-Coin : " + em);
                titleid.setText(title + " Learning");
                expint = Integer.parseInt(em2);

                switch (em1) {
                    case "1":
                        batasint = 500;
                        break;
                    case "2":
                        batasint = 2500;
                        break;
                    case "3":
                        batasint = 5000;
                        break;
                    default:
                        batasint = 10000;
                        break;
                }
                progressBar.setMax(batasint);

               if (expint < 500) {
                   refdb.child("Users").child(nama).child("level").setValue("1");
               } else if (expint > 500) {
                   if (expint<2500) {
                       gradeid.setText("M");
                       gradebg.setImageResource(R.drawable.bggraden2);
                       refdb.child("Users").child(nama).child("level").setValue("2");
                       refdb.child("Users").child(nama).child("title").setValue("Master");
                   } else if (expint>2500) {
                       gradeid.setText("S");
                       gradebg.setImageResource(R.drawable.bggraden3);
                       refdb.child("Users").child(nama).child("level").setValue("3");
                       refdb.child("Users").child(nama).child("title").setValue("Super");
                   } else {
                       gradebg.setImageResource(R.drawable.bggraden2);
                       refdb.child("Users").child(nama).child("level").setValue("Peralihan");
                       refdb.child("Users").child(nama).child("title").setValue("Rising Super");
                   }
               } else {
                   refdb.child("Users").child(nama).child("level").setValue("Peralihan");
                   refdb.child("Users").child(nama).child("title").setValue("Going to be Master");
               }


                progressBar.setProgress(expint);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        biouser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ecoinBtn.setOnClickListener(view -> {
            startActivity(new Intent(getActivity().getApplicationContext(), ecoinActivity.class));
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor namaedit = namapref.edit();
                namaedit.putString("namauser", "Guest");
                namaedit.putBoolean("loginstatus", false);
                namaedit.apply();
                startActivity(new Intent(getActivity().getApplicationContext(), Login.class));
            }
        });

        return root;
    }
}