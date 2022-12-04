package com.idnp.idnp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ForgotPassFormFragment extends Fragment {

    PasswordResetCodeFragment passwordResetCodeFragment;
    Button resetPass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forgot_pass_form, container, false);

        passwordResetCodeFragment = new PasswordResetCodeFragment();
        resetPass = view.findViewById(R.id.resetPassword);

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, passwordResetCodeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}