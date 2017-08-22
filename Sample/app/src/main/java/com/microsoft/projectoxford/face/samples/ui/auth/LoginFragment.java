package com.microsoft.projectoxford.face.samples.ui.auth;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.microsoft.projectoxford.face.samples.R;

/**
 * Created by nellipc on 8/21/17.
 */

public class LoginFragment extends Fragment {
    private Button mBtnLogin;
    private Button mBtnRegister;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private OnLoginFragmentInteractionListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
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
        mBtnLogin = ((Button) view.findViewById(R.id.btn_login));
        mBtnRegister = ((Button) view.findViewById(R.id.btn_register));
        mEtEmail = ((EditText) view.findViewById(R.id.et_email));
        mEtPassword = ((EditText) view.findViewById(R.id.et_password));
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

    }

    //interface
    public interface OnLoginFragmentInteractionListener {
        void onRegisterClick();
    }
}
