package sg.team1.book_my_campus;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String myName = getActivity().getIntent().getStringExtra("name");
    String userId = getActivity().getIntent().getStringExtra("userId");
    String myEmail = getActivity().getIntent().getStringExtra("email");
    String myPassword = getActivity().getIntent().getStringExtra("password");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View inflatedView;

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

        this.inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);
        Button EditProfileButton = inflatedView.findViewById(R.id.editProfileButton);
        TextView NameDisplay = inflatedView.findViewById(R.id.nameDisplay);
        TextView EmailDisplay = inflatedView.findViewById(R.id.emailDisplay);

        NameDisplay.setText(myName);
        EmailDisplay.setText(myEmail);

        EditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfile();
            }
        });

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_profile, container, false);
        return inflatedView;
    }

    private void showEditProfile() {
        // Inflate alert
        View dialogView = getLayoutInflater().inflate(R.layout.edit_profile, null);

        // Declaring variables
        EditText NewNameInput = dialogView.findViewById(R.id.NewNameInput);
        EditText NewEmailInput = dialogView.findViewById(R.id.NewEmailInput);
        Button CancelButton = dialogView.findViewById(R.id.editprofileCancel);
        Button ChangeButton = dialogView.findViewById(R.id.editprofileCancel);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog alert = builder.create();
        builder.setView(dialogView);

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        ChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NewName = NewNameInput.getText().toString();
                String NewEmail = NewEmailInput.getText().toString();




            }
        });

    }

}