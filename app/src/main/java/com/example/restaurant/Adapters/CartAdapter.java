package com.example.restaurant.Adapters;





import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.restaurant.CartActiviity;
import com.example.restaurant.Models.FinalValue;
import com.example.restaurant.R;

import com.example.restaurant.Models.ItemModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CartAdapter extends FirebaseRecyclerAdapter<ItemModel, com.example.restaurant.Adapters.CartAdapter.myviewholder>{


    Context context;
    private DatabaseReference databaseReference;
    String totalprice;
    private DatabaseReference databaseRef5;


    public CartAdapter(@NonNull FirebaseRecyclerOptions<ItemModel> options, Context context) {
        super(options);
        this.context = context;


    }




    @NonNull
    @Override
    public com.example.restaurant.Adapters.CartAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoritesitemslayout, parent, false);

        return new com.example.restaurant.Adapters.CartAdapter.myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ItemModel model) {
        //holder.image.setText("Link: "+model.getItemimage());
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        holder.name.setText(" "+model.getItemname());
        holder.desc.setText(" "+model.getItemdesc());
        holder.price.setText("Price: "+model.getItemprice());






        Glide.with(context).load(model.getItemimage()).into(holder.image);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentuser);
        final DatabaseReference itemRef = getRef(position);
        final String myKey = itemRef.getKey();
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseRef5 = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Final");
                databaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                            String val= snapshot.child("finalprice").getValue(String.class);
                            int ival = Integer.parseInt(val) - Integer.parseInt(model.getItemprice());
                            totalprice=String.valueOf(ival);
                            FinalValue finalval= new FinalValue(totalprice);
                            databaseReference.child("Final").setValue(finalval);




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                databaseReference.child("MyCart").child(myKey).removeValue();
                Toast.makeText(context,model.getItemname()+"  is Deleted From Your Cart",Toast.LENGTH_SHORT).show();

            }
        });


    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView name, desc,price,delete;

        ImageView image;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.Specname);
            desc = (TextView) itemView.findViewById(R.id.Specdesc);

            price = (TextView) itemView.findViewById(R.id.Specprice);
            image= (ImageView) itemView.findViewById(R.id.specimage);
            delete=(TextView) itemView.findViewById(R.id.itemdelete);


        }
    }



}