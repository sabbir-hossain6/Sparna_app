package com.sabbir.sparna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewProduct extends AppCompatActivity {

    public static String productID;
    String tempCart;

    String type;
    String name;
    String itemId;
    String price;
    String onOffer;
    String offerPrice;
    String description;
    String size;
    String quantity;
    String image;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    DatabaseReference cartReference;

    TextView tvName, tvType, tvID, tvPrice, tvOnOffer, tvOfferPrice, tvDescription, tvSize, tvStock;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        tvName = (TextView) findViewById(R.id.tvName);
        tvType = (TextView) findViewById(R.id.tvType);
        tvID = (TextView) findViewById(R.id.tvId);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvOnOffer = (TextView) findViewById(R.id.tvOnOffer);
        tvOfferPrice = (TextView) findViewById(R.id.tvOfferPrice);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvSize = (TextView) findViewById(R.id.tvSize);
        tvStock = (TextView) findViewById(R.id.tvStock);

        imageView = (ImageView) findViewById(R.id.imageView2);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Items").child(productID);
        cartReference = FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).child("cart");

        cartReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    tempCart = dataSnapshot.getValue().toString();
                else
                    tempCart="";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                type = dataSnapshot.child("type").getValue().toString();
                name = dataSnapshot.child("name").getValue().toString();
                itemId = dataSnapshot.child("itemId").getValue().toString();
                price = dataSnapshot.child("price").getValue().toString();
                onOffer = dataSnapshot.child("onOffer").getValue().toString();
                offerPrice = dataSnapshot.child("offerPrice").getValue().toString();
                description = dataSnapshot.child("description").getValue().toString();
                size = dataSnapshot.child("size").getValue().toString();
                quantity = dataSnapshot.child("quantity").getValue().toString();
                image = dataSnapshot.child("image").getValue().toString();

                tvName.setText(name);
                tvType.setText("Type: "+type);
                tvID.setText("ID: "+itemId);
                tvPrice.setText("Price: "+price+"BDT");
                tvDescription.setText("Description: "+description);
                tvStock.setText(quantity);
                tvSize.setText(size);
                if (onOffer.compareTo("Yes")==0) {
                    tvOnOffer.setText(onOffer);
                    tvOfferPrice.setText(offerPrice);
                }
                else{
                    tvOfferPrice.setVisibility(View.GONE);
                    tvOnOffer.setVisibility(View.GONE);
                }
                loadImageFromUrl(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    private void loadImageFromUrl(String image) {
        Picasso.get().load(image).into(imageView);
    }

    public void addToCart(View view) {
        cartReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                tempCart = dataSnapshot.getValue().toString();
                else tempCart="";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (tempCart!=null) {
            tempCart = tempCart.trim();
            if (tempCart.contains(productID)){
                Toast.makeText(getApplicationContext(),"Already added in cart",Toast.LENGTH_SHORT).show();
            }
            else{
                tempCart+= " "+productID;
                cartReference.setValue(tempCart);
                Toast.makeText(getApplicationContext(),"Added to cart",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            tempCart = productID;
            cartReference.setValue(tempCart);
            Toast.makeText(getApplicationContext(),"Added to cart",Toast.LENGTH_SHORT).show();
        }

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
