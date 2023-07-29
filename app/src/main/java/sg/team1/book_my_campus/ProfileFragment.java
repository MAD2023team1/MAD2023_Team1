package sg.team1.book_my_campus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private String title = "Profile";
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private ImageView profilePic;

    private ImageView camaraCircle;
    private Uri selectedImageUri;


    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;




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

    //Glide for the profile picture
    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView) {
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    //Storage reference in firebase for profile picture
    public static StorageReference getCurrentProfilePicStorageRef(String userId)
    {
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(userId);
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

        String myName = UserProfile.getName();
        String myEmail = UserProfile.getEmail();
        String userId = UserProfile.getUserId();
        String myPassword = UserProfile.getPassword();
        Uri myImage = UserProfile.getProfilePic();


        // make a view in order to set variables to textviews and buttons
        this.inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);

        // declare variables for the elements in the layout
        Button EditProfileButton = inflatedView.findViewById(R.id.editProfileButton);
        TextView NameDisplay = inflatedView.findViewById(R.id.nameDisplay);
        TextView EmailDisplay = inflatedView.findViewById(R.id.emailDisplay);
        TextView logoutText = inflatedView.findViewById(R.id.textView3);
        ImageView profileCard = inflatedView.findViewById(R.id.profileImage2);
        Switch switcher = inflatedView.findViewById(R.id.switch3);
        //to save state of app eg: App in light mode etc
        if(getActivity()!=null) {
            sharedPreferences = getActivity().getSharedPreferences("MODE", Context.MODE_PRIVATE);
            nightMode = sharedPreferences.getBoolean("night", false); //light mode default
            if (nightMode) {
                switcher.setChecked(true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            switcher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (nightMode) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        editor = sharedPreferences.edit();
                        editor.putBoolean("night", false);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor = sharedPreferences.edit();
                        editor.putBoolean("night", true);
                    }
                }
            });
        }

        NameDisplay.setText(myName);
        EmailDisplay.setText(myEmail);
        profileCard.setImageURI(myImage);
        // Check if the user has a profile picture
        if (myImage != null) {
            // If the user has a profile picture, load it using Glide
            Glide.with(getContext())
                    .load(myImage)
                    .into(profileCard);
        } else {
            // If the user does not have a profile picture, set the default image resource
            profileCard.setImageResource(R.drawable.baseline_person_24);
        }

        View dialogView = getLayoutInflater().inflate(R.layout.edit_profile, null);
        profilePic = dialogView.findViewById(R.id.editProfile);
        camaraCircle = dialogView.findViewById(R.id.editPicture);

        // Get the profile picture URL from Firestore and load it into the ImageView
        DocumentReference userDocumentRef = firestore.collection("users").document(userId);
        userDocumentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                DocumentSnapshot document = task.getResult();
                                                                if (document.exists()) {
                                                                    String profilePicUrl = document.getString("ProfilePicUrl");
                                                                    if (!TextUtils.isEmpty(profilePicUrl)) {
                                                                        // Load the profile picture into the ImageView using Glide
                                                                        setProfilePic(getContext(), Uri.parse(profilePicUrl), profilePic);
                                                                    }
                                                                } else {
                                                                    Log.d(title, "No such document");
                                                                }
                                                            } else {
                                                                Log.d(title, "get failed with ", task.getException());
                                                            }
                                                        }
                                                    });
        EditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(title, "Edit Profile clicked");

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setView(dialogView);
                builder.setCancelable(false);

                ViewGroup currentParent = (ViewGroup) dialogView.getParent();
                if (currentParent != null) {
                    currentParent.removeView(dialogView);
                }

                AlertDialog dialog = builder.create();
                dialog.show();

                // If "Cancel" button is pressed, close the dialog
                Button CancelButton = dialogView.findViewById(R.id.editprofileCancel);
                CancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.v(title, "Cancel button pressed");
                        // Check if the user has a profile picture
                        if (myImage != null) {
                            // If the user has a profile picture, load it using Glide
                            Glide.with(getContext())
                                    .load(myImage)
                                    .into(profilePic);
                        } else {
                            // If the user does not have a profile picture, set the default image resource
                            profilePic.setImageResource(R.drawable.baseline_person_24);
                        }
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
                        if (TextUtils.isEmpty(NewName)) {
                            Toast.makeText(getContext(), "Enter new name", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(NewEmail) || android.util.Patterns.EMAIL_ADDRESS.matcher(NewEmail).matches() == false) {
                            Toast.makeText(getContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        // Updating email,name and profile picture in firestore and firebase storage
                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                        CollectionReference usersCollection = firestore.collection("users");

                        // Getting the current user's ID
                        String userId = firebaseAuth.getCurrentUser().getUid();
                        usersCollection.document(userId);

                        // Get the StorageReference for the current user's profile picture
                        StorageReference profilePicRef = getCurrentProfilePicStorageRef(userId);

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

                        if (selectedImageUri != null) {
                            profilePicRef.putFile(selectedImageUri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            // Get the download URL of the uploaded image
                                            profilePicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri downloadUri) {
                                                    // Update the profile picture URL in Firestore
                                                    updateProfileInformationInFirestore(usersCollection.document(userId), downloadUri.toString(), NewName, NewEmail);
                                                    NameDisplay.setText(NewName);
                                                    EmailDisplay.setText(NewEmail);
                                                    profileCard.setImageURI(selectedImageUri);
                                                    UserProfile.setName(NewName);
                                                    UserProfile.setEmail(NewEmail);
                                                    UserProfile.setProfilePic(selectedImageUri);
                                                }
                                            });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle the error if the upload fails
                                            Toast.makeText(getContext(), "Failed to upload profile picture", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // If no new profile picture was selected, proceed to update other profile information
                            updateProfileInformationInFirestore(usersCollection.document(userId), null, NewName, NewEmail);
                            NameDisplay.setText(NewName);
                            EmailDisplay.setText(NewEmail);
                            UserProfile.setName(NewName);
                            UserProfile.setEmail(NewEmail);
                        }
                    }
                });

                //Image picker to allow camera or gallery photos
                camaraCircle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Use the ImagePicker library with the fragment's context
                        ImagePicker.with(ProfileFragment.this)
                                .cropSquare()
                                .compress(512)
                                .maxResultSize(512, 512)
                                .start(); // Pass the ActivityResultLauncher here
                    }
                });
            }
        });

        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out the current user
                firebaseAuth.signOut();

                Toast.makeText(getActivity(), "Log out was successful", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(myIntent);
                getActivity().finish();
            }
        });



        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_profile, container, false);
        return inflatedView;

    }

    // Updating (adding new values into) current user
    private void updateProfileInformationInFirestore(DocumentReference userDocument, String profilePicUrl, String newName, String newEmail) {
        // Create a map with the data to be updated in the document
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("Email", newEmail);
        updateMap.put("Name", newName);

        if (profilePicUrl != null) {
            updateMap.put("ProfilePicUrl", profilePicUrl);
        }

        // Update the document with the new data
        userDocument.update(updateMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Profile information update is successful
                        Toast.makeText(getContext(), "Profile information updated successfully!", Toast.LENGTH_SHORT).show();
                        Log.w(title, "success-profile picture Url: "+ profilePicUrl + profilePic);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error if updating profile information fails
                        Toast.makeText(getContext(), "Failed to update profile information", Toast.LENGTH_SHORT).show();
                        Log.w(title, "failed-profile picture Url: "+ profilePicUrl + profilePic);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data != null) {
            selectedImageUri = data.getData();
            setProfilePic(getContext(), selectedImageUri, profilePic);
        }
    }

}
