package com.example.memorizes.db.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.db.vo.UserVO;

import java.util.ArrayList;
import java.util.List;
// 회원 관리 Query Class
public class UserTableController {
    SQLiteHelper helper;
    SQLiteDatabase sqlite;
    List<String> list = new ArrayList<>();;
    public UserTableController(SQLiteHelper _helper) {
        this.helper = _helper;
    }

    public void join(UserVO vo) {
        sqlite = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(helper.USER_ID, vo.getUserID());
        values.put(helper.USER_PASSWORD, vo.getUserPassword());
        values.put(helper.USER_NAME, vo.getUserName());
        values.put(helper.USER_PHONE, vo.getUserPhone());

        sqlite.insert(helper.USER_TABLE_NAME, null, values);
    }

    public UserVO getUser(String userID){
        sqlite = helper.getReadableDatabase();
        UserVO vo = new UserVO();

        String query = "Select * from " + helper.USER_TABLE_NAME + " where userID='"+ userID+ "'";
        Cursor c = sqlite.rawQuery(query, null);
        while (c.moveToNext()){
            vo.setUserID(c.getString(0));
            vo.setUserPassword(c.getString(1));
            vo.setUserName(c.getString(2));
            vo.setUserPhone(c.getString(3));
        }
        return vo;
    }

    public List<String> getUserIDs(){
        sqlite = helper.getReadableDatabase();
        String query = "Select userID from " + helper.USER_TABLE_NAME;
        Cursor c = sqlite.rawQuery(query, null);

        while (c.moveToNext()) list.add(c.getString(0));
        return list;
    }


    public String getUserPassword(String userID){
        sqlite = helper.getReadableDatabase();
        String query = "Select userPassword from " + helper.USER_TABLE_NAME + " where userID='"+ userID+ "'";

        Cursor c = sqlite.rawQuery(query, null);
        String result = "";

        while (c.moveToNext()) result = c.getString(0);

        c.close();
        sqlite.close();
        return result;
    }

    public void update(String _key, String _value, String _userID){
        sqlite = helper.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(_key, _value);
        sqlite.update(helper.USER_TABLE_NAME, value, "userID=?", new String[]{_userID});
    }

    public void delete(String _userID){
        sqlite = helper.getWritableDatabase();
        sqlite.delete(helper.USER_TABLE_NAME, "userID=?", new String[]{_userID});
    }


    public void db_close(){
        sqlite.close();
        helper.close();
    }
}