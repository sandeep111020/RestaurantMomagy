package com.example.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.restaurant.Adapters.SpecialAdapter;
import com.example.restaurant.Models.ItemModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    CircleImageView profileimage;
    TextView nametext;
    RecyclerView recyclerView;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String Sdate;
    CardView fav;
    TextView cart;
    RelativeLayout food,tiffin,snacks,drinks;
    private SpecialAdapter adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        nametext=findViewById(R.id.Nametext);
        profileimage = findViewById(R.id.profileimage);
        food=findViewById(R.id.card_container);
        snacks=findViewById(R.id.card_container2);
        tiffin=findViewById(R.id.card_container3);
        cart=findViewById(R.id.cart);
        drinks=findViewById(R.id.card_container4);
        fav=findViewById(R.id.favid);
        recyclerView=findViewById(R.id.recycler_manu_meet);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        Sdate = dateFormat.format(calendar.getTime());


        FirebaseUser user = mAuth.getCurrentUser();

        Glide.with(this)
                .load(user.getPhotoUrl())
                .into(profileimage);

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeActivity.this,DisplayItems.class);
                i.putExtra("type","Food");
                startActivity(i);
            }
        });
        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,DisplayItems.class);
                i.putExtra("type","Snacks");
                startActivity(i);
            }
        });
        tiffin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,DisplayItems.class);
                i.putExtra("type","Tiffins");
                startActivity(i);
            }
        });
        drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,DisplayItems.class);
                i.putExtra("type","Drinks");
                startActivity(i);
            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,Favorites.class);
                startActivity(i);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,CartActiviity.class);
                startActivity(i);
            }
        });
        nametext.setText(" Hi,  "+user.getDisplayName()+"");
       // textName.setText(user.getDisplayName());
        //textEmail.setText(user.getEmail());

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FirebaseRecyclerOptions<ItemModel> options =
                new FirebaseRecyclerOptions.Builder<ItemModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Items").child("Special").child(Sdate), ItemModel.class)
                        .build();

        // .child("24052021130648")
        adapter3 = new SpecialAdapter(options,getApplicationContext());
        recyclerView.setAdapter(adapter3);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter3.startListening();
        //if the user is not logged in
        //opening the login activity
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter3.stopListening();
    }



}