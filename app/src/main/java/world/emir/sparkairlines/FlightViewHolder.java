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
    public Button booking_btn;
    public ImageButton pop_menu_btn;



    public FlightViewHolder(View itemView) {
        super(itemView);



        mView = itemView;
        pop_menu_btn = mView.findViewById(R.id.pop_menu_button);
        booking_btn = mView.findViewById(R.id.booking_btn);




        //Firebase get Name and Email




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
