package com.moyanask.simonsmarketplace;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.moyanask.simonsmarketplace.backend.BackgroundWorker;
import com.moyanask.simonsmarketplace.backend.BackgroundWorker2;

public class MainActivity extends AppCompatActivity {
    EditText UsernameEt, PasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Login Page");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsernameEt = (EditText)findViewById(R.id.editTextTextPersonName);
        PasswordEt = (EditText)findViewById(R.id.editTextTextPassword);
    }

    public void OnSignup(View view){
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "signup";
        BackgroundWorker2 backgroundWorker2 = new BackgroundWorker2(this);
        backgroundWorker2.execute(type, username, password);
    }

    public void OnLogin(View view){
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username, password);
    }

}
