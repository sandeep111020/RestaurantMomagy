package com.example.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.restaurant.Models.DeliveryBoysDeliveries;
import com.example.restaurant.Models.Mylatlong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AssignDelivery extends AppCompatActivity {

    Spinner spin;
    Button submit;
    ArrayList<String> users = new ArrayList<String>();
    private DatabaseReference databaseRef;
    String name, number,address,area,city,state,pin,id,price,count;
    private Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String dateTime;
    String  temp,lat,lon,head;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_delivery);
        spin=findViewById(R.id.spin1);
        submit=findViewById(R.id.submit);
        calendar = Calendar.getInstance();

        simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        dateTime = simpleDateFormat.format(calendar.getTime());

        id=getIntent().getStringExtra("id");
        name=getIntent().getStringExtra("name");
        number=getIntent().getStringExtra("number");
        address=getIntent().getStringExtra("address");
        area=getIntent().getStringExtra("area");
        city=getIntent().getStringExtra("city");
        state=getIntent().getStringExtra("state");
        pin=getIntent().getStringExtra("pin");
        price=getIntent().getStringExtra("amount");
        count=getIntent().getStringExtra("count");
        head=getIntent().getStringExtra("head");

        users.add("None");
        databaseRef = FirebaseDatabase.getInstance().getReference("DeliveryBoys").child("Profile");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String tempid= snapshot1.child("id").getValue(String.class);

                    users.add(tempid);

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db= FirebaseDatabase.getInstance().getReference().child("Tracking").child(id);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                temp=snapshot.child("Me").child("temp").getValue(String.class);
                lat=snapshot.child("Me").child("lat").getValue(String.class);
                lon=snapshot.child("Me").child("longt").getValue(String.class);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,users);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database=FirebaseDatabase.getInstance().getReference("DeliveryBoys").child(spin.getSelectedItem().toString()).child("Current");

                DatabaseReference database2=FirebaseDatabase.getInstance().getReference("DeliveryBoys").child(spin.getSelectedItem().toString()).child("History");


                DatabaseReference db=FirebaseDatabase.getInstance().getReference("Tracking").child(id).child("Me");
                temp="Your Item is Packed and Delivery Boys is started with your items";
                Mylatlong data= new Mylatlong(temp,lat,lon);
                db.setValue(data);
                DeliveryBoysDeliveries datasend=new DeliveryBoysDeliveries(name,number,address,area,city,state,pin,price,count,id);
                database.setValue(datasend);
                database2.child(dateTime).setValue(datasend);
                DatabaseReference db2 =FirebaseDatabase.getInstance().getReference("NewBookings");
                db2.child(head).removeValue();
                Intent i = new Intent(AssignDelivery.this,newbookings.class);
                startActivity(i);
            }
        });
    }
}