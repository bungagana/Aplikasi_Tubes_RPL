package com.example.eliterasi_pbo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    public static PrefConfig prefConfig;
    private EditText UserEmail,UserPassword;
    SharedPreferences namapref;
    DatabaseReference users;
    FirebaseDatabase database;

    final String TAG = this.getClass().getName();
    boolean twice = false;
    @Override
    public void onBackPressed() {

        Log.d(TAG,"click");

        if (twice == true) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        twice = true;
        Log.d(TAG,"twice: "+twice);

        //      super.onBackPressed();
        Toast.makeText(Login.this, "Please press Back again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
                Log.d(TAG,"twice: "+twice);
            }
        }, 3000);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.darklime));
        }
         namapref = this.getSharedPreferences("nama", Context.MODE_PRIVATE);
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        prefConfig = new PrefConfig(this);
        boolean loginstatus = namapref.getBoolean("loginstatus", false);
        UserEmail = findViewById(R.id.txtEmail);
        UserPassword = findViewById(R.id.txtPass);

        if (loginstatus)
        {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        }
        else
        {

        }

    }


    public void signbtn(View view) {
        startActivity(new Intent(getApplicationContext(), Signup.class));
    }

    public void loginproses(View view) {
        Login(UserEmail.getText().toString(), UserPassword.getText().toString());
    }


    boolean isUserName(EditText text) {
        CharSequence UserName = text.getText().toString();
        return (!TextUtils.isEmpty(UserName) && Patterns.EMAIL_ADDRESS.matcher(UserName).matches());
    }

    private void Login(final String user, final String pwd) {
        if (isUserName(UserEmail) == true) {

            UserEmail.setError("Mohon jangan masukan email, masukan username yang benar!");

            Toast.makeText(Login.this, "Masukan kembali username yang benar!", Toast.LENGTH_SHORT).show();

            return;


        }
        if (UserPassword.getText().toString().length() < 0) {

            UserPassword.setError("Masukan password");

        } else {

            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(user).exists())
                    {
                        if(!user.isEmpty())
                        {
                            User login = dataSnapshot.child(user).getValue(User.class);
                            assert login != null;
                            if(login.getPassword().equals(pwd)) {
                                String name = UserEmail.getText().toString();
                                SharedPreferences.Editor namaedit = namapref.edit();
                                namaedit.putString("namauser",name);
                                namaedit.putBoolean("loginstatus", true);
                                namaedit.apply();
                                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                            }
                            else {
                                Toast.makeText(Login.this, "Login gagal mohon dicoba lagi", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
}
}
