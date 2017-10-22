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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by world on 9/26/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    //
    private EditText mNameField;
    private EditText mPasswordField;
    private EditText mEmailField;

    private Button mRegisterBtn;
    private Button mLoginBtn;


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
        mLoginBtn = (Button) findViewById(R.id.button_login);


        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");



        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(RegIntent);
            }
        });


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startRegister();
            }
        });


    }

    private void startRegister() {
        final String name = mNameField.getText().toString();
        final String email = mEmailField.getText().toString();
        final String password = mPasswordField.getText().toString();
        if (!validate()) {
            onSignupFailed();
            return;
        }

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mProgress.setMessage("Signing Up ...");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference curent_user_db = mDatabase.child(user_id);

                        curent_user_db.child("name").setValue(name);
                        curent_user_db.child("email").setValue(email);
                        curent_user_db.child("password").setValue(password);
                        curent_user_db.child("role").setValue("member");
                        curent_user_db.child("image").setValue("default");
                        curent_user_db.child("thumb_image").setValue("default");
                        mProgress.dismiss();

                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);


                    }
                }
            });

        }








    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();




    }

    public boolean validate(){

        boolean valid = true;
        String name = mNameField.getText().toString();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();



        if (name.isEmpty() || name.length()<3){
            mNameField.setError("at least 3 characters");
            valid=false;
        }else {
            mNameField.setError(null);
        }


            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                mEmailField.setError("enter a valid email address");
                valid = false;
            }else {
                mEmailField.setError(null);

            }


            if (password.isEmpty() || password.length()<6 || password.length() >10 ){
                mPasswordField.setError("between 4 and 10 alphanumeric characters");
                valid = false;
            }else {
                mPasswordField.setError(null);
            }
return valid;

        }

    }


