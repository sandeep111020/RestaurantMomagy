package com.example.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurant.Models.DeliveryProfile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Deliveryboyprofile extends AppCompatActivity {
    EditText id;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryboyprofile);
        id=findViewById(R.id.deliveryboyid);
        submit=findViewById(R.id.submitbtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(id.getText().toString())){
                    Toast.makeText(Deliveryboyprofile.this,"Enter Id",Toast.LENGTH_SHORT).show();
                }
                else {
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("DeliveryBoys").child("Profile").child(id.getText().toString());
                    DeliveryProfile data= new DeliveryProfile(id.getText().toString());
                    db.setValue(data);
                }
            }
        });
    }
}