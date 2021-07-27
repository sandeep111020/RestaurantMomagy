package com.example.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.restaurant.Adapters.CartAdapter;
import com.example.restaurant.Adapters.FavoritesAdapter;
import com.example.restaurant.Models.FinalValue;
import com.example.restaurant.Models.ItemModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActiviity extends AppCompatActivity {
    RecyclerView recyclerView;
    String currentuser;
    Button place;
    LottieAnimationView lottieAnimationView;
    private CartAdapter adapter;
    private DatabaseReference databaseRef5,db;
    TextView test;
    ArrayList<String> users = new ArrayList<String>();
    String totalprice;
    private DatabaseReference databaseRef1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_activiity);
        recyclerView = findViewById(R.id.idRVItems);
        test=findViewById(R.id.test);

        place=findViewById(R.id.placeanorder);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lottieAnimationView=findViewById(R.id.lav_actionBar);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseDatabase.getInstance().getReference().child("Tracking").child(currentuser);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Intent i = new Intent(CartActiviity.this,TrackingScreen.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseRef5 = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser);
        databaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                if (snapshot.child("MyCart").getChildrenCount() == 0) {

                    FinalValue finval= new FinalValue("0");
                    databaseRef5.child("Final").setValue(finval);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    place.setVisibility(View.GONE);
                    lottieAnimationView.playAnimation();
                    test.setText("No Items In Your Cart");
                }
                totalprice = snapshot.child("Final").child("finalprice").getValue(String.class);
                place.setText(totalprice);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        databaseRef1 = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("MyCart");
        databaseRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String tempname= snapshot1.child("itemname").getValue(String.class);
                    String tempimage= snapshot1.child("itemimage").getValue(String.class);
                    String tempdesc= snapshot1.child("itemdesc").getValue(String.class);
                    String tempprice= snapshot1.child("itemprice").getValue(String.class);
                    String temptype= snapshot1.child("itemtype").getValue(String.class);
                    users.add("ItemName"+tempname+"   "+tempdesc+"   "+"ItemPrice"+tempprice+"   "+"ItemType: "+temptype+"   "+tempimage);

                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CartActiviity.this,BookingScreen.class);
                i.putExtra("price",totalprice);
                i.putExtra("booked",users);
                startActivity(i);
            }
        });




            FirebaseRecyclerOptions<ItemModel> options =
                    new FirebaseRecyclerOptions.Builder<ItemModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference("Users").child(currentuser).child("MyCart"), ItemModel.class)
                            .build();

            adapter = new CartAdapter(options,getApplicationContext());
            recyclerView.setAdapter(adapter);




    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}