package com.life.shelter.people.homeless;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private DatabaseReference databaseTramp;
    private DatabaseReference databaseReg;
    String type,country;
    ListView listViewTramp;
    private ProgressBar progressBar;
    List<HomeFirebaseClass> trampList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button addTrampButton = (Button) findViewById(R.id.add_tramp_h);
        progressBar = (ProgressBar) findViewById(R.id.home_progress_bar);
        mAuth = FirebaseAuth.getInstance();

        databaseTramp= FirebaseDatabase.getInstance().getReference("trampoos");
        databaseReg = FirebaseDatabase.getInstance().getReference("reg_data");
        mStorageRef = FirebaseStorage.getInstance().getReference("trrrrr");
        listViewTramp= (ListView)findViewById(R.id.list_view_tramp);
        trampList=new ArrayList<>();

        //Add to Activity
        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");

       progressBar.setVisibility(View.VISIBLE);

        addTrampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent it = new Intent(home.this, trampdata.class);
                startActivity(it);
            }
        });




        //pour cela, on commence par regarder si on a déjà des éléments sauvegardés


///////////////////////////////////////////////////////////

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ////////////////////////////////////////////////////////////
    }
    @Override
    protected void onStart() {
        super.onStart();

        getRegData();

        final Handler handler = new Handler();// delay
        handler.postDelayed(new Runnable() {// delay
            @Override
            public void run() {// delay
                // Do something after 3s = 3000ms


        if (isNetworkConnected()) {
            if(country != null &&  type != null) {
                databaseTramp.child(country).child("Individiual").child("users").addListenerForSingleValueEvent(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        trampList.clear();

                        for (DataSnapshot userid : dataSnapshot.getChildren()) {

                            for (DataSnapshot userdataSnapshot : userid.getChildren()) {

                                String cId = userdataSnapshot.child("cId").getValue(String.class);
                                String hname = userdataSnapshot.child("cName").getValue(String.class);
                                String haddress = userdataSnapshot.child("cAddress").getValue(String.class);
                                String hcity = userdataSnapshot.child("cCity").getValue(String.class);
                                String huri = userdataSnapshot.child("cUri").getValue(String.class);
                                String huseruri = userdataSnapshot.child("userUri").getValue(String.class);
                                String husername = userdataSnapshot.child("username").getValue(String.class);
                                String hpdate = userdataSnapshot.child("pdate").getValue(String.class);
                                String huserid = userdataSnapshot.child("userid").getValue(String.class);

                                HomeFirebaseClass hometramp = new HomeFirebaseClass(cId ,hname, haddress, hcity, huri, huseruri, husername, hpdate,huserid);
                                trampList.add(0, hometramp);
                            }
                        }
                        TrampHomeAdapter adapter = new TrampHomeAdapter(home.this, trampList);
                        //adapter.notifyDataSetChanged();
                        listViewTramp.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressBar.setVisibility(View.GONE);
                    }
                });


            }}else {
            progressBar.setVisibility(View.GONE);
        Toast.makeText(home.this, "please check the network connection", Toast.LENGTH_LONG).show();
    }
    }
        }, 3000);// delay


    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_account) {
            Intent it = new Intent(home.this, Account.class);
            startActivity(it);
        } else if (id == R.id.nav_about) {
                    Intent it = new Intent(home.this, About.class);
                    startActivity(it);
        } else if (id == R.id.nav_faq) {

            Intent it = new Intent(home.this, FAQ.class);
            startActivity(it);
        } else if (id == R.id.nav_charitable) {

            Intent it = new Intent(home.this, CharitableOrganizations.class);
            startActivity(it);
        } else if (id == R.id.nav_supporting) {

            Intent it = new Intent(home.this, Supporting.class);
            startActivity(it);
        } else if (id == R.id.nav_developers) {

            Intent it = new Intent(home.this, Developers.class);
            startActivity(it);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_org) {
            Intent it = new Intent(home.this, displayOrganizations.class);
            startActivity(it);

        } else if (id == R.id.nav_rate) {

        }else if (id == R.id.nav_profile) {
            Intent it = new Intent(home.this, ProfileActivity.class);
            startActivity(it);


        }else if (id == R.id.nav_out) {
            mAuth.getInstance().signOut();
            Intent it = new Intent(home.this, Login.class);
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            finish();
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
////////////////////////////////////////////////////////////////////////
private boolean isNetworkConnected() {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo ni = cm.getActiveNetworkInfo();
    if (ni != null) {
        return true;
    } else {
        return false;
    }
}
    private void getRegData() {


    ////import data of country and tope
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

}
