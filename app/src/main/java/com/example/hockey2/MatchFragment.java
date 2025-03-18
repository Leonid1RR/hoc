package com.example.hockey2;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MatchFragment extends Fragment {

    private Button button; // Кнопка для возврата на предыдущий фрагмент
    private TextView fcomsc, scomsc, t1, t2; // Поля для отображения данных
    private DatabaseHelper databaseHelper; // Класс для работы с базой данных

    // Конструктор фрагмента (по умолчанию пустой)
    public MatchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Здесь можно выполнить действия, которые нужны перед созданием интерфейса,
        // например, инициализацию переменных или настроек. Пока оставлено пустым.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Этап 1: Привязка разметки к фрагменту
        View view = inflater.inflate(R.layout.fragment_match, container, false);
        // inflater подключает файл XML разметки, чтобы мы могли работать с ним как с объектом.

        // Этап 2: Привязка текстовых элементов к их ID из XML-файла разметки
        fcomsc = view.findViewById(R.id.fcomsc); // Связываем с TextView для первого элемента
        scomsc = view.findViewById(R.id.scomsc); // Связываем с TextView для второго элемента
        t1 = view.findViewById(R.id.t1); // Пример для третьего TextView
        t2 = view.findViewById(R.id.t2); // Пример для четвёртого TextView

        // Этап 3: Создание экземпляра DatabaseHelper
        // Это класс, который ты создаёшь для работы с SQLite базой данных.
        databaseHelper = new DatabaseHelper(getContext());

        // Этап 4: Загрузка данных из базы данных и установка их в текстовые элементы
        loadMatchData();

        // Этап 5: Настройка кнопки "Назад"
        button = view.findViewById(R.id.back); // Привязываем кнопку из разметки
        button.setOnClickListener(v -> {
            // Переход на другой фрагмент (HomeFragment) при нажатии кнопки
            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrameLayout, new HomeFragment()); // Замена текущего фрагмента
            ft.commit(); // Применение изменений
        });

        // Возвращаем настроенную разметку
        return view;
    }

    private void loadMatchData() {
        // Этот метод отвечает за извлечение и отображение данных.

        // Пример извлечения количества голов команды 1 (из определённой строки)
        int team1Goals = databaseHelper.getTeam1GoalsFromRow(1); // Параметр "1" - это ID строки
        Log.e("MatchFragment", "Голы команды 1: " + team1Goals);

        // Извлечение количества голов команды 2
        int team2Goals = databaseHelper.getTeam2GoalsFromRow(1); // Параметр "1" - это ID строки
        Log.e("MatchFragment", "Голы команды 2: " + team2Goals);

        // Извлечение названия команды 1
        String team1Name = databaseHelper.getTeam1NameFromRow(1); // Параметр "1" - это ID строки
        Log.e("MatchFragment", "Название команды 1: " + team1Name);

        // Извлечение названия команды 2
        String team2Name = databaseHelper.getTeam2NameFromRow(1); // Параметр "1" - это ID строки
        Log.e("MatchFragment", "Название команды 2: " + team2Name);

        // Установка данных в TextView элементы
        fcomsc.setText(String.valueOf(team1Goals)); // Отображение голов команды 1
        scomsc.setText(String.valueOf(team2Goals)); // Отображение голов команды 2
        t1.setText(team1Name); // Отображение названия команды 1
        t2.setText(team2Name); // Отображение названия команды 2
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Здесь мы закрываем соединение с базой данных, чтобы избежать утечек памяти.
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
