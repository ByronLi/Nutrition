package itp341.li.byron.nutrition;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import objects.DataContainer;
import objects.Food;
import objects.FoodAdapter;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Search extends Activity {

    private ListView lv;
    DataContainer outerDC;
    FoodAdapter arrayAdapter;
    ArrayList<Food> concatList = new ArrayList<Food>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        lv = (ListView) findViewById(R.id.list);

        arrayAdapter = new FoodAdapter(this, android.R.layout.simple_list_item_1, concatList);

        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) findViewById(R.id.search_bar);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(false);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
            {
//                ArrayList<Food> concatList = outerDC.getBranded();
//                concatList.addAll(outerDC.getCommon());

                Food f = concatList.get(position);

                //Layout stuff
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_window, null);
                final PopupWindow mPopupWindow = new PopupWindow(popupView, 280, 450);
                mPopupWindow.setElevation(5.0f);

                //View Declarations
                ImageButton exitButton = (ImageButton) popupView.findViewById(R.id.popup_exit_button);
                TextView foodName = (TextView) popupView.findViewById(R.id.popup_food_name);
                TextView calories = (TextView) popupView.findViewById(R.id.popup_food_cal);
                TextView brandName = (TextView) popupView.findViewById(R.id.popup_brand_name);
                TextView servingText = (TextView) popupView.findViewById(R.id.popup_food_serving);
                ImageView foodImage = (ImageView) popupView.findViewById(R.id.popup_food_image);
                Button addFood = (Button) popupView.findViewById(R.id.popup_add_food_button);

                //Set texts
                foodName.setText(f.getFood_name().substring(0,1).toUpperCase()+f.getFood_name().substring(1));
                calories.setText(Integer.toString(f.getNf_calories()));
                if (f.getBrand_name() != null){
                    brandName.setText(f.getBrand_name());
                }
                else{
                    brandName.setText("");
                }
                servingText.setText(Double.toString(f.getServing_qty()) + " " + f.getServing_unit());
                Picasso.with(getApplicationContext()).load(f.getImage()).into(foodImage);


                addFood.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase fb = FirebaseDatabase.getInstance();

                    }

                });

                exitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopupWindow.dismiss();
                    }
                });

                Toast.makeText(getApplicationContext(), "Food Selected : "+ f.getFood_name(),   Toast.LENGTH_LONG).show();
            }
        });


        handleIntent(getIntent());

        // Get the intent, verify the action and get the query


    }

    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }


    private void handleIntent(Intent intent){

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println(query);
            System.out.print("searching for");
            doMySearch(query);
        }
    }

    private void doMySearch(String query){

       new HttpAsyncTask().execute(query);

    }

    private void updateList(DataContainer result){
        concatList.clear();
        concatList.addAll(result.getBranded());
        concatList.addAll(result.getCommon());

        lv.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        outerDC = result;
        arrayAdapter.notifyDataSetChanged();
        //finish();
    }

    private static DataContainer GET(String query){

        DataContainer dc = null;
        String result = "";
        try {

            URL myUrl = new URL("https://trackapi.nutritionix.com/v2/search/instant?query=" + query);

            // create HttpClient
            HttpURLConnection http = (HttpURLConnection) myUrl.openConnection();

            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("x-app-id", "0f09517b");
            http.setRequestProperty("x-app-key", "e227a02701d2032face77dc4aa579e17");

            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));

            Gson gson = new Gson();
            dc = gson.fromJson(in, DataContainer.class);
            System.out.println(dc.getBranded().get(0).getFood_name());

            if(in != null){
                //TODO:
            }

            else {
                result = "Read Error!";
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return dc;


    }

    private class HttpAsyncTask extends AsyncTask<String, Void, DataContainer>{
        @Override
        protected DataContainer doInBackground(String... query) {

            return GET(query[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(DataContainer result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            updateList(result);

            //System.out.print(result);
        }
    }
}
