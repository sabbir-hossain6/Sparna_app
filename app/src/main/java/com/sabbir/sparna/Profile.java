package com.sabbir.sparna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

public class Profile extends AppCompatActivity {
    public String uid;
    public String username="";
    public String email="";
    public String phone="";
    public String phone2="";
    public String address="";
    public String area="";
    public String city="";

    EditText etUsername,etPhone,etPhone2,etAddress,etArea,etCity;
    TextView tvEmail;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    DatabaseReference profileReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etUsername = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPhone2 = (EditText) findViewById(R.id.etPhone2);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etArea = (EditText) findViewById(R.id.etArea);
        etCity = (EditText) findViewById(R.id.etCity);
        tvEmail = (TextView) findViewById(R.id.tvEmail);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        uid = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Profiles");
        profileReference = databaseReference.child(uid);

        profileReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("name"))
                    username = dataSnapshot.child("name").getValue().toString();
                if (dataSnapshot.hasChild("phone"))
                    phone = dataSnapshot.child("phone").getValue().toString();
                if (dataSnapshot.hasChild("phone2"))
                    phone2 = dataSnapshot.child("phone2").getValue().toString();
                if (dataSnapshot.hasChild("address"))
                    address = dataSnapshot.child("address").getValue().toString();
                if (dataSnapshot.hasChild("area"))
                    area = dataSnapshot.child("area").getValue().toString();
                if (dataSnapshot.hasChild("city"))
                    city = dataSnapshot.child("city").getValue().toString();

                etUsername.setText(username, TextView.BufferType.EDITABLE);
                etAddress.setText(address, TextView.BufferType.EDITABLE);
                etArea.setText(area, TextView.BufferType.EDITABLE);
                etCity.setText(city, TextView.BufferType.EDITABLE);
                etPhone.setText(phone, TextView.BufferType.EDITABLE);
                etPhone2.setText(phone2, TextView.BufferType.EDITABLE);
                Toast.makeText(getApplicationContext(),"Data Retrived",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        email = firebaseUser.getEmail();
        tvEmail.setText(email);


    }

    public void saveToFirebase(View view) {
        profileReference.child("name").setValue(etUsername.getText().toString());
        profileReference.child("address").setValue(etAddress.getText().toString());
        profileReference.child("city").setValue(etCity.getText().toString());
        profileReference.child("area").setValue(etArea.getText().toString());
        profileReference.child("phone").setValue(etPhone.getText().toString());
        profileReference.child("phone2").setValue(etPhone2.getText().toString());
        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
    }
}
