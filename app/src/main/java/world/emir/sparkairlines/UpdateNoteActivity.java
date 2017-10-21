package world.emir.sparkairlines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateNoteActivity extends AppCompatActivity {

    EditText note_names,note_bodys;
    Button update_btn,close_bnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_notes);

        //Intilaze view

        note_names = (EditText) findViewById(R.id.note_name_ed_update);
        note_bodys = (EditText) findViewById(R.id.note_body_update);
        update_btn = (Button) findViewById(R.id.note_bnt_update);
        close_bnt = (Button) findViewById(R.id.close
        );

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_note();
            }
        });

        close_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UpdateNoteActivity.this,"One more and you will exit edit notes ",Toast.LENGTH_SHORT).show();
                killAcitvity();
            }
        });




    }


    public void update_note() {

        final String value = getIntent().getExtras().getString("KEY");

        Log.i("KEY",value);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Flights/" + value);


        final String note_name = note_names.getText().toString();
        final String note_body = note_bodys.getText().toString();



        if (!TextUtils.isEmpty(note_name) && !TextUtils.isEmpty(note_body)) {

            mDatabase.child("note_name").setValue(note_name);
            mDatabase.child("note").setValue(note_body);



            Toast.makeText(this,"Success!",Toast.LENGTH_LONG).show();


            Intent mainIntent = new Intent(UpdateNoteActivity.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);


        }else {
            Toast.makeText(UpdateNoteActivity.this,"Check inputs",Toast.LENGTH_SHORT).show();
        }

    }

    void killAcitvity(){

        finish();
    }


    }


