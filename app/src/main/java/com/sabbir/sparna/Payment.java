package com.sabbir.sparna;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Payment extends AppCompatActivity {
    DatabaseReference cartReference;
    DatabaseReference orderReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    String text;
    String orderText;
    String [] cartItems;
    ArrayList<String> basket;

    TextView textView;
    EditText editText;
    Button bkashBtn;

    @Nullable
    @Override
    public ComponentName getCallingActivity() {
        return super.getCallingActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        basket = new ArrayList<String>();
        textView = (TextView) findViewById(R.id.bkashmsg);
        editText = (EditText) findViewById(R.id.inputTrans);
        bkashBtn = (Button) findViewById(R.id.bkashPlace);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null)
            orderReference = FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).child("order");
        else
            Toast.makeText(getApplicationContext(),"Please log in",Toast.LENGTH_SHORT).show();

        cartReference = FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).child("cart");
        cartReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    text = dataSnapshot.getValue().toString();
                    if (text!=null) {
                        cartItems = text.split(" ");
                        if (!basket.isEmpty())
                            basket.clear();

                        for (int i = 0; i < cartItems.length; i++) {
                            basket.add(cartItems[i]);
                        }

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

    public void selectCash(View view) {
        cartReference.setValue("");
        if (text.trim()!=null) {
            orderReference.setValue(orderText+" "+text);
        }
        finish();
        startActivity(new Intent(this,Orders.class));
    }

    public void selectBkash(View view) {
        textView.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        bkashBtn.setVisibility(View.VISIBLE);
    }

    public void placeOrderBkash(View view) {
        cartReference.setValue("");
        if (text.trim()!=null) {
            orderReference.setValue(orderText+" "+text);
        }
        DatabaseReference transactionReference = FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).child("transactions");
        transactionReference.setValue(editText.getText().toString());
        finish();
        startActivity(new Intent(this,Orders.class));
    }
}
