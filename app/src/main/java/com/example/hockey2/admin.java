package com.example.hockey2;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class admin extends Fragment {

    private EditText matchNameEditText, deletablematch, team1EditText, team2EditText, ftscEditText, stscEditText;
    private Button addNewMatchButton;
    private Button delmatchButton;
    private Button backButton;
    private DatabaseHelper databaseHelper;

    public admin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        // Инициализация EditText и кнопки
        matchNameEditText = view.findViewById(R.id.matchName);
        team1EditText = view.findViewById(R.id.team1);
        team2EditText = view.findViewById(R.id.team2);
        ftscEditText = view.findViewById(R.id.ftsc);
        stscEditText = view.findViewById(R.id.stsc);
        addNewMatchButton = view.findViewById(R.id.addNewMatch);
        delmatchButton = view.findViewById(R.id.delmatch);
        backButton = view.findViewById(R.id.backBtn);

        // Инициализация DatabaseHelper
        databaseHelper = new DatabaseHelper(getContext());

        backButton.setOnClickListener(v -> {
            change(new HomeFragment());
        });

        delmatchButton.setOnClickListener(v -> {
            EditText matchfordel = view.findViewById(R.id.editTextNumber);
            String text = matchfordel.getText().toString();
            int id = Integer.parseInt(text);

            databaseHelper.deleteMatch(id);
        });

        // Установка слушателя на кнопку
        addNewMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matchName = matchNameEditText.getText().toString();
                String team1 = team1EditText.getText().toString();
                String team2 = team2EditText.getText().toString();
                String ftsc = ftscEditText.getText().toString();
                String stsc = stscEditText.getText().toString();

                int firstTeamScore = ftsc.isEmpty() ? 0 : Integer.parseInt(ftsc);
                int secondTeamScore = stsc.isEmpty() ? 0 : Integer.parseInt(stsc);

                // Добавление данных в БД
                DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
                SQLiteDatabase db = databaseHelper.getReadableDatabase();
                databaseHelper.addMatch(db,matchName,team1,firstTeamScore,null,team2,secondTeamScore,null);

            }
        });

        return view;
    }



    public void change(Fragment f) {
        // Переход на новый фрагмент
        if (getActivity() != null) {
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrameLayout, f); // Заменить фрагмент в контейнере с ID mainFrameLayout
            ft.commit();
        }
    }
}
