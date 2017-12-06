package itp341.li.byron.nutrition;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import objects.User;
import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_UID = "userUID";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String muid;
    private SeekBar seekBar;
    private Button saveChanges;
    private TextView profileCalorieTextView;
    private int dailyCalorieGoal = 0;
    private String profileName;
    private TextView profileNameTV;
    private TextView profileEmailTV;
    private String userEmail;
    DatabaseReference userReference;

    private OnFragmentInteractionListener mListener;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param uid UID for current user;
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String uid) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_UID, uid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            muid = getArguments().getString(ARG_UID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        userReference = FirebaseDatabase.getInstance().getReference().child("Users").child((String) getArguments().get(ARG_UID));

        profileCalorieTextView = (TextView) v.findViewById(R.id.profile_seekbar_response);

        profileNameTV = (TextView) v.findViewById(R.id.profile_user_name);
        profileEmailTV = (TextView) v.findViewById(R.id.profile_user_email);


        seekBar = (SeekBar) v.findViewById(R.id.profile_calorie_chooser);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                dailyCalorieGoal = ((int) Math.round(i / 10.0)) * 10;
                seekBar.setProgress(dailyCalorieGoal);
                profileCalorieTextView.setText(dailyCalorieGoal + " Cal");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ValueEventListener userCalListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                dailyCalorieGoal = u.getDailyCalorieGoal();
                profileCalorieTextView.setText(Integer.toString(dailyCalorieGoal) + " Cal");
                seekBar.setProgress(dailyCalorieGoal);

                profileName = u.getName();
                userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                profileEmailTV.setText(userEmail);
                profileNameTV.setText(profileName);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userReference.addValueEventListener(userCalListener);

        saveChanges = (Button) v.findViewById(R.id.saveChanges_button);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
                userReference.child("dailyCalorieGoal").setValue(dailyCalorieGoal);
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
