package com.example.huyha.activities.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.huyha.activities.drawer.DrawerActivity;
import com.example.huyha.models.localData.Database;
import com.example.huyva.karaoke.R;

public class WelcomeActivity extends AppCompatActivity {
    Database mDatabase = new Database();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mDatabase.setmContext(this);
        mDatabase.createDatabaseDirectory();
        mDatabase.copyDatabaseToDirectory();
        Intent intent = new Intent(this, DrawerActivity.class);
        startActivity(intent);
    }
}
