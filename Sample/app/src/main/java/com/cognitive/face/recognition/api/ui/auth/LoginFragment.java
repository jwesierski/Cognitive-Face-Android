package com.cognitive.face.recognition.api.ui.auth;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cognitive.face.recognition.api.helper.db.DbHelper;

/**
 * Created by nellipc on 8/21/17.
 */

public class LoginFragment extends Fragment {
    private Button mBtnLogin;
    private Button mBtnRegister;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private OnLoginFragmentInteractionListener mListener;
    private DbHelper mDbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(com.cognitive.face.recognition.api.R.layout.fragment_login, container, false);
        initViews(view);
        mDbHelper = new DbHelper(getContext());
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
                validateUser();
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRegisterClick();
            }
        });
    }

    private void validateUser() {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        //Cursor cursor = database.query (true, DbHelper.TABLE_NAME,new String [] {DbHelper.COLUMN_USER_EMAIL,DbHelper.COLUMN_USER_PASSWORD},
        //        null,null,null,null,null,null);

        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();


        if (email.matches("") || password.matches("")) {
            Toast.makeText(getActivity(),"Please input valid Email and Password.",Toast.LENGTH_SHORT).show();
            return;
        }

            String query = " SELECT " + DbHelper.COLUMN_USER_EMAIL + " , " + DbHelper.COLUMN_USER_PASSWORD +
                    " FROM " + DbHelper.TABLE_NAME + " WHERE " + DbHelper.COLUMN_USER_EMAIL + " = "
                    + email + " AND " + DbHelper.COLUMN_USER_PASSWORD + " = " + password;
            Cursor cursor = database.rawQuery(query, null);


        if (cursor.getCount() > 0) {
            //cursor.moveToFirst();
            //String name = cursor.getString(0);
            //String pass = cursor.getString(1);
            //Toast.makeText(getActivity(),"name = " + name + " , password = " + pass, Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity()," Success. ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity()," Email or password is incorrect. Please try again. ", Toast.LENGTH_SHORT).show();
        }

        mDbHelper.close();
    }

    //interface
    public interface OnLoginFragmentInteractionListener {
        void onRegisterClick();
    }

}
