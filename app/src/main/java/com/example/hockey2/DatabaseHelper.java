package com.example.hockey2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "hockey.db"; // Имя базы данных
    private static final int DATABASE_VERSION = 1; // Версия БД

    private static final String COLUMN_MATCH_NAME_MATCHES = "match_name";

    // SQL-запрос для создания таблицы
    private static final String CREATE_TABLE_MATCHES = "CREATE TABLE matches ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_MATCH_NAME_MATCHES + " TEXT, "
            + "team1_name TEXT, "
            + "team1_goals INTEGER DEFAULT 0, "
            + "team1_info TEXT, "
            + "team2_name TEXT, "
            + "team2_goals INTEGER DEFAULT 0, "
            + "team2_info TEXT"
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MATCHES); // Создаём таблицу
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS matches"); // Удаляем старую версию
        onCreate(db); // Создаём заново
    }

    // Метод для добавления матча
    public void addMatch(SQLiteDatabase db, String matchName, String team1Name, int team1Goals, String team1Info, String team2Name, int team2Goals, String team2Info) {
        ContentValues values = new ContentValues();
        values.put("match_name", matchName);
        values.put("team1_name", team1Name);
        values.put("team1_goals", team1Goals);
        values.put("team1_info", team1Info);
        values.put("team2_name", team2Name);
        values.put("team2_goals", team2Goals);
        values.put("team2_info", team2Info);

        db.insert("matches", null, values);
    }

    // Перегруженный метод для добавления матча, который можно вызывать извне
    public void addMatch(String matchName, String team1Name, int team1Goals, String team1Info, String team2Name, int team2Goals, String team2Info) {
        SQLiteDatabase db = this.getWritableDatabase();
        addMatch(db, matchName, team1Name, team1Goals, team1Info, team2Name, team2Goals, team2Info);
        db.close();
    }

    public Match getMatch(int matchId) {
        Match match = null;
        SQLiteDatabase db = this.getReadableDatabase(); // Открываем базу данных для чтения

        String query = "SELECT * FROM matches WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(matchId)}); // Получаем данные по ID

        if (cursor != null && cursor.moveToFirst()) {
            match = new Match();
            match.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            match.setMatchName(cursor.getString(cursor.getColumnIndexOrThrow("match_name")));
            match.setTeam1_name(cursor.getString(cursor.getColumnIndexOrThrow("team1_name")));
            match.setTeam1_goal(cursor.getString(cursor.getColumnIndexOrThrow("team1_goals")));
            match.setTeam1_info(cursor.getString(cursor.getColumnIndexOrThrow("team1_info")));
            match.setTeam2_name(cursor.getString(cursor.getColumnIndexOrThrow("team2_name")));
            match.setTeam2_goal(cursor.getString(cursor.getColumnIndexOrThrow("team2_goals")));
            match.setTeam2_info(cursor.getString(cursor.getColumnIndexOrThrow("team2_info")));
            cursor.close(); // Закрываем курсор
        }

        db.close(); // Закрываем базу данных
        return match;
    }

    public List<Match> getAllMatches() {
        List<Match> matchList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM matches", null);

        if (cursor.moveToFirst()) {
            do {
                Match match = new Match();
                match.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                match.setMatchName(cursor.getString(cursor.getColumnIndexOrThrow("match_name")));
                match.setTeam1_info(cursor.getString(cursor.getColumnIndexOrThrow("team1_info")));
                match.setTeam1_goal(cursor.getString(cursor.getColumnIndexOrThrow("team1_goals")));
                match.setTeam1_info(cursor.getString(cursor.getColumnIndexOrThrow("team1_info")));
                match.setTeam2_name(cursor.getString(cursor.getColumnIndexOrThrow("team2_name")));
                match.setTeam2_goal(cursor.getString(cursor.getColumnIndexOrThrow("team2_goals")));
                match.setTeam2_info(cursor.getString(cursor.getColumnIndexOrThrow("team2_info")));

                matchList.add(match);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return matchList;

    }


    // Метод для получения всех матчей
    public List<String> getNameMatches() {
        List<String> names = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("matches", new String[]{
                        COLUMN_MATCH_NAME_MATCHES},
                null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int nameIndex = cursor.getColumnIndex(COLUMN_MATCH_NAME_MATCHES);
                if (nameIndex != -1) {
                    String name = cursor.getString(nameIndex);
                    names.add(name);
                }
            }
            cursor.close(); // Закрываем курсор
        }
        return names;
    }

    // Метод для обновления информации о матче
    public void updateMatch(int id, String matchName, String team1Name, int team1Goals, String team1Info, String team2Name, int team2Goals, String team2Info) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("match_name", matchName);
        values.put("team1_name", team1Name);
        values.put("team1_goals", team1Goals);
        values.put("team1_info", team1Info);
        values.put("team2_name", team2Name);
        values.put("team2_goals", team2Goals);
        values.put("team2_info", team2Info);

        db.update("matches", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Метод для удаления матча
    public void deleteMatch(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("matches", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Метод для получения голов команды 1 из определённой строки
    public int getTeam1GoalsFromRow(int rowId) {
        SQLiteDatabase db = this.getReadableDatabase(); // Открываем базу данных для чтения
        Cursor cursor = db.rawQuery("SELECT team1_goals FROM matches WHERE id = ?", new String[]{String.valueOf(rowId)});
        int team1Goals = 0; // Переменная для хранения результата

        if (cursor.moveToFirst()) {
            int team1GoalsIndex = cursor.getColumnIndex("team1_goals");
            if (team1GoalsIndex != -1) {
                team1Goals = cursor.getInt(team1GoalsIndex); // Извлечение данных
            }
        }
        cursor.close(); // Закрываем курсор
        return team1Goals; // Возвращаем количество голов
    }

    // Метод для получения голов команды 2 из определённой строки
    public int getTeam2GoalsFromRow(int rowId) {
        SQLiteDatabase db = this.getReadableDatabase(); // Открываем базу данных для чтения
        Cursor cursor = db.rawQuery("SELECT team2_goals FROM matches WHERE id = ?", new String[]{String.valueOf(rowId)});
        int team2Goals = 0; // Переменная для хранения результата

        if (cursor.moveToFirst()) {
            int team2GoalsIndex = cursor.getColumnIndex("team2_goals");
            if (team2GoalsIndex != -1) {
                team2Goals = cursor.getInt(team2GoalsIndex); // Извлечение данных
            }
        }
        cursor.close(); // Закрываем курсор
        return team2Goals; // Возвращаем количество голов
    }

    // Метод для получения названия команды 1 из определённой строки
    public String getTeam1NameFromRow(int rowId) {
        SQLiteDatabase db = this.getReadableDatabase(); // Открываем базу данных для чтения
        Cursor cursor = db.rawQuery("SELECT team1_name FROM matches WHERE id = ?", new String[]{String.valueOf(rowId)});
        String team1Name = ""; // Переменная для хранения результата

        if (cursor.moveToFirst()) {
            int team1NameIndex = cursor.getColumnIndex("team1_name");
            if (team1NameIndex != -1) {
                team1Name = cursor.getString(team1NameIndex); // Извлечение данных
            }
        }
        cursor.close(); // Закрываем курсор
        return team1Name; // Возвращаем название команды 1
    }

    // Метод для получения названия команды 2 из определённой строки
    public String getTeam2NameFromRow(int rowId) {
        SQLiteDatabase db = this.getReadableDatabase(); // Открываем базу данных для чтения
        Cursor cursor = db.rawQuery("SELECT team2_name FROM matches WHERE id = ?", new String[]{String.valueOf(rowId)});
        String team2Name = ""; // Переменная для хранения результата

        if (cursor.moveToFirst()) {
            int team2NameIndex = cursor.getColumnIndex("team2_name");
            if (team2NameIndex != -1) {
                team2Name = cursor.getString(team2NameIndex); // Извлечение данных
            }
        }
        cursor.close(); // Закрываем курсор
        return team2Name; // Возвращаем название команды 2
    }
}
