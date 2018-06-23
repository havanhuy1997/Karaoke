package com.huyha.huyha.activities.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.huyha.huyha.activities.drawer.DrawerActivity;
import com.huyha.huyha.models.localData.Database;
import com.huyha.van.karaoke.R;

public class WelcomeActivity extends AppCompatActivity {
    private String TAG = "WelComeActivity";
    Database mDatabase = new Database();
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
        mDatabase.setmContext(getApplicationContext());
        mDatabase.createDatabaseDirectory();
        mDatabase.copyDatabaseToDirectory();
        intent = new Intent(this, DrawerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }
}
