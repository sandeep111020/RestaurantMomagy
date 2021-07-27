package com.example.restaurant.Adapters;




import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.restaurant.R;

import com.example.restaurant.Models.ItemModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class SpecialAdapter extends FirebaseRecyclerAdapter<ItemModel, com.example.restaurant.Adapters.SpecialAdapter.myviewholder>{


    Context context;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    String Sdate;
    private DatabaseReference databaseRef,databaseRef4;

    public SpecialAdapter(@NonNull FirebaseRecyclerOptions<ItemModel> options, Context context) {
        super(options);
        this.context = context;


    }




    @NonNull
    @Override
    public com.example.restaurant.Adapters.SpecialAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.specialitemslayout, parent, false);

        return new com.example.restaurant.Adapters.SpecialAdapter.myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ItemModel model) {
        //holder.image.setText("Link: "+model.getItemimage());
        holder.name.setText(" "+model.getItemname());
        holder.desc.setText(" "+model.getItemdesc());
        holder.price.setText("Price "+model.getItemprice());

        Glide.with(context).load(model.getItemimage()).into(holder.image);

    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView name, desc,price;

        ImageView image;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.Specname);
            desc = (TextView) itemView.findViewById(R.id.Specdesc);

            price = (TextView) itemView.findViewById(R.id.Specprice);
            image= (ImageView) itemView.findViewById(R.id.specimage);



        }
    }



}