package world.emir.sparkairlines;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by world on 9/26/2017.
 */

public class RegisterActivity  extends AppCompatActivity{

    //
    private EditText mNameField;
    private EditText mPasswordField;
    private EditText mEmailField;

    private Button mRegisterBtn;


    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;


    private DatabaseReference mDatabase;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNameField = (EditText) findViewById(R.id.name_field);
        mPasswordField = (EditText) findViewById(R.id.password_field);
        mEmailField = (EditText) findViewById(R.id.email_field);
        mRegisterBtn = (Button) findViewById(R.id.registerBtn);



        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startRegister();
            }
        });



    }

    private void startRegister() {
        final String name = mNameField.getText().toString();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            mProgress.setMessage("Signing Up ...");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful()){

                     String user_id = mAuth.getCurrentUser().getUid();
                     DatabaseReference curent_user_db = mDatabase.child(user_id);

                     curent_user_db.child("name").setValue(name);
                     curent_user_db.child("image").setValue("default");
                     mProgress.dismiss();

                     Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                     mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(mainIntent);


                 }
                }
            });

        }

    }
}
