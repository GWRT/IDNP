package com.idnp.idnp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppActivity extends AppCompatActivity {

    MapsFragment mapsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        mapsFragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, mapsFragment).commit();
    }
}