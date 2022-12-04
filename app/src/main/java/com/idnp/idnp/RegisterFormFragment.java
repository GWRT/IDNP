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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterFormFragment extends Fragment {

    FirebaseFirestore mFireStore;
    FirebaseAuth mAuth;

    EditText username, email, password, confirmPassword;
    Button button_reg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_form, container, false);

        mFireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        username = view.findViewById(R.id.input_username);
        email = view.findViewById(R.id.input_email);
        password = view.findViewById(R.id.input_password);
        confirmPassword = view.findViewById(R.id.input_confirmPassword);

        button_reg = view.findViewById(R.id.register_btn);

        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr = username.getText().toString();
                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();
                String confPass = confirmPassword.getEditableText().toString();

                if(validateUsername(usernameStr) &&
                    validateEmail(emailStr) &&
                    validatePassword(passwordStr) &&
                    validateConfirmPassword(passwordStr, confPass))
                {
                    registerUser(usernameStr, emailStr, passwordStr);
                }
            }
        });

        return view;
    }

    private void registerUser(String username, String email, String pass){
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("name", username);
                map.put("email", email);
                map.put("password", pass);

                mFireStore.collection("User").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(getActivity(), AppActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error al Registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean validateUsername(String value){
        String noWhiteSpaces = "[A-Za-z0-9._-]";

        if(value.isEmpty()){
            username.setError("Rellene el campo vacio");
            return false;

        }else if(value.length() >= 15){
            username.setError("Nombre de usuario muy largo");
            return false;
        }
//        else if(!value.matches(noWhiteSpaces)) {
//         username.setError("No estan permitidos los espacio en blanco");
//        return false;
//         }
        else{
            username.setError(null);
            return true;
        }
    }

    private Boolean validateEmail(String value){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(value.isEmpty()){
            email.setError("Rellene el campo vacio");
            return false;
        }else if(!value.matches(emailPattern)) {
            email.setError("Correo electronico invalido");
            return false;
        }else{
            email.setError(null);
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
            password.setError("Rellene el campo vacio");
            return false;
        }
        else if(!value.matches(passwordPattern)) {
           password.setError("contraseña invalido");
            password.requestFocus();
           return false;
        }
        else{
            email.setError(null);
            return true;
        }
    }

    private Boolean validateConfirmPassword(String pass, String confPass){
        if(confPass.isEmpty()) {
            confirmPassword.setError("Rellene el campo vacio");
            return false;
        }
        if(!pass.equals(confPass)){
            confirmPassword.setError("No coinciden las contraseñas");
            confirmPassword.requestFocus();
            return false;
        }else{
            confirmPassword.setError(null);
            return true;
        }
    }
}