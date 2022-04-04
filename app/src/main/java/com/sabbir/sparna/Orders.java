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
import android.widget.TextView;
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

public class Orders extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatabaseReference orderReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    RecyclerView recyclerView;
    ArrayList<product> products;
    OrderAdapter myAdapter;
    String text;
    String [] orderItems;
    ArrayList<String> basket;
    public static TextView textView;
    TextView orderStatus;
    DatabaseReference orderStatusReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        orderStatusReference = FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).child("orderStatus");

        orderStatusReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderStatus.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        basket = new ArrayList<String>();
        orderStatus = (TextView) findViewById(R.id.orderStatus);
        textView = findViewById(R.id.textView);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null)
            orderReference = FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).child("order");
        else
            Toast.makeText(getApplicationContext(),"Please log in",Toast.LENGTH_SHORT).show();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Items");

        products = new ArrayList<product>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        orderReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    text = dataSnapshot.getValue().toString();
                    if (text!=null) {
                        orderItems = text.split(" ");
                        basket.clear();
                        for (int i = 0; i < orderItems.length; i++) {
                            basket.add(orderItems[i]);
                        }
                        callItems();
                    }
                }

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
                myAdapter = new OrderAdapter(getApplicationContext(),products);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });

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

