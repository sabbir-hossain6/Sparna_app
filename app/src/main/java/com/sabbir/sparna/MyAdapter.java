package com.sabbir.sparna;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<product> products;

    public MyAdapter(Context context, ArrayList<product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.name.setText(products.get(position).getName());
        holder.type.setText("Type: "+products.get(position).getType());
        holder.description.setVisibility(View.GONE);
        holder.sizes.setText("Size: "+products.get(position).getSize());
        holder.quantity.setText("Stock: "+products.get(position).getQuantity()+" pieces");
        holder.price.setText("Price: "+products.get(position).getPrice());
        holder.tvItemId.setText(products.get(position).getItemId());
        holder.imageURL.setText(products.get(position).getImage());

        if (products.get(position).getOnOffer().compareTo("Yes")==0) {
            holder.salePrice.setText("Offer Price: "+products.get(position).getOfferPrice());
            holder.onSale.setText("Item is on Sale");
        }
        else{
            holder.salePrice.setVisibility(View.GONE);
            holder.onSale.setVisibility(View.GONE);
        }

        Picasso.get().load(products.get(position).getImage()).into(holder.thumbView);

        holder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewProduct.productID = holder.tvItemId.getText().toString();
                Intent myIntent = new Intent(context,ViewProduct.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,type,description,sizes,quantity,price,salePrice,onSale,tvItemId,imageURL;
        Button viewBtn;
        ImageView thumbView;
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
            tvItemId = (TextView) inflate.findViewById(R.id.textView9);
            imageURL = (TextView) inflate.findViewById(R.id.imageURL);

            thumbView = (ImageView) inflate.findViewById(R.id.thumbView);

            viewBtn = (Button) inflate.findViewById(R.id.viewBtn);
        }
    }


}
