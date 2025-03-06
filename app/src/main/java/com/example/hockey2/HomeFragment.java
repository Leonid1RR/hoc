package com.example.hockey2;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private DatabaseHelper dbHelper;
    private Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        List<String> matchNames = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        matchNames = databaseHelper.getNameMatches();
        System.out.println(matchNames);
        button = view.findViewById(R.id.admin);
        button.setOnClickListener(v ->{
            change(new admin());
        });
        // Создание адаптера
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, matchNames);

        //  ---->  Toast.makeText(getContext(), matchNames.toString(), Toast.LENGTH_LONG).show();

        // Настройка ListView
        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, v, position, id) -> {
            // Переключение на новый фрагмент
            // Для этого создаем новый экземпляр MatchFragment
            change(new MatchFragment());
        });

        return view;
    }






    public void change(Fragment f) {
        // Переход на новый фрагмент
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.mainFrameLayout, f); // Заменить фрагмент в контейнере с ID mainFrameLayout
        ft.commit();
    }
}
