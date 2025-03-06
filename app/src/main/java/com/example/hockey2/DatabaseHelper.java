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

        // Добавление трех матчей после создания таблицы
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

    // Метод для получения всех матчей
    public List<String> getNameMatches() {
        List<String> names = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("matches",new String[]{
                COLUMN_MATCH_NAME_MATCHES},
                null,null,null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                int nameIndex = cursor.getColumnIndex(COLUMN_MATCH_NAME_MATCHES);
                if(nameIndex != -1){
                    String name = cursor.getString(nameIndex);
                    names.add(name);
                }
            }
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
}
