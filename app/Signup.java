package id.sch.sman1pangkah.sptos.mazan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity {

    private EditText email,UserName,UserPassword;
    DatabaseReference users;
    FirebaseDatabase database;
    String poin, jumlahkarya, agree, denied, title, exp, levelid, bio;
    ViewPager viewPager;
    SharedPreferences sharedPreferences;

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
        Toast.makeText(Signup.this, "Please press Back again to exit", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_signup);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.darkreg));
        }
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        poin = "0";
        title = "Novice";
        exp = "0";
        jumlahkarya = "0";
        denied = "0";
        agree ="0";
        levelid = "1";
        bio = "Hello World! Ayo ciptakan dunia ini menjadi lebih baik ";
        email = findViewById(R.id.txtEmail);
        UserName = findViewById(R.id.txtName);
        UserPassword = findViewById(R.id.txtPass);
    }
    public void signproses(View view) {
        performRegistration();
    }
    public void loginbtn(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public void performRegistration() {
        if (!isEmail(email)) {

            email.setError("Masukan Email yang Benar!");

        } if (UserName.getText().toString().length()<4) {

            UserName.setError("Username diperlukan lebih dari 4 huruf");

        } if (UserPassword.getText().toString().length()<8) {

            UserPassword.setError("Password diperlukan lebih dari 8 huruf/angka");

        } else {
            final User user = new User(UserName.getText().toString(), UserPassword.getText().toString(),email.getText().toString(), poin, jumlahkarya, agree, denied, title, exp, levelid, bio );
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(user.getUserName()).exists())
                        Toast.makeText(Signup.this,"Nama pengguna sudah ada", Toast.LENGTH_SHORT).show();
                    else {
                        users.child(user.getUserName()).setValue(user);
                        Toast.makeText(Signup.this,"Sign Up Sukses, Silakan Log in", Toast.LENGTH_SHORT).show();
                        assert getFragmentManager() != null;
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            email.setText("");
            UserPassword.setText("");
            UserName.setText("");
        }
    }

    private class ViewPagerStack implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View page, float position) {
            if (position >= 0) {
                page.setScaleX(0.7f - 0.05f * position);
                page.setScaleY(0.7f);
                page.setTranslationX(-page.getWidth() * position);
                page.setTranslationY(30 * position);
            }
        }
    }

}
