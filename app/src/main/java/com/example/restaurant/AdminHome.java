package com.example.restaurant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.restaurant.Models.ItemModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminHome extends AppCompatActivity {
    ImageView itemimage;
    EditText itemname,itemdes,itemprice;
    Spinner itemtype;
    ImageView extraimg1,extraimg2,extraimg3;
    CheckBox check;
    ProgressDialog loadingbar;
    private Uri ImageUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    public static final int GalleryPick = 1;
    Button submit,menu;
    String Sitemimage,Sitemname,Sitemdes,Sitemprice,Sitemtype,Scheck;

    String[] type={"Food","Snacks","Tiffins","Drinks"};
    private Calendar calendar;
    String  Sdate;
    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        itemimage=findViewById(R.id.itemimage);
        itemname=findViewById(R.id.itemname);
        loadingbar=new ProgressDialog(this);
        itemdes=findViewById(R.id.itemdesc);
        itemprice=findViewById(R.id.itemprice);
        itemtype=findViewById(R.id.itemtype);
        check=findViewById(R.id.check);
        menu=findViewById(R.id.menu);
        extraimg1=findViewById(R.id.extraimg1);
        extraimg2=findViewById(R.id.extraimg2);
        extraimg3=findViewById(R.id.extraimg3);
        submit=findViewById(R.id.submititem);
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Items");


        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("ddMMyyyy");
        Sdate = dateFormat.format(calendar.getTime());

        Scheck="false";
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    Scheck="true";
                }
            }
        });
        itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        extraimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        extraimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        extraimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminHome.this,newbookings.class);
                startActivity(i);
            }
        });
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemtype.setAdapter(aa);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
    }

    private void ValidateProductData()
    {
        Sitemname=itemname.getText().toString();
        Sitemdes=itemdes.getText().toString();
        Sitemprice=itemprice.getText().toString();

        Sitemtype=itemtype.getSelectedItem().toString().trim();


        if (ImageUri==null)
        {
            Toast.makeText(this, "Product Image is Required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Sitemname))
        {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Sitemprice)){
            Toast.makeText(this, "Price is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Sitemdes))
        {
            Toast.makeText(this, "Item Description is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Sitemtype))
        {
            Toast.makeText(this, "Item Type  is required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }
    private void StoreProductInformation()
    {
        loadingbar.setMessage("Please Wait");
        loadingbar.setTitle("Adding New Product");
        loadingbar.setCancelable(false);
        loadingbar.show();
        UploadImage();


    }
    public void UploadImage() {

        if (ImageUri != null) {

            loadingbar.setTitle("Image is Uploading...");
            loadingbar.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(ImageUri));
            storageReference2.putFile(ImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String TempItemName = itemname.getText().toString().trim();
                            String TempItemType = itemtype.getSelectedItem().toString().trim();
                            String TempItemdesc = itemdes.getText().toString().trim();
                            String TempItemPrice =itemprice.getText().toString().trim();

                            loadingbar.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    ItemModel userProfileInfo = new ItemModel(TempItemName,TempItemdesc,TempItemPrice,TempItemType,Scheck, url);
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(TempItemType).child(ImageUploadId).setValue(userProfileInfo);
                                    if (Scheck.equals("true")){
                                        databaseReference.child("Special").child(Sdate).child(ImageUploadId).setValue(userProfileInfo);
                                    }
                                    Intent i = new Intent(AdminHome.this,AdminHome.class);
                                    startActivity(i);
                                }
                            });

//                            @SuppressWarnings("VisibleForTests")
//
//                            uploadinfo imageUploadInfo = new uploadinfo(TempImageName,TempImageDescription,TempImagePrice, taskSnapshot.getUploadSessionUri().toString());
//                            String ImageUploadId = databaseReference.push().getKey();
//                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    });
        }
        else {

            Toast.makeText(AdminHome.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri=data.getData();
            itemimage.setImageURI(ImageUri);
        }
    }

    private void OpenGallery()
    {
        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GalleryPick);
    }

}