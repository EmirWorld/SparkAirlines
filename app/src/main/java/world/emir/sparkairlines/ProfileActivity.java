package world.emir.sparkairlines;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView user_name;
    TextView user_email;
    Button editBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //Intilaze
        user_email = (TextView) findViewById(R.id.email);
        user_name = (TextView) findViewById(R.id.user_name);
        editBtn = (Button) findViewById(R.id.edit_btn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Firebase get Name and Email

        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString(); //Get name from firebase child name
                String email = dataSnapshot.child("email").getValue().toString(); //Get email from firebase child email


                user_name.setText(name);
                user_email.setText(email);

                /**
                 * Sets the Action Bar for new Android versions.
                 */

                android.support.v7.app.ActionBar ab = getSupportActionBar();
                ab.setTitle(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //********

        //********Button to edit ****//

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProfileEditFragment profileEditFragment = new ProfileEditFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.profile_view,profileEditFragment).commit();


            }
        });
        //**************************************************************//



    }

    /*Back button on activity*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /* ************************* */
}
