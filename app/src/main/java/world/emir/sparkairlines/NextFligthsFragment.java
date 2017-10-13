package world.emir.sparkairlines;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class NextFligthsFragment extends Fragment {


    ImageButton popup_menu_btn;


    public NextFligthsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_next_fligths, container, false);

        popup_menu_btn = (ImageButton) rootView.findViewById(R.id.pop_menu_btn);

        //Firebase get Name and Email


        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (mCurrentUser != null) {

            String current_uid = mCurrentUser.getUid();

            DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String role = dataSnapshot.child("role").getValue().toString(); //Get role information

                    if (role.equals("member")) {

                        Log.i("Status", role);

                    } else {

                        popup_menu_btn.setVisibility(View.VISIBLE);

                        popup_menu_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                PopupMenu popup = new PopupMenu(getActivity(), popup_menu_btn);
                                //Inflating the Popup using xml file
                                popup.getMenuInflater().inflate(R.menu.popupp_menu1, popup.getMenu());

                                //registering popup with OnMenuItemClickListener
                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    public boolean onMenuItemClick(MenuItem item) {

                                        int id = item.getItemId();
                                        //noinspection SimplifiableIfStatement
                                        if (id == R.id.add_biljesku) {

                                            Toast.makeText(getActivity(), "Add trip description", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getActivity(), AddNoteActiviy.class);
                                            getActivity().startActivity(i);

                                            return true;
                                        } else if (id == R.id.see_biljesku) {

                                            Toast.makeText(getActivity(), "See trip description", Toast.LENGTH_SHORT).show();
                                            return true;
                                        }


                                        return true;
                                    }
                                });

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


            //********


            return rootView;




    }
}
