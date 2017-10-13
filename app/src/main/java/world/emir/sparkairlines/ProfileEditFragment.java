package world.emir.sparkairlines;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileEditFragment extends Fragment {


    public ProfileEditFragment() {
        // Required empty public constructor
    }

    /* ************************* */
    EditText edit_names;
    EditText edit_emails;
    ImageView defualt_img;
    ImageView profile_img;
    Button save_btn;
    Button delete_btn;
    Button upload_btn;

  //Progress dialog
    private  ProgressDialog mProgress;

    /* ************************* */



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile_edit, container, false);


        //Intilaze

        edit_names = (EditText) rootView.findViewById(R.id.name_ed);
        defualt_img = (ImageView) rootView.findViewById(R.id.profile_img);
        save_btn = (Button) rootView.findViewById(R.id.save_btn);
        delete_btn = (Button) rootView.findViewById(R.id.btn_del);
        upload_btn = (Button) rootView.findViewById(R.id.change_img);
        profile_img = (ImageView) rootView.findViewById(R.id.profile_img);

        //Progress dialog
        mProgress = new ProgressDialog(getActivity());


        /* ******************************************************************* */

        //Firebase

        final FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        final DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);


       //Retriving name show it in EditText fileds

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString(); //Get name from firebase child name
                String email = dataSnapshot.child("email").getValue().toString(); //Get email
                String profile_image = dataSnapshot.child("profile_image").getValue().toString(); //Get email


                edit_names.setText(name);

                Log.i("Picture",profile_image);
                Glide.with(getActivity())
                        .load(profile_image)
                        .fitCenter()
                        .placeholder(R.drawable.sea_default)
                        .into(profile_img);

                /**
                 *
                 */


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Buttons


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Progress dialog

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Updating...", "Please wait...", true);


                final String edit_name = edit_names.getText().toString();


                mUserDatabase.child("name").setValue(edit_name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            Log.i("Submited ",edit_name);

                           dialog.dismiss();


                        }else{

                            Toast.makeText(getActivity(),"Sorry: Something went wrong!",Toast.LENGTH_SHORT).show();

                        }
                    }
                });









            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("UPLOAD NEW PROFILE PICTURE")
                        .setMessage("Pleas enter, link for your profile picture " +mCurrentUser.getEmail());


                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lap = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lap);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.ic_file_upload_black_24dp);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final String picture_link = input.getText().toString();

                                //Progress dialog

                                final ProgressDialog dialog1 = ProgressDialog.show(getActivity(), "Updating...", "Please wait...", true);



                                mUserDatabase.child("profile_image").setValue(picture_link).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            Log.i("Submited ", picture_link);

                                            dialog1.dismiss();


                                        } else {

                                            Toast.makeText(getActivity(), "Sorry: Something went wrong!", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            }


                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();


            }

        });







        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("PASSWORD")
                .setMessage("Pleas enter password to delete your account " +mCurrentUser.getEmail());


                final EditText input = new EditText(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.ic_lock_black_24dp);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String password = input.getText().toString();

                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                // Get auth credentials from the user for re-authentication. The example below shows
                                // email and password credentials but there are multiple possible providers,

                                AuthCredential credential = EmailAuthProvider
                                        .getCredential(user.getEmail(),password);

                                // Prompt the user to re-provide their sign-in credentials
                                user.reauthenticate(credential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                user.delete()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(getActivity(),"Your account is deleted!",Toast.LENGTH_SHORT);
                                                                    Log.d("Firebase :", "User account deleted.");
                                                                }
                                                            }
                                                        });

                                            }
                                        });

                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();


            }
        });


    return  rootView;
    }

}






