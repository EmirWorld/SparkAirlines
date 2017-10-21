package world.emir.sparkairlines;

import android.content.Context;
import android.content.Intent;
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
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by phpwizz on 10/14/17.
 */

public class FlightViewHolder extends RecyclerView.ViewHolder{

    View mView;
    Context context;


    public FlightViewHolder(View itemView) {
        super(itemView);



        mView = itemView;
        final ImageButton popup_menu_btn = mView.findViewById(R.id.pop_menu_button);

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
                            public void onClick(final View view) {

                                PopupMenu popup = new PopupMenu(view.getContext(), popup_menu_btn);
                                //Inflating the Popup using xml file
                                popup.getMenuInflater().inflate(R.menu.popupp_menu1, popup.getMenu());

                                //registering popup with OnMenuItemClickListener
                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    public boolean onMenuItemClick(MenuItem item) {

                                        int id = item.getItemId();
                                        //noinspection SimplifiableIfStatement
                                        if (id == R.id.add_biljesku) {

                                            Toast.makeText(view.getContext(), "Add trip description", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(view.getContext(), AddNoteActiviy.class);
                                            view.getContext().startActivity(i);

                                            return true;
                                        } else if (id == R.id.see_biljesku) {

                                            Toast.makeText(view.getContext(), "See trip description", Toast.LENGTH_SHORT).show();
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




    }

    public void  setFirstDestination(String firstDestination){

        TextView sFirstDestination = (TextView) mView.findViewById(R.id.first_destination_txt);
        sFirstDestination.setText(firstDestination);



    }
    public void setSecondDestination(String secondDestination) {

        TextView sSecondDestination = (TextView) mView.findViewById(R.id.second_destination_txt);
        sSecondDestination.setText(secondDestination);
    }

    public void setDate (String date){
        TextView sDate = (TextView) mView.findViewById(R.id.date);
        sDate.setText(date);
    }





    public void setAuthor (String author){

        TextView author_of_flight = mView.findViewById(R.id.author);
        author_of_flight.setText(author);
    }


    public void setPrice (String price){
        TextView sPrice = mView.findViewById(R.id.price_of_flight);
        sPrice.setText(price);
    }

    public void setTime (String time){
        TextView sTime = mView.findViewById(R.id.time);
        sTime.setText(time);
    }




}
