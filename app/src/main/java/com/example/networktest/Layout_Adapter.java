
package com.example.networktest;

        import android.content.Context;
        import android.graphics.Color;
        import android.graphics.Movie;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.DrawableRes;
        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;

        import java.util.ArrayList;
        import java.util.List;

public class Layout_Adapter extends ArrayAdapter {


    private String backgroundColor;



    public Layout_Adapter(@NonNull Context context, int resource, @NonNull List objects, String vBackgroundColor) {
        super(context, resource, objects);
        backgroundColor = vBackgroundColor;
        Log.v("Name","Games");
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_view,parent,false);




        // get the ITEM from the Games ArrayList at the current position and set it to currentWords variable
        Games currentWords = (Games) getItem(position);




        // Create Image View for Cover and set image to it




        // Create Text View for Game Name and set text to it
        TextView gameNameView = (TextView) listItem.findViewById(R.id.game_name);
        gameNameView.setText(currentWords.getName());

        Log.v("Text View"," " + gameNameView);




        // Create Text View for Company Name and set text to it



        //Create Image View for icon and set image to it
        //ImageView mImageView = (ImageView) listItem.findViewById(R.id.listView_image);

        //If given resource ID is 0 then delete the ImageView from XML
        /*if(currentWords.getResourceId() == 0) {
            mImageView.setImageResource(R.drawable.family_daughter);
            mImageView.setVisibility(View.INVISIBLE);
            mImageView.getLayoutParams().width = 0;
        } else {
            mImageView.setImageResource(currentWords.getResourceId());
        }*/


        //Set Background Colors
       /* mImageView.setBackgroundColor(Color.parseColor("#E4E8BA"));
        ViewPager_Activity vp = new ViewPager_Activity();
        englishWordView.setBackgroundColor(Color.parseColor(backgroundColor));
        miwokWordView.setBackgroundColor(Color.parseColor(backgroundColor));*/



        //Return listItem which is the finished layout for that list item to be displayed
        return listItem;
    }

}
