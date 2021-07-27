package com.example.restaurant.Adapters;





import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ItemsAdapter extends FirebaseRecyclerAdapter<ItemModel, com.example.restaurant.Adapters.ItemsAdapter.myviewholder>{


    Context context;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    String Sdate;
    int i=0;
    String totalprice;
    private DatabaseReference databaseRef,databaseRef4;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseRef5;

    public ItemsAdapter(@NonNull FirebaseRecyclerOptions<ItemModel> options, Context context) {
        super(options);
        this.context = context;


    }




    @NonNull
    @Override
    public com.example.restaurant.Adapters.ItemsAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdisplaylayout, parent, false);

        return new com.example.restaurant.Adapters.ItemsAdapter.myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ItemModel model) {
        //holder.image.setText("Link: "+model.getItemimage());
        holder.name.setText(" "+model.getItemname());
        holder.desc.setText(" "+model.getItemdesc());
        holder.price.setText("Price "+model.getItemprice());

        Glide.with(context).load(model.getItemimage()).into(holder.image);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentuser);
        holder.addfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i%2!=0){
                    holder.addfav.setImageResource(R.drawable.ic_baseline_favorite_24);
                    ItemModel userProfileInfo = new ItemModel(model.getItemname(),model.getItemdesc(),model.getItemprice(),model.getItemtype(),model.getItemcheck(), model.getItemimage());
                    String ImageUploadId = databaseReference.push().getKey();
                    databaseReference.child("Fav").child(ImageUploadId).setValue(userProfileInfo);
                    Toast.makeText(context,"Item Added to Favorites",Toast.LENGTH_SHORT).show();
                }
                else if (i%2==0){
                    holder.addfav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    String ImageUploadId = databaseReference.push().getKey();
                    databaseReference.child("Fav").child(ImageUploadId).removeValue();
                    Toast.makeText(context,"Item Removed From  Favorites",Toast.LENGTH_SHORT).show();
                }

            }
        });
        holder.addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemModel userProfileInfo = new ItemModel(model.getItemname(),model.getItemdesc(),model.getItemprice(),model.getItemtype(),model.getItemcheck(), model.getItemimage());
                String ImageUploadId = databaseReference.push().getKey();
                databaseReference.child("MyCart").child(ImageUploadId).setValue(userProfileInfo);
                databaseRef5 = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Final");
                databaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       if (snapshot.exists()) {
                           String val= snapshot.child("finalprice").getValue(String.class);
                           int ival = Integer.parseInt(val)+ Integer.parseInt(model.getItemprice());
                           totalprice=String.valueOf(ival);
                           FinalValue finalval= new FinalValue(totalprice);
                           databaseReference.child("Final").setValue(finalval);
                       }
                       else{
                           totalprice = model.getItemprice();
                           FinalValue finalval= new FinalValue(totalprice);
                           databaseReference.child("Final").setValue(finalval);
                       }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                Toast.makeText(context,"Item Added to MyCart",Toast.LENGTH_SHORT).show();
            }
        });



    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView name, desc,price;

        ImageView image,addfav;

        Button addcart;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.Specname);
            desc = (TextView) itemView.findViewById(R.id.Specdesc);

            price = (TextView) itemView.findViewById(R.id.Specprice);
            image= (ImageView) itemView.findViewById(R.id.specimage);
            addfav=(ImageView) itemView.findViewById(R.id.addfav);
            addcart=(Button) itemView.findViewById(R.id.addcartbtton);



        }
    }



}