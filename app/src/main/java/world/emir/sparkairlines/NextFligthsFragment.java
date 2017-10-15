package world.emir.sparkairlines;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class NextFligthsFragment extends Fragment {


    ImageButton popup_menu_btn;
    RecyclerView recyclerView;
    //DatabaseReference mDatabase;


    public NextFligthsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_next_fligths, container, false);


        //Intilaze recycler view

        recyclerView = (RecyclerView) rootView.findViewById(R.id.next_flights_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Intilaze popmenu image



        //Firebase

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Flights");

        FirebaseRecyclerAdapter<Flights,FlightViewHolder> adapter = new FirebaseRecyclerAdapter<Flights, FlightViewHolder>(
                Flights.class,
                R.layout.new_flights_single_row,
                FlightViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(FlightViewHolder viewHolder, Flights model, int position) {

                viewHolder.setTitle(model.getName_of_destination());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setAuthor(model.getAuthor());
                viewHolder.setThumbImage(getActivity(),model.getThumb_image());


            }
        };

        recyclerView.setAdapter(adapter);



        //Firebase get Name and Email


            //********


            return rootView;


    }








    }
