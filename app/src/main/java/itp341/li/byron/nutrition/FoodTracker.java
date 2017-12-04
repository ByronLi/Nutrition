package itp341.li.byron.nutrition;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import objects.DataContainer;
import objects.Food;
import objects.FoodAdapter;

import java.util.ArrayList;

public class FoodTracker extends Activity {

    ListView trackerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker);
        trackerList = (ListView) findViewById(R.id.trackerList);
    }


    private void updateList(DataContainer result){


        FoodAdapter arrayAdapter = new FoodAdapter(this, android.R.layout.simple_list_item_1, result.getCommon());
        trackerList.setAdapter(arrayAdapter);
    }
}
