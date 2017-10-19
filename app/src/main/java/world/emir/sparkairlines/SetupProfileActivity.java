package world.emir.sparkairlines;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Criteria;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class SetupProfileActivity extends AppCompatActivity {

    private Button mUpdateButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private ImageButton mProfileImButton;
    private EditText mEditName;
    private Uri mImageUri = null;
    private StorageReference mStorage;
    private ProgressDialog mProgess;


    private static final int GALLERY_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile);

        mStorage = FirebaseStorage.getInstance().getReference().child("Profile_images");
        mAuth =FirebaseAuth.getInstance();
        mDatabaseUsers =FirebaseDatabase.getInstance().getReference().child("Users");

        mUpdateButton = (Button) findViewById(R.id.update_btn);
        mProfileImButton = (ImageButton) findViewById(R.id.profile_img);
        mEditName = (EditText) findViewById(R.id.setup_name);

        mProgess = new ProgressDialog(this);


        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSetupAcoount();

            }
        });

        mProfileImButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });

    }

    private void startSetupAcoount() {
         final String name = mEditName.getText().toString().trim();

        final String user_id = mAuth.getCurrentUser().getUid();
        if (!TextUtils.isEmpty(name) && mImageUri != null){
            mProgess.setMessage("Finishing setup...");
            mProgess.show();

            StorageReference filepath = mStorage.child(mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String downloadUri = taskSnapshot.getDownloadUrl().toString();

                    mDatabaseUsers.child(user_id).child("name").setValue(name);
                    mDatabaseUsers.child(user_id).child("image").setValue(downloadUri);

                    mProgess.dismiss();

                    Intent mainIntent = new Intent(SetupProfileActivity.this, MainActivity.class);
                    mainIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);
                }
            });





        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(2,2)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                mImageUri = result.getUri();
                mProfileImButton.setImageURI(mImageUri);
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
