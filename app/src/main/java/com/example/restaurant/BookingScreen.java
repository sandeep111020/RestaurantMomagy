package com.example.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurant.Models.Booking;
import com.example.restaurant.Models.Mylatlong;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BookingScreen extends AppCompatActivity implements LocationListener {

    ArrayList<String> bookeddata = new ArrayList<String>();
    EditText name,number,address,area,city,state,pin;
    String Sname,Snumber,Saddress,Sarea,Scity,Sstate,Spin;
    Button submitl;
    String price;
    TextView test,ammount;
    private DatabaseReference databaseRef;

    String Slatitude, Slongitude;
    GetCurrentLocation currentLoc;
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_screen);
        name=findViewById(R.id.edt_name);
        number=findViewById(R.id.edt_phone_number);
        address=findViewById(R.id.edt_address1);
        area=findViewById(R.id.edt_area);
        city=findViewById(R.id.edt_address2);
        state=findViewById(R.id.edt_address3);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        pin=findViewById(R.id.edt_pin);
        submitl=findViewById(R.id.btn_pay);
        ammount=findViewById(R.id.txt_amount);
        test=findViewById(R.id.test);
        price=getIntent().getStringExtra("price");
        bookeddata= getIntent().getStringArrayListExtra("booked");
        test.setText(String.valueOf(bookeddata));
        ammount.setText(price);
        submitl.setText("Pay :"+price);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(BookingScreen.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
            },100);





        }
        getLocation();
        currentLoc = new GetCurrentLocation(this);
        //Slatitude=String.valueOf(currentLoc.latitude);
       // Slongitude=String.valueOf(currentLoc.longitude);


        submitl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sname=name.getText().toString();
                Snumber=number.getText().toString();
                Saddress=address.getText().toString();
                Sarea=area.getText().toString();
                Scity=city.getText().toString();
                Sstate=state.getText().toString();
                Spin=pin.getText().toString();
                if (TextUtils.isEmpty(Sname)){
                    Toast.makeText(BookingScreen.this,"Please enter name",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Snumber)){
                    Toast.makeText(BookingScreen.this,"Please enter number",Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(Saddress)){
                    Toast.makeText(BookingScreen.this,"Please enter address",Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(Sarea)){
                    Toast.makeText(BookingScreen.this,"Please enter area",Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(Scity)){
                    Toast.makeText(BookingScreen.this,"Please enter city",Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(Sstate)){
                    Toast.makeText(BookingScreen.this,"Please enter state",Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(Spin)){
                    Toast.makeText(BookingScreen.this,"Please enter pincode",Toast.LENGTH_SHORT).show();

                }
                else{
                    databaseRef = FirebaseDatabase.getInstance().getReference().child("NewBookings");
                    String UploadId = databaseRef.push().getKey();
                    Booking book = new Booking(Sname,Snumber,Saddress,Sarea,Scity,Sstate,Spin,price,bookeddata,currentuser);
                    databaseRef.child(UploadId).setValue(book);



                    DatabaseReference db=FirebaseDatabase.getInstance().getReference("Tracking").child(currentuser);
                    Mylatlong data=new Mylatlong("Your oder is placed","37.4217","-120.084");
                    db.child("Me").setValue(data);
                    Intent i =new Intent(BookingScreen.this,TrackingScreen.class);
                    startActivity(i);

                }

            }
        });


    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        
       // locationText.setText("Current Location: " + location.getLatitude() + ", " + location.getLongitude());
        Toast.makeText(BookingScreen.this,"Current Location: " + location.getLatitude() + ", " + location.getLongitude() , Toast.LENGTH_SHORT).show();
        Slatitude=String.valueOf(location.getLatitude());
        Slongitude=String.valueOf(location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(BookingScreen.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        currentLoc.connectGoogleApi();
    }

    @Override
    protected void onStop() {
        super.onStop();
        currentLoc.disConnectGoogleApi();
    }


}