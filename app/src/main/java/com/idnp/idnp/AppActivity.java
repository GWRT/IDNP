package com.idnp.idnp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AppActivity extends AppCompatActivity {

    private Button btnSingOut;
    private MapsFragment mapsFragment;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        mAuth = FirebaseAuth.getInstance();
        btnSingOut = findViewById(R.id.singOut);

        mapsFragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, mapsFragment).commit();

        btnSingOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(AppActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}