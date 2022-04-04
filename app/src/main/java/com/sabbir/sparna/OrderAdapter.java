package com.sabbir.sparna;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>{

    Context context;
    ArrayList<product> products;
    String text;
    public static int totalPrice=0;

    public OrderAdapter(Context context, ArrayList<product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.orderview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.name.setText(products.get(position).getName());
        holder.type.setText("Type: "+products.get(position).getType());
        holder.sizes.setText("Sizes: "+products.get(position).getSize());
        holder.quantity.setText("Quantity: "+products.get(position).getQuantity());
        holder.price.setText("Price: "+products.get(position).getPrice());
        holder.productId.setText(products.get(position).getItemId());
        if (products.get(position).getOnOffer().compareTo("No")==0) {
            holder.salePrice.setVisibility(View.GONE);
            holder.onSale.setVisibility(View.GONE);
            totalPrice = totalPrice + Integer.parseInt(products.get(position).getPrice());
        }
        else {
            holder.salePrice.setText("Sale Price: "+products.get(position).getOfferPrice());
            holder.onSale.setText("Item is on Sale");
            totalPrice = totalPrice + Integer.parseInt(products.get(position).getOfferPrice());
        }
        Orders.textView.setText("Your order has been placed top your designated address on your profile\nTotal price is: "+totalPrice);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,type,description,sizes,quantity,price,salePrice,onSale,productId;
        public MyViewHolder(View inflate) {
            super(inflate);
            name = (TextView) inflate.findViewById(R.id.textView1);
            type = (TextView) inflate.findViewById(R.id.textView2);
            description = (TextView) inflate.findViewById(R.id.textView3);
            sizes = (TextView) inflate.findViewById(R.id.textView4);
            quantity = (TextView) inflate.findViewById(R.id.textView5);
            price = (TextView) inflate.findViewById(R.id.textView6);
            salePrice = (TextView) inflate.findViewById(R.id.textView7);
            onSale = (TextView) inflate.findViewById(R.id.textView8);
            productId = (TextView) inflate.findViewById(R.id.productId);
        }
    }


}
