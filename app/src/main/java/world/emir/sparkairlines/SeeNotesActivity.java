package world.emir.sparkairlines;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SeeNotesActivity extends AppCompatActivity {

    TextView note_names,note_bodys;
    Button update_btn,remove_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_notes);

        //Intilaze view

        note_names = (TextView) findViewById(R.id.note_name_view);
        note_bodys = (TextView) findViewById(R.id.notes_body);
        update_btn = (Button) findViewById(R.id.update_note_btn);
        remove_btn = (Button) findViewById(R.id.remove_note_btn);


        //Retreving data


        final String value = getIntent().getExtras().getString("KEY");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Flights/"+value);


        DatabaseReference current_flights_db = mDatabase.child(value);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

           if (dataSnapshot.child("note").exists() && dataSnapshot.child("note_name").exists() ) {

               String note_name = dataSnapshot.child("note_name").getValue().toString();
               String note_body = dataSnapshot.child("note").getValue().toString();

               note_names.setText(note_name);
               note_bodys.setText(note_body);
           }
           else{

               Toast.makeText(SeeNotesActivity.this, "No note found!", Toast.LENGTH_SHORT).show();
               finish();

           }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create Database ref

                AlertDialog.Builder builder = new AlertDialog.Builder(SeeNotesActivity.this);

                builder.setTitle("Remove this note!");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(final DialogInterface dialog, int which) {


                        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Flights/"+value);


                        mDatabase.child("note_name").removeValue();
                        mDatabase.child("note").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                               Toast.makeText(SeeNotesActivity.this,"Removed",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });


                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();




            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            Intent intent = new Intent(SeeNotesActivity.this,UpdateNoteActivity.class);

                intent.putExtra("KEY", value);
                startActivity(intent);
                SeeNotesActivity.this.startActivity(intent);


            }
        });





    }
}


