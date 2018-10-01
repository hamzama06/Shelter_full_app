package com.life.shelter.people.homeless;

/**
 * Created by AHMED MAGDY on 9/23/2018.
 */

public class RegisterClass {
    private String email;
    private String country;
    private String type;



    public  RegisterClass() {
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        String userId = mAuth.getCurrentUser().getUid();
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference("reg_data");
//
//        Log.v("Register",database.child(userId).toString());
//
//        setCountry(database.child(userId).child("ccountry").toString());
//        setType(database.child(userId).child("ctype").toString());
//        setEmail(database.child(userId).child("cemail").toString());
    }

    public RegisterClass(String cemail, String ccountry, String ctype) {
        this.email = cemail;
        this.country = ccountry;
        this.type = ctype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String cemail) {
        this.email = cemail;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String ccountry) {
        this.country = ccountry;
    }

    public String getType() {
        return type;
    }

    public void setType(String ctype) {
        this.type = ctype;
    }
}
