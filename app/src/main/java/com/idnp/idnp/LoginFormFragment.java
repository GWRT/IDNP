package com.idnp.idnp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFormFragment extends Fragment {

    RegisterFormFragment registerFormFragment;
    ForgotPassFormFragment forgotPassFormFragment;
    FirebaseAuth mAuth;

    EditText etEmail, etPassword;

    Button btnLogin,btnRegistro;
    TextView forgotPass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_form, container, false);

        //Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        //Fragments
        registerFormFragment = new RegisterFormFragment();
        forgotPassFormFragment = new ForgotPassFormFragment();

        //form
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);

        btnLogin = view.findViewById(R.id.btnLogin);
        btnRegistro = view.findViewById(R.id.btnRegistro);
        forgotPass = view.findViewById(R.id.forgotPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailUser = etEmail.getText().toString();
                String passUser = etPassword.getText().toString();

                if(validateEmail(emailUser) && validatePassword(passUser)){
                    loginUser(emailUser, passUser);
                }
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, registerFormFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, forgotPassFormFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private Boolean validateEmail(String value){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(value.isEmpty()){
            etEmail.setError("Rellene el campo vacio");
            return false;
        }else if(!value.matches(emailPattern)) {
            etEmail.setError("Correo electronico invalido");
            return false;
        }else{
            etEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(String value){
        String passwordPattern = "^"+
                "(?=.[a-zA-Z0-9])" +   //cualquier caracter
                "(?=.\\d)[A-Za-z\\d]" +
                "{4,}" +               //mas de 4 caracteres
                "$";

        if(value.isEmpty()){
            etPassword.setError("Rellene el campo vacio");
            return false;
        }
        else if(!value.matches(passwordPattern)) {
            etPassword.setError("contraseña invalido");
            etPassword.requestFocus();
        return false;
        }
        else{
            etPassword.setError(null);
            return true;
        }
    }

    private void loginUser(String email, String pass){
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Bienvenido", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(),AppActivity.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Fallo en el login", Toast.LENGTH_SHORT).show();
            }
        });
    }

}