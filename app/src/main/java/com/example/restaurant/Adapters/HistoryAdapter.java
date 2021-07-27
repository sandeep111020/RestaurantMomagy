package com.example.restaurant.Adapters;




import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.restaurant.Models.Booking;
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


public class HistoryAdapter extends FirebaseRecyclerAdapter<Booking, com.example.restaurant.Adapters.HistoryAdapter.myviewholder>{


    Context context;
    DatabaseReference databaseReference;
    ArrayList itemstring;

    private TextView[] t;
    String itemkey;

    int intcount=0;



    public HistoryAdapter(@NonNull FirebaseRecyclerOptions<Booking> options, Context context) {
        super(options);
        this.context = context;


    }




    @NonNull
    @Override
    public com.example.restaurant.Adapters.HistoryAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historyorderlayout, parent, false);

        return new com.example.restaurant.Adapters.HistoryAdapter.myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Booking model) {
        //holder.image.setText("Link: "+model.getItemimage());

        holder.name.setText("Name: "+model.getName());
        holder.number.setText("Number: "+model.getNumber());
        holder.price.setText("Price: "+model.getPrice());
        holder.pin.setText("Pin: "+model.getPin());
        holder.address.setText("Address: "+model.getAddress());
        holder.area.setText("Area: "+model.getArea());
        holder.city.setText("City: "+model.getCity());
        holder.state.setText("State: "+model.getState());

     /*   itemstring=model.getBookeddata();
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
*/
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView name,number,price,address,area,city,state,pin,count;

        ImageView image;
        LottieAnimationView lottie;
        LinearLayout layout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.personname);
            number = (TextView) itemView.findViewById(R.id.personnumber);

            price = (TextView) itemView.findViewById(R.id.totalamount);
            address=itemView.findViewById(R.id.personaddress);
            city = (TextView) itemView.findViewById(R.id.personcity);
            count = (TextView) itemView.findViewById(R.id.personcount);
            pin = (TextView) itemView.findViewById(R.id.personpin);
            state = (TextView) itemView.findViewById(R.id.personstate);

            layout=itemView.findViewById(R.id.dynamlayout);
            area = (TextView) itemView.findViewById(R.id.personarea);





        }
    }



}