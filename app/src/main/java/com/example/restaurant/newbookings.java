package com.example.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.restaurant.Adapters.CartAdapter;
import com.example.restaurant.Adapters.NewBookingAdapter;
import com.example.restaurant.Models.Booking;
import com.example.restaurant.Models.ItemModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class newbookings extends AppCompatActivity {

    private NewBookingAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbookings);
        recyclerView = findViewById(R.id.idRVItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Booking> options =
                new FirebaseRecyclerOptions.Builder<Booking>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("NewBookings"), Booking.class)
                        .build();

        adapter = new NewBookingAdapter(options,getApplicationContext());
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