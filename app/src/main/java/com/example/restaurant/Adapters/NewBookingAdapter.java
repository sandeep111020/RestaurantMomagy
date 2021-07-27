package com.example.restaurant.Adapters;





import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.restaurant.AssignDelivery;
import com.example.restaurant.CartActiviity;
import com.example.restaurant.Models.Booking;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;


public class NewBookingAdapter extends FirebaseRecyclerAdapter<Booking, com.example.restaurant.Adapters.NewBookingAdapter.myviewholder>{


    Context context;
    private DatabaseReference databaseReference;
    String totalprice;
    ArrayList itemstring;
    private DatabaseReference databaseRef5;
    private TextView[] t;
    String itemkey;

    int intcount=0;


    public NewBookingAdapter(@NonNull FirebaseRecyclerOptions<Booking> options, Context context) {
        super(options);
        this.context = context;


    }




    @NonNull
    @Override
    public com.example.restaurant.Adapters.NewBookingAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newbookinglayout, parent, false);

        return new com.example.restaurant.Adapters.NewBookingAdapter.myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Booking model) {

        itemkey= getRef(position).getKey();

        holder.name.setText("Name: "+model.getName());
        holder.number.setText("Number: "+model.getNumber());
        holder.state.setText("State: "+model.getState());
        holder.address.setText("Address: "+model.getAddress());
        holder.area.setText("Area: "+model.getArea());
        holder.city.setText("City: "+model.getCity());
        holder.pin.setText("PinCode: "+model.getPin());
        itemstring=model.getBookeddata();
        Iterator itr=itemstring.iterator();
        t = new TextView[100];


        while(itr.hasNext()){
            intcount++;
            String str = String.valueOf(itr.next());
            String[] arrOfStr = str.split("   ");
            int i=0;
            for (String a : arrOfStr){

                t[i]=new TextView(context);
                t[i].setText(a);
                if(a.contains("https://")){
                    t[i].setText(" ");
                }


                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                t[i].setLayoutParams(params);
                holder.layout.addView(t[i]);
                i++;

            }
                //System.out.println(a);
            //System.out.println(itr.next());
        }


        holder.delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, AssignDelivery.class);
                i.putExtra("name",model.getName());
                i.putExtra("number",model.getNumber());
                i.putExtra("address",model.getAddress());
                i.putExtra("area",model.getArea());
                i.putExtra("city",model.getCity());
                i.putExtra("pin",model.getPin());
                i.putExtra("state",model.getState());
                i.putExtra("amount",model.getPrice());
                i.putExtra("id",model.getId());
                i.putExtra("head",itemkey);
                i.putExtra("count",String.valueOf(intcount));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        //Glide.with(context).load(model.getItemimage()).into(holder.image);




    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView name,number,address,area,city,state,pin;

        ImageView image;

        Button delivery;
        LinearLayout layout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.personname);
            number = (TextView) itemView.findViewById(R.id.personnumber);
            address = (TextView) itemView.findViewById(R.id.personaddress);
            area = (TextView) itemView.findViewById(R.id.personarea);
            city = (TextView) itemView.findViewById(R.id.personcity);
            state = (TextView) itemView.findViewById(R.id.personstate);
            pin = (TextView) itemView.findViewById(R.id.personpin);
            layout=itemView.findViewById(R.id.dynamlayout);
            delivery=itemView.findViewById(R.id.makedelivery);


        }
    }



}