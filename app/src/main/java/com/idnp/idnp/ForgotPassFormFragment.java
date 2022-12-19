package com.idnp.idnp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassFormFragment extends Fragment {

    //PasswordResetCodeFragment passwordResetCodeFragment;
    private EditText entryEmail;
    private Button resetPass;

    private String email="";

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forgot_pass_form, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(getContext());
        //passwordResetCodeFragment = new PasswordResetCodeFragment();
        entryEmail = view.findViewById(R.id.forgotPassword_entry);
        resetPass = view.findViewById(R.id.resetPassword);

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, passwordResetCodeFragment)
//                        .addToBackStack(null)
//                        .commit();

                email = entryEmail.getText().toString();
                if(!email.isEmpty()) {
                    mDialog.setMessage("Espere un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();

                }
                else
                    Toast.makeText(getContext(), "Debe ingresar un email", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void resetPassword(){
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "Se envio un correo correctamente", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "No se puedo enviar un correo", Toast.LENGTH_SHORT).show();
                }

                mDialog.dismiss();
            }
        });
    }
}