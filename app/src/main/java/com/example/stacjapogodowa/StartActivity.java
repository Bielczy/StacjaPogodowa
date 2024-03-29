package com.example.stacjapogodowa;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button showValuesButton = (Button)findViewById(R.id.btnTemperature);
        Button retriveData = (Button)findViewById(R.id.btnArchive);
        Button retriveFromFirebase = (Button)findViewById(R.id.btnRetrive);
        firebaseAuth = FirebaseAuth.getInstance();

        showValuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.buttons_container, new ExtractByDateFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        retriveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ArchiveActivity.class);
                startActivity(intent);
            }
        });

        retriveFromFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RetriveActivity.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout: {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }
}