package com.example.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.restaurant.Adapters.FavoritesAdapter;

import com.example.restaurant.Models.ItemModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Favorites extends AppCompatActivity {
    RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    String currentuser;
    TextView test;
    LottieAnimationView lottieAnimationView;
    private DatabaseReference databaseRef5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        test=findViewById(R.id.test);
        recyclerView = findViewById(R.id.idRVItems);
        lottieAnimationView=findViewById(R.id.lav_actionBar);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseRef5 = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser).child("Fav");
        databaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getChildrenCount() == 0) {

                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();
                    test.setText("No Items In Your Favorites");
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

     /*   ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(animation -> {
                    lottieAnimationView
                            .setProgress((Float) animation.getAnimatedValue());
                });
        animator.start();
        lottieAnimationView.addAnimatorUpdateListener(
                        (animation) -> {
                            // Do something.
                        });
        lottieAnimationView.playAnimation();*/


        FirebaseRecyclerOptions<ItemModel> options =
                new FirebaseRecyclerOptions.Builder<ItemModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Users").child(currentuser).child("Fav"), ItemModel.class)
                        .build();

        adapter = new FavoritesAdapter(options,getApplicationContext());
        recyclerView.setAdapter(adapter);
        ;
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