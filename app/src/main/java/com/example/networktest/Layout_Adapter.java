
package com.example.networktest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Layout_Adapter extends ArrayAdapter {


    private String backgroundColor;



    public Layout_Adapter(@NonNull Context context, int resource, @NonNull List objects, String vBackgroundColor) {
        super(context, resource, objects);
        backgroundColor = vBackgroundColor;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_view,parent,false);




        // get the ITEM from the Games ArrayList at the current position and set it to currentWords variable
        Games currentWords = (Games) getItem(position);

        Calendar calendar = new Calendar();
        String date = calendar.getUnixToDate(currentWords.getReleaseDate());


        // Create Image View for Cover and set image to it
        ImageView imageView = listItem.findViewById(R.id.cover_image);
        String url = "https://images.igdb.com/igdb/image/upload/t_original/" + currentWords.getCoverURL() +".jpg";
        Picasso.with(getContext()).load(url).into(imageView);



        // Create Text View for Game Name and set text to it
        TextView gameNameView = (TextView) listItem.findViewById(R.id.game_name);
        gameNameView.setText(currentWords.getName());



        // Create Text View for Company Name and set text to it
        TextView companyName = (TextView) listItem.findViewById(R.id.company_name);
        companyName.setText(currentWords.getCompanyName());



        // Create Text View for Release Date and set text to it

        TextView releaseDate = (TextView) listItem.findViewById(R.id.release_date);
        releaseDate.setText(date);


        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameActivity = new Intent(getContext(), GameInfo.class);
                gameActivity.putExtra("GameID", currentWords.getName());
                gameActivity.putExtra("Company Name", currentWords.getCompanyName());
                getContext().startActivity(gameActivity);

            }
        });


        //Return listItem which is the finished layout for that list item to be displayed
        return listItem;
    }


    public void itemClicked() {

    }

}
