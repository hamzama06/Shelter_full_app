package com.life.shelter.people.homeless;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;

import static android.support.v4.app.ActivityCompat.requestPermissions;


/**
 * Created by AHMED MAGDY on 9/15/2018.
 */

public class TrampHomeAdapter extends ArrayAdapter<HomeFirebaseClass> {

    private Activity context;
    private List<HomeFirebaseClass> trampList;
    private String a1, a2;
    String type,country;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReg;
    ImageView aTrampPhoto;

    private RegisterClass registerClass = new RegisterClass();

    public TrampHomeAdapter(Activity context, List<HomeFirebaseClass> trampList) {
        super(context, R.layout.list_layout_home, trampList);
        this.context = context;
        this.trampList = trampList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listViewItem = convertView;

        if (listViewItem == null){
            LayoutInflater inflater = context.getLayoutInflater();
            listViewItem = inflater.inflate(R.layout.list_layout_home, parent, false);
        }


        final TextView aTrampName = (TextView) listViewItem.findViewById(R.id.tramp_name);
        final TextView aTrampAddress = (TextView) listViewItem.findViewById(R.id.tramp_address);
        final TextView aTrampCity = (TextView) listViewItem.findViewById(R.id.tramp_city);


         aTrampPhoto = (ImageView) listViewItem.findViewById(R.id.tramp_photo);
        final ImageView aUserPhoto = (ImageView) listViewItem.findViewById(R.id.user_name_logo_list);



        final TextView aUserName = (TextView) listViewItem.findViewById(R.id.user_name_list);
        final TextView aDate = (TextView) listViewItem.findViewById(R.id.date_list);
        aUserName.equals(null);
        final ImageView aFaceLogo = (ImageView) listViewItem.findViewById(R.id.face_logo);
        final ImageView aDonateLogo = (ImageView) listViewItem.findViewById(R.id.donate_logo);
        // more option button
        ImageView moreOption = listViewItem.findViewById(R.id.more_list);

        final HomeFirebaseClass hometramp = trampList.get(position);
//////////////////////////////////////////////////////////////////

        // more option click listener
        moreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemDialog(hometramp, position);
            }
        });


        aUserName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent mIntent = new Intent(context, ProfileActivity.class);
                context.startActivity(mIntent);
                return true;
            }
        });
        aUserPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent mIntent = new Intent(context, ProfileActivity.class);
                context.startActivity(mIntent);
                return true;
            }
        });
        /***go to user page***/
        aUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFirebaseClass hometramp = trampList.get(position);
                Intent uIntent = new Intent(context, userwork.class);
                 uIntent.putExtra("userid",  hometramp.getUserId());
                 uIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 context.startActivity(uIntent);
                // context.finish();
            }
        });
        aUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFirebaseClass hometramp = trampList.get(position);
                Intent uIntent = new Intent(context, userwork.class);
                uIntent.putExtra("userid",  hometramp.getUserId());
                uIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(uIntent);
               // context.finish();
            }
        });



        aFaceLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission_Check();
            }
        });
