package com.life.shelter.people.homeless;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class trampdata extends AppCompatActivity {
    EditText nameEditText, addressEditText, cityEditText;
    Button buttonSave;
    ImageView photoEdit;
    private ProgressBar progressBar;
    private Uri imagePath;
    public static final int PICK_IMAGE = 1;
    private StorageReference mStorageRef;
    DatabaseReference databaseTramp;
    DatabaseReference databaseReg;

    String userPhotoUri;
    String userName;
    String type,country;
    private FirebaseAuth mAuth;

    String s="a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trampdata);
        mAuth = FirebaseAuth.getInstance();
        databaseTramp= FirebaseDatabase.getInstance().getReference("trampoos");
        mStorageRef = FirebaseStorage.getInstance().getReference("trrrrr");
        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");

        nameEditText= (EditText)findViewById(R.id.edit_name);
        addressEditText= (EditText)findViewById(R.id.edit_address);
        cityEditText= (EditText)findViewById(R.id.edit_city);
        buttonSave= (Button) findViewById(R.id.button);
        photoEdit=(ImageView) findViewById(R.id.edit_photo);
        progressBar= findViewById(R.id.progressbar);


        photoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses image
                showFileChooser();
            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = 26)
            @Override
            public void onClick(View v) {

                try {// try & catch for Notification)
                    addTramp();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        /////////////////////////////////////
        getRegData();

    }

    private void getRegData() {
        databaseReg = FirebaseDatabase.getInstance().getReference("reg_data");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                type = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("ctype").getValue(String.class);
                country = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("ccountry").getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        databaseReg .addValueEventListener(postListener);
    }


    @RequiresApi(api = 26)
    private void addTramp() throws IOException {  //throws IOException because of try & catch vith method above
        String mtrampname = nameEditText.getText().toString();
        String mtrampaddress = addressEditText.getText().toString();
        String mtrampcity = cityEditText.getText().toString();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String userId = mAuth.getCurrentUser().getUid();
            if (user.getPhotoUrl() != null) {
                userPhotoUri = user.getPhotoUrl().toString();
            }
            if (user.getDisplayName() != null) {
                userName = user.getDisplayName();
            }

            //user name has space not null so i need to reset it to null
            if (user.getDisplayName().trim() == "") {
                userName = null;
            }
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String postdate = sdf.format(calendar.getTime());


            if ((!TextUtils.isEmpty(mtrampname)) && (!TextUtils.isEmpty(mtrampaddress)) && (!TextUtils.isEmpty(mtrampcity))) {
                if (!s.equals("a")) {

//we here replace code with one has the same id for both home and account activity
                    DatabaseReference reference = databaseTramp.push();
                    String id = reference.getKey();
                    HomeFirebaseClass homefirebaseclass = new HomeFirebaseClass(id, mtrampname, mtrampaddress, mtrampcity, s,
                            userPhotoUri, userName, postdate, userId);
                    //  databasetramp.push().setValue(homefirebaseclass);
                    databaseTramp.child(country).child(type).child("users").child(mAuth.getCurrentUser().getUid()).child(id).setValue(homefirebaseclass);

                    // databaseacount.push().setValue(homefirebaseclass);
                    Toast.makeText(this, "tramp data saved", Toast.LENGTH_LONG).show();
                    nameEditText.setText("");
                    addressEditText.setText("");
                    cityEditText.setText("");

                    Intent intent2 = new Intent(trampdata.this, home.class);

                    // Start the new activity
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent2);
                } else {
                    Toast.makeText(this, "you should add aphoto", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "you should fill all fields", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null&& data.getData() != null) {
            imagePath=data.getData();


            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                photoEdit.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage(){
        if (imagePath != null) {
            progressBar.setVisibility(View.VISIBLE);

            StorageReference trampsRef = mStorageRef.child(imagePath.getLastPathSegment());

            trampsRef.putFile(imagePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);

                            s = taskSnapshot.getDownloadUrl().toString();

                            Toast.makeText(trampdata.this, "image uploaded", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(trampdata.this, "an error occurred while  uploading image", Toast.LENGTH_LONG).show();

                        }
                    });
        }else {                            Toast.makeText(trampdata.this, "an error occurred while  uploading image", Toast.LENGTH_LONG).show();
        }
    }



}

