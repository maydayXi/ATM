package com.home.atm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText edtUserName;
    private EditText edtPwd;
    private CheckBox cbRemUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();

        cbRemUsername.setChecked(getSharedPreferences("ATM", MODE_PRIVATE)
                .getBoolean("REMEMBER_USERNAME", false));
        cbRemUsername.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton,
                                         boolean b) {
                getSharedPreferences("ATM", MODE_PRIVATE)
                        .edit()
                        .putBoolean("REMEMBER_USERNAME", b)
                        .apply();
            }
        });

        String userName = cbRemUsername.isChecked()?
                getSharedPreferences("ATM", MODE_PRIVATE)
                .getString("USERNAME", "")
                :"";

        edtUserName.setText(userName);
    }

    private void findViews() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPwd = findViewById(R.id.edtPwd);
        cbRemUsername = findViewById(R.id.cbRemUsername);
    }

    public void login(View view) {
        final String userName = edtUserName.getText().toString();
        final String pwd = edtPwd.getText().toString();

        FirebaseDatabase.getInstance().getReference("users")
                .child(userName).child("password")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    // 非同步讀取資料庫方法

                    // <summary> 資料來時自動執行 </summary>
                    // <param name="datasnapshot"> 資料庫物件 </param>
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // getValue() 可取得資料庫的值
                        String password = (String) dataSnapshot.getValue();

                        if(Objects.requireNonNull(password).equals(pwd)) {

                            boolean rememberUsername =
                                    getSharedPreferences("ATM", MODE_PRIVATE)
                                    .getBoolean("REMEMBER_USERNAME", false);
                            Log.d(TAG, "onDataChange: REMEBER_USERNAME IS " + rememberUsername);

                            if (rememberUsername) {
                                // save username if login correct
                                getSharedPreferences("ATM",
                                        MODE_PRIVATE)
                                        .edit()
                                        .putString("USERNAME",
                                                userName)
                                        .apply();
                            }

                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Login Failed",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void quit(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
