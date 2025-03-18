package com.example.hockey2;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.hockey2.adapter.MatchAdapter;
import com.example.hockey2.listener.OnItemClickListener;

import java.util.List;

public class admin extends Fragment {

    private EditText matchNameEditText, team1EditText, team2EditText, ftscEditText, stscEditText;
    private TextView matchToDelete;
    private Button addNewMatchButton;
    private Button delmatchButton;
    private Button backButton;
    private DatabaseHelper databaseHelper;
    private Match chosenMatchToDelete;

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
        matchToDelete = view.findViewById(R.id.matchToDelete);

        // Инициализация DatabaseHelper
        databaseHelper = new DatabaseHelper(getContext());

        backButton.setOnClickListener(v -> change(new HomeFragment()));

        delmatchButton.setOnClickListener(v -> {
            if (chosenMatchToDelete == null) {
                return;
            }

            int id = chosenMatchToDelete.getId();
            databaseHelper.deleteMatch(id);
        });

        matchToDelete.setOnClickListener(this::setupMatchesPopupWindow);

        // Установка слушателя на кнопку
        addNewMatchButton.setOnClickListener(v -> {
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

        });

        return view;
    }

    private void setupMatchesPopupWindow(View parent) {
        PopupWindow creatorList = createMatchesListPopup(parent);
        setupMatchesList(
                creatorList.getContentView(),
                databaseHelper.getAllMatches(),
                (v, d) -> {
                    Match match = (Match) d;
                    creatorList.dismiss();
                    matchToDelete.setText(match.getMatchName());
                    chosenMatchToDelete = match;
                }
        );
    }

    private void setupMatchesList(View v, List<Match> matches, OnItemClickListener listener) {
        RecyclerView matchRV = v.findViewById(R.id.matchesRV);

        MatchAdapter adapter = new MatchAdapter(matches);
        adapter.setOnItemClickListener(listener);
        matchRV.setAdapter(adapter);
        matchRV.setLayoutManager(new LinearLayoutManager(v.getContext()));
    }

    private PopupWindow createMatchesListPopup(View v) {
        View popupView = LayoutInflater.from(v.getContext())
                .inflate(R.layout.popup_matches_list, null);

        PopupWindow creatorList = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        creatorList.setElevation(20);
        creatorList.setOutsideTouchable(true);
        creatorList.showAsDropDown(v);

        return creatorList;
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
