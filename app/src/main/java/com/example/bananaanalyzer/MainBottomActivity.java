package com.example.bananaanalyzer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bananaanalyzer.AnalyzeModule.AnalyzeMain;
import com.example.bananaanalyzer.HistoryModule.HistoryMain;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainBottomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_analyze:
                    startActivity(new Intent(MainBottomActivity.this,
                            AnalyzeMain.class));
                    return false;
                case R.id.nav_history:
                    startActivity(new Intent(MainBottomActivity.this,
                            HistoryMain.class));
                    return false;
            }
            return false;
        }
    };
}
