
package com.example.networktest;

        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Color;
        import android.graphics.Movie;
        import android.graphics.drawable.Drawable;
        import android.os.Build;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import androidx.annotation.DrawableRes;
        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.annotation.RequiresApi;

        import com.mashape.unirest.http.HttpResponse;
        import com.mashape.unirest.http.Unirest;
        import com.mashape.unirest.http.exceptions.UnirestException;
        import com.squareup.picasso.Picasso;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.io.InputStream;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.List;

public class Layout_Adapter extends ArrayAdapter {


    private String backgroundColor;



    public Layout_Adapter(@NonNull Context context, int resource, @NonNull List objects, String vBackgroundColor) {
        super(context, resource, objects);
        backgroundColor = vBackgroundColor;
        Log.v("Name","Games");



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
        Picasso.get().load("https://images.igdb.com/igdb/image/upload/t_thumb/co1ui1.jpg").fit().into(imageView);





        // Create Text View for Game Name and set text to it
        TextView gameNameView = (TextView) listItem.findViewById(R.id.game_name);
        gameNameView.setText(currentWords.getName());





        // Create Text View for Company Name and set text to it
        //TextView companyName = (TextView) listItem.findViewById(R.id.company_name);
        //companyName.setText(currentWords.getCompany());








        // Create Text View for Release Date and set text to it

        TextView releaseDate = (TextView) listItem.findViewById(R.id.release_date);
        releaseDate.setText(date);



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

    public static Drawable LoadImageFromWeb(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "IGDB");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}
