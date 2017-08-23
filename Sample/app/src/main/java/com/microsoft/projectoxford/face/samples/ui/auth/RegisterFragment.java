package com.microsoft.projectoxford.face.samples.ui.auth;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.microsoft.projectoxford.face.samples.R;
import com.microsoft.projectoxford.face.samples.helper.db.DbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private DbHelper mDbHelper;

    @BindView(R.id.et_first_name)
    EditText mEtFirstName;

    @BindView(R.id.et_last_name)
    EditText mEtLastName;

    @BindView(R.id.et_dob)
    EditText mEtDOB;

    @BindView(R.id.et_email)
    EditText mEtEmail;

    @BindView(R.id.et_password)
    EditText mEtPassword;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_register,container,false);

        Unbinder bind = ButterKnife.bind(this, view);//initialize library

        mDbHelper = new DbHelper(getContext());

        return view;
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick(){
    registerUser();

    }

    private void registerUser(){
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();
        String firstName = mEtFirstName.getText().toString();
        String lastName = mEtLastName.getText().toString();
        String dob = mEtDOB.getText().toString();


        getContentValues(firstName, lastName, dob, email, password);
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        SQLiteDatabase readableDatabase = mDbHelper.getReadableDatabase();

        String query = " SELECT " + DbHelper.COLUMN_USER_EMAIL +
                " FROM " + DbHelper.TABLE_NAME + " WHERE " + DbHelper.COLUMN_USER_EMAIL + " = "
                + mEtEmail.getText().toString();
        Cursor cursor = readableDatabase.rawQuery(query,null);


        if (cursor.getCount() > 0) {

            Toast.makeText(getActivity()," The user already exists, please try another username. ", Toast.LENGTH_SHORT).show();
        }
        else {

            long i = database.insert(DbHelper.TABLE_NAME, null, getContentValues(firstName, lastName, dob, email, password));
            Log.d("myTag", "registerUser: " + i);

        }

        mDbHelper.close();
    }


    private ContentValues getContentValues(String firstName, String lastName, String dob, String email, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.COLUMN_USER_FIRST_NAME,email);
        contentValues.put(DbHelper.COLUMN_USER_LAST_NAME,email);
        contentValues.put(DbHelper.COLUMN_USER_DOB,email);
        contentValues.put(DbHelper.COLUMN_USER_EMAIL,email);
        contentValues.put(DbHelper.COLUMN_USER_PASSWORD,password);
        return contentValues;
    }

}
