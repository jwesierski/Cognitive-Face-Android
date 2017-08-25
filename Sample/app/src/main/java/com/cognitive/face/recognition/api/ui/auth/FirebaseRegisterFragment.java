package com.cognitive.face.recognition.api.ui.auth;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.cognitive.face.recognition.api.data.model.User;
import com.cognitive.face.recognition.api.helper.db.DbHelper;
import com.cognitive.face.recognition.api.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirebaseRegisterFragment extends Fragment {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog mProgressDialog;


    @BindView(com.cognitive.face.recognition.api.R.id.et_first_name)
    EditText mEtFirstName;

    @BindView(com.cognitive.face.recognition.api.R.id.et_last_name)
    EditText mEtLastName;

    @BindView(com.cognitive.face.recognition.api.R.id.et_dob)
    EditText mEtDOB;

    @BindView(com.cognitive.face.recognition.api.R.id.et_email)
    EditText mEtEmail;

    @BindView(com.cognitive.face.recognition.api.R.id.et_password)
    EditText mEtPassword;

    public FirebaseRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(com.cognitive.face.recognition.api.R.layout.fragment_register, container, false);

        Unbinder bind = ButterKnife.bind(this, view);//initialize library

        mFirebaseAuth = FirebaseAuth.getInstance();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Please wait...");

        return view;
    }

    @OnClick(com.cognitive.face.recognition.api.R.id.btn_register)
    public void onRegisterClick() {
        mProgressDialog.show();
        registerUser();

    }

    private void registerUser() {
        final String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();
        final String firstName = mEtFirstName.getText().toString();
        final String lastName = mEtLastName.getText().toString();
        final String dob = mEtDOB.getText().toString();

        if (email.matches("") || password.matches("")) {
            Toast.makeText(getActivity(), "Please input valid Email and Password.", Toast.LENGTH_SHORT).show();
            return;
        }


        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mProgressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Registration is successful.", Toast.LENGTH_SHORT).show();
                    String userId = task.getResult().getUser().getUid();
                    saveUserInfo(new User(firstName, lastName, dob, email), userId);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialog.dismiss();
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void saveUserInfo(User user, String userId) {
        mDatabaseReference.child("users").child(userId).setValue(user);
    }

}
