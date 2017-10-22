package world.emir.sparkairlines;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class ProfileActivity extends AppCompatActivity {

    TextView user_name;
    TextView user_email;
    Button editBtn;
    ImageView profile_picture;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);




    //Intilaze
        user_email = (TextView) findViewById(R.id.email);
        user_name = (TextView) findViewById(R.id.user_name);
        editBtn = (Button) findViewById(R.id.edit_btn);
        profile_picture = (ImageView) findViewById(R.id.profile_picture);





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

                String profile_pictures = dataSnapshot.child("image").getValue().toString();//Retriving picture link

                user_name.setText(name);
                user_email.setText(email);

                Picasso.with(getApplicationContext()).load(profile_pictures).into(profile_picture);


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

                Intent setupProfileIntent = new Intent(ProfileActivity.this, SetupProfileActivity.class);
                setupProfileIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(setupProfileIntent);


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
