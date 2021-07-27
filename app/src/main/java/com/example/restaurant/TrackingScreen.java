package com.example.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackingScreen extends AppCompatActivity {
    Button showmap;
    TextView text;
    String mylat,mylon,dlat,dlon;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_screen);
        showmap=findViewById(R.id.showmapbutton);
        text=findViewById(R.id.trackingdetails);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i = new Intent(TrackingScreen.this,UserMap.class);
                i.putExtra("lat",mylat);
                i.putExtra("lon",mylon);
                i.putExtra("dlat",dlat);
                i.putExtra("dlon",dlon);
                startActivity(i);
            }
        });
        db= FirebaseDatabase.getInstance().getReference().child("Tracking").child(currentuser);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                text.setText(snapshot.child("Me").child("temp").getValue(String.class));
                mylat=snapshot.child("Me").child("lat").getValue(String.class);
                mylon=snapshot.child("Me").child("longt").getValue(String.class);
                dlat=snapshot.child("DelBoy").child("dlat").getValue(String.class);
                dlon=snapshot.child("DelBoy").child("dlong").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}