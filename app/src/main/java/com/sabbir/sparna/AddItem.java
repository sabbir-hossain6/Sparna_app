package com.sabbir.sparna;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class AddItem extends AppCompatActivity {
    public String type;
    public String name;
    public String itemId;
    public String price;
    public String onOffer;
    public String offerPrice;
    public String description;
    public String size;
    public int quantity;

    EditText etName,etType,etId,etPrice,etOnOffer,etOfferPrice,etDescription,etQuantity,etSize;

    public static final int ImageBack = 1;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference itemsReference;
    DatabaseReference item;

    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        etName = (EditText) findViewById(R.id.etName);
        etType = (EditText) findViewById(R.id.etType);
        etId = (EditText) findViewById(R.id.etID);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etOnOffer = (EditText) findViewById(R.id.etOnOffer);
        etOfferPrice = (EditText) findViewById(R.id.etOnOfferPrice);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
        etSize = (EditText) findViewById(R.id.etSizes);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        itemsReference = databaseReference.child("Items");
        mStorageRef = FirebaseStorage.getInstance().getReference().child("productImages");
    }

    public void addNewItem(View view) {
        type = etType.getText().toString();
        name = etName.getText().toString();
        itemId = etId.getText().toString();
        price = etPrice.getText().toString();
        onOffer = etOnOffer.getText().toString();
        offerPrice = etOfferPrice.getText().toString();
        description = etDescription.getText().toString();
        size = etSize.getText().toString();;
        quantity = Integer.parseInt(etQuantity.getText().toString());


        if(name!=null)
            itemsReference.child(itemId).child("name").setValue(name);
        if(itemId!=null)
            itemsReference.child(itemId).child("itemId").setValue(itemId);
        if(type!=null)
            itemsReference.child(itemId).child("type").setValue(type);
        if(price!=null)
            itemsReference.child(itemId).child("price").setValue(price);
        if(onOffer!=null)
            itemsReference.child(itemId).child("onOffer").setValue(onOffer);
        if(offerPrice!=null)
            itemsReference.child(itemId).child("offerPrice").setValue(offerPrice);
        if(description!=null)
            itemsReference.child(itemId).child("description").setValue(description);
        if(size!=null)
            itemsReference.child(itemId).child("size").setValue(size);
        if(quantity+""!=null)
            itemsReference.child(itemId).child("quantity").setValue(quantity);

        Toast.makeText(this,"Item "+itemId+" added",Toast.LENGTH_SHORT).show();

    }

    public void editItem(View view){
        itemId = etId.getText().toString();
        item = itemsReference.child(itemId);

        item.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(itemId!=null){
                    item = itemsReference.child(itemId);

                    etName.setText(dataSnapshot.child("name").getValue().toString(), TextView.BufferType.EDITABLE);
                    etType.setText(dataSnapshot.child("type").getValue().toString(), TextView.BufferType.EDITABLE);
                    etPrice.setText(dataSnapshot.child("price").getValue().toString(), TextView.BufferType.EDITABLE);
                    etOnOffer.setText(dataSnapshot.child("onOffer").getValue().toString(), TextView.BufferType.EDITABLE);
                    etOfferPrice.setText(dataSnapshot.child("offerPrice").getValue().toString(), TextView.BufferType.EDITABLE);
                    etDescription.setText(dataSnapshot.child("description").getValue().toString(), TextView.BufferType.EDITABLE);
                    etQuantity.setText(dataSnapshot.child("quantity").getValue().toString(), TextView.BufferType.EDITABLE);
                    etSize.setText(dataSnapshot.child("size").getValue().toString(), TextView.BufferType.EDITABLE);
                }
                Toast.makeText(getApplicationContext(),"Data retrived",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void uploadImageOnclick(View view){
        itemId = etId.getText().toString();
        if (itemId!=null) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, ImageBack);
        }
        else{
            Toast.makeText(AddItem.this,"Set the id name first",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == ImageBack){
            if (resultCode == RESULT_OK){
                Uri ImageData = data.getData();
                final StorageReference ImageName  = mStorageRef.child("image"+ImageData.getLastPathSegment());
                ImageName.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AddItem.this,"Image Upload Successful",Toast.LENGTH_SHORT).show();
                        ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                 itemsReference.child(itemId).child("image").setValue(String.valueOf(uri));
                            }
                        });
                    }
                });
            }
        }
    }
}
