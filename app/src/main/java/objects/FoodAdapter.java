package objects;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;
import itp341.li.byron.nutrition.R;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<Food> {

    private Context c;

    private ArrayList<Food> foods;

    public FoodAdapter(Context c, int resId, ArrayList<Food> foods){
        super(c, resId, foods);
        this.c = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Food f = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_food ,parent, false);
        }

        //TODO: Example
        // Lookup view for data population
        TextView foodName = (TextView) convertView.findViewById(R.id.foodname_text);
        TextView calories = (TextView) convertView.findViewById(R.id.calorie_text);
        TextView servingSize = (TextView) convertView.findViewById(R.id.serving_text);
        TextView brandName = (TextView) convertView.findViewById(R.id.brandname_text);
        ImageView foodThumb = (ImageView) convertView.findViewById(R.id.image_list);
        // Populate the data into the template view using the data object
        foodName.setText(f.getFood_name());
        calories.setText(Integer.toString(f.getNf_calories()) + " cal");
        if (f.getBrand_name() != null){
            brandName.setText(f.getBrand_name());
        }
        else{
            brandName.setText(R.string.common);
        }
        if (f.getServing_unit() != null){
            servingSize.setText(f.getServing_qty() + " " + f.getServing_unit());
        }
        if (f.getImage() != null) {
            Picasso.with(c).load(f.getImage()).into(foodThumb);
        }
        // Return the completed view to render on screen
        return convertView;


    }


}
