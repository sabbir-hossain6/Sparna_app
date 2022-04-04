package com.sabbir.sparna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatabaseReference cartReference;
    DatabaseReference orderReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    RecyclerView recyclerView;
    ArrayList<product> products;
    CartAdapter myAdapter;
    String text;
    String orderText;
    String [] cartItems;
    ArrayList<String> basket;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        basket = new ArrayList<String>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null)
            orderReference = FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).child("order");
        else
            Toast.makeText(getApplicationContext(),"Please log in",Toast.LENGTH_SHORT).show();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Items");

        products = new ArrayList<product>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartReference = FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).child("cart");
        cartReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    text = dataSnapshot.getValue().toString();
                    if (text!=null) {
                        cartItems = text.split(" ");
                        basket.clear();
                        for (int i = 0; i < cartItems.length; i++) {
                            basket.add(cartItems[i]);
                        }
                        callItems();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        orderReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    orderText = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void callItems() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot position:dataSnapshot.getChildren()){
                        if (basket.contains(position.getKey())){
                            product e = position.getValue(product.class);
                            products.add(e);
                        }
                }
                myAdapter = new CartAdapter(getApplicationContext(),products);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void placeOrder(View view) {
        /*cartReference.setValue("");
        if (text.trim()!=null) {
            orderReference.setValue(orderText+" "+text);
        }*/
        //finish();
        startActivity(new Intent(this,Payment.class));
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
                finish();
                startActivity(new Intent(this,MainActivity.class));
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
}

