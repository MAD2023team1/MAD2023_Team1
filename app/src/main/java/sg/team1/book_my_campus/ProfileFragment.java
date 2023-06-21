package sg.team1.book_my_campus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View inflatedView;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String myName = getActivity().getIntent().getStringExtra("name");
        String userId = getActivity().getIntent().getStringExtra("userId");
        String myEmail = getActivity().getIntent().getStringExtra("email");
        String myPassword = getActivity().getIntent().getStringExtra("password");

        this.inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);
        Button EditProfileButton = inflatedView.findViewById(R.id.editProfileButton);
        TextView NameDisplay = inflatedView.findViewById(R.id.nameDisplay);
        TextView EmailDisplay = inflatedView.findViewById(R.id.emailDisplay);
        TextView logoutText = inflatedView.findViewById(R.id.textView3);

        NameDisplay.setText(myName);
        EmailDisplay.setText(myEmail);

        EditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePage ParentActivity = (HomePage) getActivity();
                //
                // ParentActivity.switchToEditProfile(EditProfileFragment.newInstance(mParam1, mParam2));
            }
        });
        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out the current user
                firebaseAuth.signOut();

                Toast.makeText(getActivity(),"Log out was successful", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(myIntent);
                getActivity().finish();
            }
        });

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_profile, container, false);
        return inflatedView;



    }
}