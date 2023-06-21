package com.example.memorizes.db.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.db.vo.WordVO;

import java.util.ArrayList;
import java.util.List;

public class GroupTableController {
    SQLiteHelper helper;
    SQLiteDatabase sqlite;
    List<String> list = new ArrayList<>();

    public GroupTableController(SQLiteHelper _helper) {
        this.helper = _helper;
    }

    public void insert(String groupName) {
        sqlite = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(helper.GROUP_NAME, groupName);

        sqlite.insert(helper.GROUP_TABLE_NAME, null, values);
    }
    public List<String> getAllGroups(){
        sqlite = helper.getReadableDatabase();
        String query = "Select * from " + helper.GROUP_TABLE_NAME;
        Cursor c = sqlite.rawQuery(query, null);
        while (c.moveToNext()) list.add(c.getString(0));
        return list;
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM "+ helper.GROUP_TABLE_NAME;
        sqlite = helper.getReadableDatabase();
        Cursor cursor = null;
        if (sqlite !=null){
            cursor = sqlite.rawQuery(query, null);
        }
        return cursor;
    }

    public void update(String _key, String _value, String _groupName){
        sqlite = helper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(_key, _value);
        sqlite.update(helper.GROUP_TABLE_NAME, value, "groupName=?", new String[]{_groupName});
    }

    public void delete(String _groupName){
        sqlite = helper.getWritableDatabase();
        sqlite.delete(helper.GROUP_TABLE_NAME, "groupName=?", new String[]{_groupName});
    }

    public void updateAllGroup(String oldName, String newName){
        String query = "UPDATE " + helper.WORD_TABLE_NAME + " SET " +
                helper.GROUP_NAME + "='" + newName + "' WHERE "
                + helper.GROUP_NAME + "='" + oldName + "'";
        sqlite.execSQL(query);
    }
    public void db_close(){
        sqlite.close();
        helper.close();
    }
}