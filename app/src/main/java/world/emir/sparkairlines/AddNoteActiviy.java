package world.emir.sparkairlines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddNoteActiviy extends AppCompatActivity {

    private EditText name_of_note;
   private  EditText note;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_activiy);

        name_of_note = (EditText) findViewById(R.id.ed_note_name);
        note = (EditText) findViewById(R.id.ed_note);
        Button add_btn = (Button) findViewById(R.id.add_button);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Flights");

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_note();

            }
        });
    }

    private void create_note() {



        //Sleepy code till CardView is finished then it should get every post id and add it this childs!



       /* final String note_name = name_of_note.getText().toString();
        final String note_body = note.getText().toString();



        if (!TextUtils.isEmpty(note_name) && !TextUtils.isEmpty(note_body)) {



            String flight_id = UUID.randomUUID().toString();
            DatabaseReference current_flights_db = mDatabase.child(flight_id);

            current_flights_db.child("note_name").setValue(note_name);
            current_flights_db.child("note").setValue(note_body);



            Toast.makeText(this,"Success!",Toast.LENGTH_LONG).show();


            Intent mainIntent = new Intent(AddNoteActiviy.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);


        }else {
            Toast.makeText(AddNoteActiviy.this,"Check inputs",Toast.LENGTH_SHORT).show();
        }
*/
    }


}

