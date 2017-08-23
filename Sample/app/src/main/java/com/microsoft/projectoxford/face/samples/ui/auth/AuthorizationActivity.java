package com.microsoft.projectoxford.face.samples.ui.auth;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.microsoft.projectoxford.face.samples.R;


public class AuthorizationActivity extends AppCompatActivity implements LoginFragment.OnLoginFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null)
            replaceFragment(new LoginFragment(), false);
    }

    @Override
    public void onRegisterClick() {
        replaceFragment(new RegisterFragment(),true);

    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }
}
