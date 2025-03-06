package com.example.hockey2;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        //databaseHelper.addMatch("Game 1", "Team A", 3, "Info about Team A", "Team B", 2, "Info about Team B");
        //atabaseHelper.addMatch("Game 1", "Team A", 3, "Info about Team A", "Team B", 2, "Info about Team B");
        //databaseHelper.addMatch("Game 1", "Team A", 3, "Info about Team A", "Team B", 2, "Info about Team B");

        change(new HomeFragment());
    }

    public void change(Fragment f){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainFrameLayout,f);
        ft.commit();
    }
}