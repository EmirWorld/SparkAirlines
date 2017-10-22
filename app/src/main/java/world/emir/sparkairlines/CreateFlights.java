package world.emir.sparkairlines;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static android.os.SystemClock.sleep;

public class CreateFlights extends AppCompatActivity {




    private EditText name_of_first_destination;
    private EditText name_of_second_destination;


    private EditText time_of_flight;
    public EditText date_of_flight;

    private EditText flight_price;
    private Button btn_submit;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flights);

        name_of_first_destination = (EditText) findViewById(R.id.name_of_first_destination);
        name_of_second_destination = (EditText) findViewById(R.id.name_of_second_destination);

        time_of_flight = (EditText) findViewById(R.id.ed_time);
        date_of_flight = (EditText) findViewById(R.id.ed_date);

        flight_price = (EditText) findViewById(R.id.ed_price);
        btn_submit = (Button) findViewById(R.id.submit_btn);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Flights");
        date_of_flight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    DateDialog dialog =new DateDialog(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft,"DatePicker");
                }



            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_flights();

            }
        });
    }

    private void create_flights() {
        final String name2 = name_of_first_destination.getText().toString();
        final String name1 = name_of_second_destination.getText().toString();
         final String date = date_of_flight.getText().toString();
        final String time = time_of_flight.getText().toString();


        final String price = flight_price.getText().toString();


        if (!TextUtils.isEmpty(name1) && !TextUtils.isEmpty(name2)
                && !TextUtils.isEmpty(time)&&!TextUtils.isEmpty(date)
                && !TextUtils.isEmpty(price)) {


            String time_published = getDateTime().toString();


            String flight_id = UUID.randomUUID().toString();
            DatabaseReference current_flights_db = mDatabase.child(flight_id);

            current_flights_db.child("first_destination").setValue(name1);
            current_flights_db.child("second_destination").setValue(name2);
            current_flights_db.child("time").setValue(time);
            current_flights_db.child("date").setValue(date);
            current_flights_db.child("price").setValue(price + "$");

            current_flights_db.child("date_of_fligh_created").setValue(time_published);
            current_flights_db.child("author").setValue("Admin");

            String succesMessage = "New flight for " + name1 + " - " + name2 + " whit price of " + price + " $ ,is succesufly created in " + time_published;

            Toast.makeText(this, succesMessage, Toast.LENGTH_LONG).show();


            Intent mainIntent = new Intent(CreateFlights.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);


        } else {
            Toast.makeText(CreateFlights.this, "Check inputs", Toast.LENGTH_SHORT).show();
        }

    }


    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }





}




