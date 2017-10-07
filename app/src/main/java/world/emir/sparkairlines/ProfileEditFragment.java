package world.emir.sparkairlines;


import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileEditFragment extends Fragment {


    public ProfileEditFragment() {
        // Required empty public constructor
    }

    /* ************************* */
    TextView edit_name;
    TextView edit_email;
    ImageView defualt_img;
    Button save_btn;
    Button delete_btn;

    /* ************************* */



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        //Intilaze

        edit_name = (TextView) rootView.findViewById(R.id.name_ed);
        edit_email = (TextView) rootView.findViewById(R.id.email_ed);
        defualt_img = (ImageView) rootView.findViewById(R.id.profile_img);
        save_btn = (Button) rootView.findViewById(R.id.save_btn);
        delete_btn = (Button) rootView.findViewById(R.id.btn_del);

        /* ******************************************************************* */

        //Buttons



        //Firebase get Name and Email

        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString(); //Get name from firebase child name
                String email = dataSnapshot.child("email").getValue().toString(); //Get email from firebase child email


                edit_name.setText(name);
                edit_email.setText(email);

                /**
                 *
                 */


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //********


        return rootView;

    }

  /** Edit submit EmirWorld ***/

}






