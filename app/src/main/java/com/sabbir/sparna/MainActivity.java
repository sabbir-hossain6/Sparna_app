package com.sabbir.sparna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = (Button) findViewById(R.id.loginBTN);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser =firebaseAuth.getCurrentUser();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Login.class));
            }
        });
        loginBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MainActivity.this,AdminLogin.class));
                return false;
            }
        });
    }

    public void addPage(View view){
        startActivity(new Intent(this,AddItem.class));
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logOutMenu:{
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this,MainActivity.class));
            }
            case R.id.logInMenu:{
                startActivity(new Intent(this,Login.class));
            }
            case R.id.goToHome:{
                return true;
            }
            case R.id.changeProfile:{
                finish();
                startActivity(new Intent(this,Profile.class));
                return true;
            }
            case R.id.myOrder:{
                finish();
                startActivity(new Intent(this,Orders.class));
            }
            case R.id.myCart:{
                finish();
                startActivity(new Intent(this,Cart.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToListing(View view) {
        startActivity(new Intent(this,ProductListing.class));
    }

    public void goToOrder(View view) {
        finish();
        startActivity(new Intent(this,Orders.class));
    }

    public void goToCart(View view) {
        finish();
        startActivity(new Intent(this,Cart.class));
    }
}
