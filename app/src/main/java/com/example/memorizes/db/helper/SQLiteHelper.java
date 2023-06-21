package com.example.memorizes.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// SQLite DB 생성 클래스
public class SQLiteHelper extends SQLiteOpenHelper {
    public final int DATABASE_VERSION = 1;
    public final String USER_TABLE_NAME = "userinfo";
    public final String USER_ID = "userID";
    public final String USER_PASSWORD = "userPassword";
    public final String USER_NAME = "username";
    public final String USER_PHONE = "userphone";

    public final String WORD_TABLE_NAME = "voca";
    public final String WORD = "word";
    public final String MEAN = "mean";

    public final String GROUP_TABLE_NAME = "groups";
    public final String GROUP_NAME = "groupName";

    public SQLiteHelper(@Nullable Context context, @Nullable String name,
                        @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query = "create table if not exists " + USER_TABLE_NAME + "("
                + USER_ID + " text not null primary key, "
                + USER_PASSWORD + " text not null, "
                + USER_NAME + " text not null, "
                + USER_PHONE + " text not null);";

        String create_query2 = "create table if not exists " + WORD_TABLE_NAME + "("
                + WORD + " text not null primary key, "
                + MEAN + " text not null, "
                + GROUP_NAME + " text not null );";

        String create_query3 = "create table if not exists " + GROUP_TABLE_NAME + "("
                + GROUP_NAME + " text not null primary key );";

        db.execSQL(create_query);
        db.execSQL(create_query2);
        db.execSQL(create_query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_query = "drop table " + USER_TABLE_NAME + ";";
        String drop_query2 = "drop table " + USER_TABLE_NAME + ";";

        db.execSQL(drop_query);
        db.execSQL(drop_query2);

        onCreate(db);
    }
}
