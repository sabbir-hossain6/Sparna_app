package com.sabbir.sparna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AdminLogin extends AppCompatActivity {

    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        password = (EditText) findViewById(R.id.adminPass);
    }

    public void goToAdd(View view) {
        if (password.getText().toString().compareTo("admin")==0){
            startActivity(new Intent(this,AddItem.class));
        }
    }
}
