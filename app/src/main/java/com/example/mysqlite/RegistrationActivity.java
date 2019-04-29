package com.example.mysqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.mysqlite.dao.MySqliteOpenHelper;
import com.example.mysqlite.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {
    @BindView(R.id.nameEdit)
    TextInputEditText nameEdit;
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
        setContentView(R.layout.activity_registration);

        ButterKnife.bind(this);
        preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
        editor = preferences.edit();
        mySqliteOpenHelper = new MySqliteOpenHelper(this);
    }
    @OnClick(R.id.button)
    public void onButtonClick(View view){
        String name,email,pass;
        name = nameEdit.getText().toString().trim();
        email = emailEdit.getText().toString().trim();
        pass = passEdit.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            nameEdit.setError("Please enter your name.");
            return;
        }
        if(TextUtils.isEmpty(email)){
            emailEdit.setError("Please enter your email.");
            return;
        }
        if(TextUtils.isEmpty(pass)){
            passEdit.setError("Please enter your password.");
            return;
        }
        editor.putString("name",name);
        editor.putString("email",email);
        editor.putString("pass",pass);
        editor.putBoolean("auth",true);
        editor.apply();
        User user = new User(name,email,pass);
        mySqliteOpenHelper.addUser(user);
        Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
