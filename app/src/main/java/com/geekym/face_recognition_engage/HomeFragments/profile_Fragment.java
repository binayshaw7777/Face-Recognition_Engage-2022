package com.geekym.face_recognition_engage.HomeFragments;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.geekym.face_recognition_engage.Authentication.SignIn_Activity;
import com.geekym.face_recognition_engage.HomeScreen;
import com.geekym.face_recognition_engage.R;
import com.geekym.face_recognition_engage.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class profile_Fragment extends Fragment {

    ImageButton Logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    TextView Name, Email, Uid, OrgName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_, container, false);

        Initialization(view);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Confirm Logout");
                alertDialogBuilder.setIcon(R.drawable.logo);
                alertDialogBuilder.setMessage("Do you really want to logout?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getContext(), SignIn_Activity.class));
                        getActivity().finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() { //To display user's data in card view
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users userprofile = snapshot.getValue(Users.class);
                if (userprofile != null) {
                    String name = userprofile.name;
                    String email = userprofile.email;
                    String uid = userprofile.id;
                    String org = userprofile.organization;

                    Name.setText("Name: " + name);
                    Email.setText("Email: " + email);
                    Uid.setText("ID: " + uid);
                    OrgName.setText("Organization: " + org);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void Initialization(View view) {
        Logout = view.findViewById(R.id.logout_button);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        Name = view.findViewById(R.id.name);
        Email = view.findViewById(R.id.email);
        Uid = view.findViewById(R.id.uid);
        OrgName = view.findViewById(R.id.orgname);
    }

    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        alertDialogBuilder.setTitle("Confirm Exit");
        alertDialogBuilder.setIcon(R.drawable.logo);
        alertDialogBuilder.setMessage("Do you really want to exit?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finishAffinity();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



//    private void Fetch() {
//        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Users userprofile = snapshot.getValue(Users.class);
//                Intent i = new Intent(getContext(), HomeScreen.class);
//                if (userprofile != null){
//                    String name = userprofile.name;
//                    String email = userprofile.email;
//                    String uid = userprofile.id;
//                    String org = userprofile.organization;
//
//                    Name.setText(name);
//                    Email.setText(email);
//                    Uid.setText(uid);
//                    OrgName.setText(org);
//
//                    Toast.makeText(getContext(), "Welcome "+name, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}