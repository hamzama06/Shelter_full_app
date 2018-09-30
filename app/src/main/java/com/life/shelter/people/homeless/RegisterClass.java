package com.life.shelter.people.homeless;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by AHMED MAGDY on 9/23/2018.
 */

public class RegisterClass {
    private String cemail;
    private String ccountry;
    private String ctype;


    public  RegisterClass() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("reg_data");

        Log.v("Register",database.child(userId).toString());

        setCountry(database.child(userId).child("ccountry").toString());
        setType(database.child(userId).child("ctype").toString());
        setEmail(database.child(userId).child("cemail").toString());
    }

    public RegisterClass(String cemail, String ccountry, String ctype) {
        this.cemail = cemail;
        this.ccountry = ccountry;
        this.ctype = ctype;
    }

    public String getEmail() {
        return cemail;
    }

    public void setEmail(String cemail) {
        this.cemail = cemail;
    }

    public String getCountry() {
        return ccountry;
    }

    public void setCountry(String ccountry) {
        this.ccountry = ccountry;
    }

    public String getType() {
        return ctype;
    }

    public void setType(String ctype) {
        this.ctype = ctype;
    }
}
