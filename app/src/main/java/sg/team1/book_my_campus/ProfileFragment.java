package sg.team1.book_my_campus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    String title = "Profile";
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


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

        Log.i(title, String.valueOf(firestore));

        // get values from intent that was passed to HomePage
        String myName = getActivity().getIntent().getStringExtra("name");
        String userId = getActivity().getIntent().getStringExtra("userId");
        String myEmail = getActivity().getIntent().getStringExtra("email");
        String myPassword = getActivity().getIntent().getStringExtra("password");

        // make a view in order to set variables to textviews and buttons
        this.inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);

        // declare variables for the elements in the layout
        Button EditProfileButton = inflatedView.findViewById(R.id.editProfileButton);
        TextView NameDisplay = inflatedView.findViewById(R.id.nameDisplay);
        TextView EmailDisplay = inflatedView.findViewById(R.id.emailDisplay);
        TextView logoutText = inflatedView.findViewById(R.id.textView3);

        NameDisplay.setText(myName);
        EmailDisplay.setText(myEmail);

        EditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(title, "Edit Profile clicked");

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.edit_profile,null);
                builder.setView(dialogView);
                builder.setCancelable(false);

                AlertDialog dialog = builder.create();
                dialog.show();

                // If "Cancel" button is pressed, close the dialog
                Button CancelButton = dialogView.findViewById(R.id.editprofileCancel);
                CancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.v(title, "Cancel button pressed");
                        dialog.dismiss();
                    }
                });

                // If "Change" button is pressed,
                Button ChangeButton = dialogView.findViewById(R.id.editprofileChange);
                ChangeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.v(title, "Change button pressed");

                        // On click, extract string data from EditText and declare them as variables
                        EditText NewNameInput = dialogView.findViewById(R.id.NewNameInput);
                        EditText NewEmailInput = dialogView.findViewById(R.id.NewEmailInput);
                        String NewName = NewNameInput.getText().toString();
                        String NewEmail = NewEmailInput.getText().toString();

                        // Data validation for inputs
                        if(TextUtils.isEmpty(NewName)){
                            Toast.makeText(getContext(), "Enter new name", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(TextUtils.isEmpty(NewEmail) || android.util.Patterns.EMAIL_ADDRESS.matcher(NewEmail).matches() == false){
                            Toast.makeText(getContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        // Updating email and name in firestore
                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                        CollectionReference usersCollection = firestore.collection("users");

                        String userId = firebaseAuth.getCurrentUser().getUid();
                        usersCollection.document(userId);

                        // Updating (adding new values into) current user
                        Map<String, Object> editMap = new HashMap<>();
                        editMap.put("Email", NewEmail);
                        editMap.put("Name", NewName);
                        usersCollection.document(userId).update(editMap);

                        // Changing the values separately in firebase authentication as well
                            //Here I get the current user
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(NewEmail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Details successfully updated!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(getContext(), "Unsuccessful update", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
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