///////////////////////////////////////////////////////////////////
        //chech box action
        final CheckBox tasken =(CheckBox)listViewItem.findViewById(R.id.tasken);

        tasken.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
                public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {

//               getRegData();
//             final Handler handler = new Handler();// delay
//              handler.postDelayed(new Runnable() {// delay
//                @Override
//              public void run() {// delay
//              if (!type.equals("Organization")) {
//
//                  Toast.makeText(context, "you must be an organization", Toast.LENGTH_LONG).show();
//
//                  buttonView.setChecked(false);
//                  return;
//              }
//              DatabaseReference databasetramp= FirebaseDatabase.getInstance().getReference("trampoos");
//              HomeFirebaseClass hometramp = trampList.get(position);
//
//              if (isChecked) {
//                  databasetramp.child(country).child("Organization").child("users").child(mAuth.getCurrentUser().getUid()).push().setValue(hometramp);
//
//                  FirebaseUser user = mAuth.getCurrentUser();
//                  if(user.getDisplayName() != null){
//                      tasken.setText(user.getDisplayName());
//                  }else {
//                      tasken.setText("Unknown name");;
//                  }
//              } else {
//                  databasetramp.child(country).child("Organization").child("users").child(mAuth.getCurrentUser().getUid()).push().setValue(null);
//
//              }}
//                                     }, 1000);// delay//
///////

              // get back with message if account type not Organization
              if (!registerClass.getType().equals("Organization")) {
                  Toast.makeText(context, "you must be an organization", Toast.LENGTH_SHORT).show();
                  buttonView.setChecked(false);
                  return;
              }

              // account type Organization
              DatabaseReference database = FirebaseDatabase.getInstance().getReference("trampoos")
                      .child(registerClass.getCountry())
                      .child(registerClass.getType())
                      .child("users")
                      .child(mAuth.getCurrentUser().getUid());
              // update check status
              database.child(hometramp.getId()).child("checked").setValue(isChecked);
              // update check box label
              if (isChecked) {
                  if (hometramp.getUserName() != null)
                      tasken.setText(hometramp.getUserName());
                  else
                      tasken.setText("Unknown name");
              }
              // feedback message
              Toast.makeText(context, "Data status changed!.", Toast.LENGTH_SHORT).show();

         }//

        });



        ///////////////////////////////////////////////////////////////////


        aTrampName.setText(hometramp.getName());
        aTrampAddress.setText(hometramp.getAddress());
        aTrampCity.setText(hometramp.getCity());

        a1=hometramp.getUri();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new RoundedCorners(16));

        Glide.with(context)
                .load(a1)
                .apply(requestOptions)
                .into(aTrampPhoto);



        aDate.setText(hometramp.getDate());

     ////////////////////////////////
        a2=hometramp.getUserUri();

          if(a2 != null){
         Glide.with(context)
         .load(a2)
                 .apply(RequestOptions.circleCropTransform())
         .into(aUserPhoto);
         }else {
         Glide.with(context)
         .load("https://firebasestorage.googleapis.com/v0/b/shelter-87aaa.appspot.com/o/user.png?alt=media&token=0a6b51c3-f1ec-4fea-a0eb-a7eaa45875d4")
                 .apply(RequestOptions.circleCropTransform())
                 .into(aUserPhoto);         }

         if(hometramp.getUserName() != null){
         aUserName.setText(hometramp.getUserName());
         }else {

         aUserName.setText("Unknown name");

         }


return listViewItem;
    }
    private void getRegData() {
////import data of country and tope
        databaseReg = FirebaseDatabase.getInstance().getReference("reg_data");
        mAuth = FirebaseAuth.getInstance();

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


    private void permission_Check() {
        //////////////////
        if (ActivityCompat.checkSelfPermission(context,android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(context , new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                return;
            }

        }
        SaveFile();
    }


    private void SaveFile() {

        // Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        //  String filePath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "عنوان الصورة", "info");
        //  ShareImage(Uri.parse(filePath));

        Bitmap bitmap = ((BitmapDrawable)aTrampPhoto.getDrawable()).getBitmap();
        SavePhoto(bitmap);

    }

    private void SavePhoto(Bitmap bitmap) {
        try {
            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Shelter";
            File dir = new File(file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, "Image_Share.jpeg");

            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray.toByteArray());
            fos.close();

            ShareImage(Uri.fromFile(file));

        } catch (Exception e) {

        }
    }

    public void ShareImage(Uri path) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, path);
        intent.putExtra(Intent.EXTRA_TEXT, "a tramp need help");
        context.startActivity(Intent.createChooser(intent, "share picture"));
    }

    /**
     *  Delete or Edit item data
     * @param item HomeFirebaseClass
     * @param position index of item in list
     */
    private void showItemDialog(final HomeFirebaseClass item, int position) {
        final Dialog dialog = new Dialog(context);
        final int itemIndex = position;
        String userId = mAuth.getCurrentUser().getUid();

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("trampoos")
                .child(registerClass.getCountry())
                .child(registerClass.getType())
                .child("users")
                .child(userId);

        // can edit or delete it
        if (item.isOwner(userId)) {
            dialog.setContentView(R.layout.delete_edit_dialog);

            Button edit = dialog.findViewById(R.id.edit_button_dialog);
            Button delete = dialog.findViewById(R.id.delete_button_dialog);
            Button close = dialog.findViewById(R.id.close_button_dialog);

            // update data
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,EditDataActivity.class);
                    intent.putExtra("data", (Serializable) item);
                    context.startActivity(intent);
                }
            });
            // remove data
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (database.child(item.getId()).removeValue().isSuccessful())
                        trampList.remove(itemIndex);
                }
            });
            // close dialog
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.setCanceledOnTouchOutside(false);
            // show dialog view
            dialog.show();
        }
    }




}

