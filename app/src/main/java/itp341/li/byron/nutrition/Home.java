package itp341.li.byron.nutrition;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements Profile.OnFragmentInteractionListener{

    private TextView mTextMessage;
    private SearchView searchView;

    Fragment profileFragment;
    Fragment searchFragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    FirebaseAuth firebaseAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    searchView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.hide(profileFragment);
                    fragmentTransaction.commit();

                    return true;
                case R.id.navigation_search:
                    searchView.setVisibility(View.VISIBLE);
                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.hide(profileFragment);
                    fragmentTransaction.commit();

                    return true;
                case R.id.navigation_profile:
                    searchView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.show(profileFragment);
                    fragmentTransaction.commit();


                    return true;
                case R.id.navigation_favorites:
                    searchView.setVisibility(View.INVISIBLE);
                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.hide(profileFragment);
                    fragmentTransaction.commit();

                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();

        fm = getFragmentManager();
        profileFragment = fm.findFragmentById(R.id.profileFragmentLayout);
        if (profileFragment == null && firebaseAuth.getCurrentUser() != null){
            profileFragment = Profile.newInstance(firebaseAuth.getCurrentUser().getUid());
            Fade fade = new Fade();
            fade.setDuration(500);
            profileFragment.setEnterTransition(fade);
            profileFragment.setExitTransition(fade);
        }

//        searchFragment = fm.findFragmentById(R.id.searchFragmentLayout);
//        if (searchFragment == null){
//            searchFragment = SearchContainer.newInstance();
//            Fade fade = new Fade();
//            fade.setDuration(500);
//            searchFragment.setExitTransition(fade);
//        }

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.home_search_bar);
        //TODO:comment out
        searchView.setVisibility(View.VISIBLE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, profileFragment);
        fragmentTransaction.hide(profileFragment);
        fragmentTransaction.commit();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

}
