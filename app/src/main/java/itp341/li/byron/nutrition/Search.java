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
import android.view.View;
import android.widget.*;
import com.google.gson.Gson;
import objects.DataContainer;
import objects.Food;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Search extends Activity {

    private ListView lv;
    DataContainer outerDC;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        lv = (ListView) findViewById(R.id.list);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) findViewById(R.id.search_bar);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(false);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
            {
                Food f = outerDC.getCommon().get(position);
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
        ArrayList<String> temp = new ArrayList<String>();
        for (Food f : result.getCommon()){
            temp.add(f.getFood_name());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, temp);
        lv.setAdapter(arrayAdapter);
        outerDC = result;
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
