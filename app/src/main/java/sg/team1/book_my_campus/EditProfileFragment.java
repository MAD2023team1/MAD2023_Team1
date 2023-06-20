package sg.team1.book_my_campus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    String title = "Edit Profile";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View inflatedView;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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

        // create instance of firestore
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // get intent values from LoginPage to HomePage
        String myName = getActivity().getIntent().getStringExtra("name");
        String userId = getActivity().getIntent().getStringExtra("userId");
        String myEmail = getActivity().getIntent().getStringExtra("email");
        String myPassword = getActivity().getIntent().getStringExtra("password");

        // create a view so that i can use findViewById to get objects for EditText objects
        this.inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);
        EditText NewNameInput = inflatedView.findViewById(R.id.newNameInput);
        EditText NewEmailInput = inflatedView.findViewById(R.id.newEmailInput);

        // extracting values from editText for new name and email
        String NewName = String.valueOf(NewNameInput.getText());
        String NewEmail = String.valueOf(NewEmailInput.getText());

        Map<String, Object> user = new HashMap<>();
        user.put("Name", NewName);
        user.put("Email", NewEmail);
        user.put("Password", myPassword);

        firestore.collection("users").document(userId).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.v(title, "Update Successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(title, "Update failed");
                    }
                });

        // Inflate the layout for this fragment
        return inflatedView;
    }
}