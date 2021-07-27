package com.example.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.restaurant.Adapters.FavoritesAdapter;
import com.example.restaurant.Adapters.HistoryAdapter;
import com.example.restaurant.Models.Booking;
import com.example.restaurant.Models.ItemModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DeliveryBoyHistory extends AppCompatActivity {

    private HistoryAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy_history);
        recyclerView = findViewById(R.id.idRVItems);
        mLinearLayoutManager = new LinearLayoutManager(this);
        //mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        FirebaseRecyclerOptions<Booking> options =
                new FirebaseRecyclerOptions.Builder<Booking>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("DeliveryBoys").child("111").child("History"), Booking.class)
                        .build();

        adapter = new HistoryAdapter(options,getApplicationContext());
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