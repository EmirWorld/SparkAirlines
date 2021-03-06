package world.emir.sparkairlines;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopOfferFragment extends Fragment {

    RecyclerView recyclerView;


    public TopOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_top_offer, container, false);

        //Intilaze recycler view

        recyclerView = (RecyclerView) rootView.findViewById(R.id.top_flights_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Intilaze popmenu image



        //Firebase



        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Flights");


        FirebaseRecyclerAdapter<Flights,FlightViewHolder> adapter = new FirebaseRecyclerAdapter<Flights, FlightViewHolder>(
                Flights.class,
                R.layout.new_flights_single_row,
                FlightViewHolder.class,
                mDatabase.orderByChild("date").startAt("2017-01-01").endAt(getCurrentTimeStamp())

        ) {
            @Override
            protected void populateViewHolder(final FlightViewHolder viewHolder, Flights model, int position) {

                final String flight_key = getRef(position).getKey();



                viewHolder.setTime(model.getTime());

                viewHolder.setPrice(model.getPrice());
                viewHolder.setDate(model.getDate());
                viewHolder.setFirstDestination(model.getFirst_destination());
                viewHolder.setSecondDestination(model.getSecond_destination());
                viewHolder.setAuthor(model.getAuthor());



                FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (mCurrentUser != null ) {

                    String current_uid = mCurrentUser.getUid();

                    DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

                    mUserDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String role = dataSnapshot.child("role").getValue().toString(); //Get role information

                            if (role.equals("member")) {

                                viewHolder.pop_menu_btn.setVisibility(View.GONE);
                                viewHolder.booking_btn.setVisibility(View.GONE);


                            } else {

                                viewHolder.pop_menu_btn.setVisibility(View.GONE);
                                viewHolder.booking_btn.setVisibility(View.GONE);

                                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        PopupMenu popup = new PopupMenu(getActivity(), viewHolder.mView);
                                        //Inflating the Popup using xml file
                                        popup.getMenuInflater().inflate(R.menu.pop_menu2, popup.getMenu());


                                        //registering popup with OnMenuItemClickListener
                                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                            public boolean onMenuItemClick(MenuItem item) {

                                                int id = item.getItemId();
                                                //noinspection SimplifiableIfStatement

                                                if (id == R.id.delete_flight) {

                                                    //Create Database ref

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                                    builder.setTitle("Remove this flight!");
                                                    builder.setMessage("Are you sure?");

                                                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                                        public void onClick(final DialogInterface dialog, int which) {

                                                            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Deleting...", "Please wait...", true);

                                                            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Flights/"+flight_key);
                                                            mDatabase.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {


                                                                    progressDialog.dismiss();

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


                                                return true;
                                            }
                                        });

                                        popup.setGravity(Gravity.RIGHT);

                                        popup.show();//showing popup menu





                                    }
                                });



                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }



            }
        };

        recyclerView.setAdapter(adapter);



        //Firebase get Name and Email


        //********


        return rootView;

    }

    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


}
