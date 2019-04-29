package com.example.mysqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.mysqlite.dao.MySqliteOpenHelper;
import com.example.mysqlite.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.emailEdit)
    TextInputEditText emailEdit;
    @BindView(R.id.passEdit)
    TextInputEditText passEdit;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    MySqliteOpenHelper mySqliteOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
        editor = preferences.edit();
        mySqliteOpenHelper = new MySqliteOpenHelper(this);
    }
    @OnClick(R.id.button)
    public void onButtonClick(View view){
        String name,email,pass;
        email = emailEdit.getText().toString().trim();
        pass = passEdit.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            emailEdit.setError("Please enter your email.");
            return;
        }
        if(TextUtils.isEmpty(pass)){
            passEdit.setError("Please enter your password.");
            return;
        }


        List<User> userList = mySqliteOpenHelper.login(email,pass);
        if(userList.size()>0) {
            editor.putString("name", userList.get(0).getName());
            editor.putString("email", email);
            editor.putString("pass", pass);
            editor.putBoolean("auth", true);
            editor.apply();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.reg)
    public void startRegActivity(View view){
        startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
    }
}
