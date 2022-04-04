package com.sabbir.sparna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductListing extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<product> products;
    MyAdapter myAdapter;
    FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Items");

        products = new ArrayList<product>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot position:dataSnapshot.getChildren()){
                    product e = position.getValue(product.class);
                    /*if (e.onOffer!=null)
                    if (e.onOffer.compareTo("No")==0){
                        e.setOfferPrice("");
                        e.setOnOffer("");
                    }*/
                    products.add(e);
                }
                myAdapter = new MyAdapter(getApplicationContext(),products);
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
