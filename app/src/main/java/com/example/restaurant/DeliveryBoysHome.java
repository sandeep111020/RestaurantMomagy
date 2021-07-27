package com.example.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurant.Models.Mylatlong;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeliveryBoysHome extends AppCompatActivity {

    TextView name,number,address,area,city,state,pin,count,amount;


    ImageView history;
    FloatingActionButton fab;
    Button delivery,delivered;
    private DatabaseReference databaseRef,db;
    String id;
    String userlong,userlat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boys_home);
        name = (TextView) findViewById(R.id.personname);
        number = (TextView)findViewById(R.id.personnumber);
        address = (TextView)findViewById(R.id.personaddress);
        area = (TextView)findViewById(R.id.personarea);
        city = (TextView)findViewById(R.id.personcity);
        state = (TextView)findViewById(R.id.personstate);
        pin = (TextView) findViewById(R.id.personpin);
        delivered=findViewById(R.id.delivered);
        count=findViewById(R.id.personcount);
        history=findViewById(R.id.history);
        fab=findViewById(R.id.add_fab);
        amount=findViewById(R.id.totalamount);

        delivery=findViewById(R.id.makedelivery);


        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeliveryBoysHome.this,DeliveryBoyHistory.class);
                startActivity(i);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeliveryBoysHome.this,Deliveryboyprofile.class);
                startActivity(i);
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(DeliveryBoysHome.this,MapActivity.class);
                i.putExtra("id",id);
                i.putExtra("lat",userlat);
                i.putExtra("lon",userlong);
                startActivity(i);
            }
        });

        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db=FirebaseDatabase.getInstance().getReference("Tracking").child(id).child("Me");
                Mylatlong i= new Mylatlong("Your Item is Delivered",userlat,userlong);

                db.setValue(i);
            }
        });
        databaseRef = FirebaseDatabase.getInstance().getReference("DeliveryBoys").child("111").child("Current");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String Sname= snapshot.child("name").getValue(String.class);
                    String Snumber= snapshot.child("number").getValue(String.class);
                    String Saddress= snapshot.child("address").getValue(String.class);
                    String Sarea= snapshot.child("area").getValue(String.class);
                    String Scity= snapshot.child("city").getValue(String.class);
                    String Sstate= snapshot.child("state").getValue(String.class);
                    String Spin= snapshot.child("pin").getValue(String.class);
                    String Scount= snapshot.child("count").getValue(String.class);
                    String Samount= snapshot.child("price").getValue(String.class);
                    id=snapshot.child("id").getValue(String.class);

                    name.setText("Name: "+Sname);
                    number.setText("Number: "+Snumber);
                    address.setText("Address: "+Saddress);
                    area.setText("Area: "+Sarea);
                    city.setText("City: "+Scity);
                    state.setText("State: "+Sstate);
                    pin.setText("PinCode: "+Spin);
                    count.setText("Number of Items: "+Scount);
                    amount.setText("Amount:  "+Samount);
                db=FirebaseDatabase.getInstance().getReference("Tracking").child(id).child("Me");
                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        userlat= snapshot.child("lat").getValue(String.class);
                        userlong= snapshot.child("longt").getValue(String.class);







                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}