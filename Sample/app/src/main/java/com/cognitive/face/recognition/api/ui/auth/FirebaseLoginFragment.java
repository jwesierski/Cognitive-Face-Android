package com.cognitive.face.recognition.api.ui.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cognitive.face.recognition.api.helper.db.DbHelper;
import com.cognitive.face.recognition.api.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by nellipc on 8/21/17.
 */

public class FirebaseLoginFragment extends Fragment {
    private Button mBtnLogin;
    private Button mBtnRegister;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private OnLoginFragmentInteractionListener mListener;
    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(com.cognitive.face.recognition.api.R.layout.fragment_login, container, false);
        initViews(view);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Please wait...");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = ((OnLoginFragmentInteractionListener) context);
        } catch (ClassCastException exception) {
            throw new ClassCastException(context.toString() + " must implement OnLoginFragmentInteractionListener");
        }
    }

    private void initViews(View view) {
        mBtnLogin = ((Button) view.findViewById(com.cognitive.face.recognition.api.R.id.btn_login));
        mBtnRegister = ((Button) view.findViewById(com.cognitive.face.recognition.api.R.id.btn_register));
        mEtEmail = ((EditText) view.findViewById(com.cognitive.face.recognition.api.R.id.et_email));
        mEtPassword = ((EditText) view.findViewById(com.cognitive.face.recognition.api.R.id.et_password));
        setInitListeners();
    }

    private void setInitListeners() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                loginUser();
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRegisterClick();
            }
        });
    }

    private void loginUser() {
        //Cursor cursor = database.query (true, DbHelper.TABLE_NAME,new String [] {DbHelper.COLUMN_USER_EMAIL,DbHelper.COLUMN_USER_PASSWORD},
        //        null,null,null,null,null,null);

        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

        if (email.matches("") || password.matches("")) {
            Toast.makeText(getActivity(),"Please input valid Email and Password.",Toast.LENGTH_SHORT).show();
            return;
        }

        mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                mProgressDialog.dismiss();

                if (task.isSuccessful()){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(),"Please input valid Email and Password.",Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//            }
//        });
//        thread.start();


    }

    //interface
    public interface OnLoginFragmentInteractionListener {
        void onRegisterClick();
    }

}
