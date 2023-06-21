package com.example.memorizes.db.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.db.vo.WordVO;

import java.util.ArrayList;
import java.util.List;
// 단리 관리 Query Class
public class WordTableController {
    SQLiteHelper helper;
    SQLiteDatabase sqlite;
    List<String> list = new ArrayList<>();

    public WordTableController(SQLiteHelper _helper) {
        this.helper = _helper;
    }

    public void insert(WordVO vo) {
        sqlite = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(helper.WORD, vo.getWord());
        values.put(helper.MEAN, vo.getMean());
        values.put(helper.GROUP_NAME, vo.getGroup());
        sqlite.insert(helper.WORD_TABLE_NAME, null, values);
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM "+ helper.WORD_TABLE_NAME;
        sqlite = helper.getReadableDatabase();
        Cursor cursor = null;
        if (sqlite !=null){
            cursor = sqlite.rawQuery(query, null);
        }
        return cursor;
    }

    public List<String> getWordList(){
        sqlite = helper.getReadableDatabase();
        String query = "Select word from " + helper.WORD_TABLE_NAME;
        Cursor c = sqlite.rawQuery(query, null);

        while (c.moveToNext()) list.add(c.getString(0));
        return list;
    }

    public void update(WordVO vo, String _word){
        sqlite = helper.getWritableDatabase();

        String query = "UPDATE " + helper.WORD_TABLE_NAME + " SET " +
                helper.WORD + "='" + vo.getWord() + "', " +
                helper.MEAN + "='" + vo.getMean() +"',"+
                helper.GROUP_NAME + "='" + vo.getGroup() + "' WHERE "
                + helper.WORD + "='" + _word+"'";

        sqlite.execSQL(query);
    }

    public void delete(String _word){
        sqlite = helper.getWritableDatabase();
        sqlite.delete(helper.WORD_TABLE_NAME, "word=?", new String[]{_word});
    }

    public List<WordVO> readGroupData(String group){
        List<WordVO> list = new ArrayList<>();
        sqlite = helper.getReadableDatabase();
        String query = "SELECT * FROM "+ helper.WORD_TABLE_NAME + " where " + helper.GROUP_NAME +
                "='"+ group +"'" ;

        Cursor cursor = null;
        if (sqlite !=null) cursor = sqlite.rawQuery(query, null);

        while (cursor.moveToNext()){
            WordVO vo = new WordVO(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );

            list.add(vo);
        }

        return list;
    }

    public void db_close(){
        sqlite.close();
        helper.close();
    }
}