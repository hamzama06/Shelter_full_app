package com.life.shelter.people.homeless;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditDataActivity extends AppCompatActivity {

    private EditText nameEditText, addressEditText, cityEditText;
    private Button updateBtn;
    private ImageView photo;
    private ProgressBar progressBar;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_activity);

        // get intent data
        Intent intent = getIntent();
        final HomeFirebaseClass data = (HomeFirebaseClass) intent.getSerializableExtra("data");
        RegisterClass registerClass = new RegisterClass();
        database = FirebaseDatabase.getInstance().getReference("trampoos")
                .child(registerClass.getCountry())
                .child(registerClass.getType())
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        // link view
        nameEditText = findViewById(R.id.edit_data_name);
        addressEditText = findViewById(R.id.edit_data_address);
        cityEditText = findViewById(R.id.edit_data_city);
        updateBtn = findViewById(R.id.edit_data_button);
        photo = findViewById(R.id.edit_data_photo);
        progressBar= findViewById(R.id.progressbar);

        // set view data
        nameEditText.setText(data.getName());
        addressEditText.setText(data.getAddress());
        cityEditText.setText(data.getCity());
        Glide.with(getApplicationContext())
                .load(data.getUri())
                .apply(RequestOptions.circleCropTransform())
                .into(photo);

        // update button listener
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show progress Bar
                progressBar.setVisibility(View.VISIBLE);
                // get view data
                String mName = nameEditText.getText().toString();
                String mAddress = addressEditText.getText().toString();
                String mCity = cityEditText.getText().toString();
                // set edit date
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                String postdate = sdf.format(calendar.getTime());
                // check if all field filled
                if ((!TextUtils.isEmpty(mName)) && (!TextUtils.isEmpty(mAddress)) && (!TextUtils.isEmpty(mCity))) {
                   // String id = data.getId();
                    String ownerId = data.getUserId();
                    String userName = data.getName();
                    String userphotoUri = data.getUserUri();
                    String photoUrl = data.getUri();
                    HomeFirebaseClass homefirebaseclass = new HomeFirebaseClass(mName, mAddress, mCity, photoUrl,
                            userphotoUri, userName, postdate, ownerId);
                    // save data to database

                    database.child("....id....").setValue(homefirebaseclass);

                    // hide progress Bar
                    progressBar.setVisibility(View.GONE);
                    // success message
                    Toast.makeText(EditDataActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
                    // clean view data
                    nameEditText.setText("");
                    addressEditText.setText("");
                    cityEditText.setText("");
                    // move to home Page
                    Intent intent = new Intent(EditDataActivity.this, home.class);
                    // Start the new activity
                    startActivity(intent);
                }
                else
                    Toast.makeText(EditDataActivity.this, "you should fill all fields", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
