package com.example.memorizes.intro;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.memorizes.db.vo.WordVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SharedPreferencesManager {
    private static final String PREFERENCES_NAME = "my_preferences";
    private static final String EXAM_PREFERENCES_NAME = "exam_preferences";

    public static SharedPreferences getPreferences(Context context){
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    // 로그인 정보 저장
    public static void setLoginInfo(Context context, String userID, String userPassword){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userID", userID);
        editor.putString("userPassword", userPassword);

        editor.apply();
    }

    public static Map<String, String> getLoginInfo(Context context){
        SharedPreferences prefs = getPreferences(context);
        Map<String, String> LoginInfo = new HashMap<>();
        String userID = prefs.getString("userID", ""); // default Value ="" <- nullPointException 방지
        String userPassword = prefs.getString("userPassword", "");

        LoginInfo.put("userID", userID);
        LoginInfo.put("userPassword", userPassword);

        return LoginInfo;
    }

    public static void setGroupName(Context context, String group){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("groupName", group);
        editor.apply();
    }

    public static String getGroupName(Context context){
        SharedPreferences prefs = getPreferences(context);
        prefs.getString("groupName", "");

        return prefs.getString("groupName", "");
    }

    public static void setAllWordsPref(Context context, String key, ArrayList<WordVO> values){
        SharedPreferences prefs = context.getSharedPreferences(EXAM_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Json 배열
        JSONArray jsonArray = new JSONArray();

        // Gson 객체 생성
        Gson gson = new GsonBuilder().create();

        for (int i = 0; i < values.size(); i++) {
            // WordVO -> Json
            String value = gson.toJson(values.get(i), WordVO.class);
            jsonArray.put(value);
        }

        if (!values.isEmpty()) {
            editor.putString(key, jsonArray.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
    }

    public static ArrayList getAllWordsPref(Context context, String key){
        SharedPreferences prefs =  context.getSharedPreferences(EXAM_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(key, null);

        ArrayList<WordVO> list = new ArrayList<WordVO>();
        Gson gson =new GsonBuilder().create();

        if (json != null) {
            try {
                JSONArray arr = new JSONArray(json);
                for (int i = 0; i < arr.length(); i++) {
                    WordVO wv = gson.fromJson(arr.get(i).toString() , WordVO.class);
                    list.add(wv);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public static void setCorrectWordsPref(Context context, String key, ArrayList<WordVO> values){
        SharedPreferences prefs = context.getSharedPreferences(EXAM_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Json 배열
        JSONArray jsonArray = new JSONArray();

        // Gson 객체 생성
        Gson gson = new GsonBuilder().create();

        for (int i = 0; i < values.size(); i++) {
            // WordVO -> Json
            String value = gson.toJson(values.get(i), WordVO.class);
            jsonArray.put(value);
        }

        if (!values.isEmpty()) {
            editor.putString(key, jsonArray.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
    }

    public static ArrayList getCorrectWordsPref(Context context, String key){
        SharedPreferences prefs =  context.getSharedPreferences(EXAM_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(key, null);

        ArrayList<WordVO> list = new ArrayList<WordVO>();
        Gson gson =new GsonBuilder().create();

        if (json != null) {
            try {
                JSONArray arr = new JSONArray(json);
                for (int i = 0; i < arr.length(); i++) {
                    WordVO wv = gson.fromJson(arr.get(i).toString() , WordVO.class);
                    list.add(wv);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public static void setIncorrectWordsPref(Context context, String key, ArrayList<WordVO> values){
        SharedPreferences prefs = context.getSharedPreferences(EXAM_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Json 배열
        JSONArray jsonArray = new JSONArray();

        // Gson 객체 생성
        Gson gson = new GsonBuilder().create();

        for (int i = 0; i < values.size(); i++) {
            // WordVO -> Json
            String value = gson.toJson(values.get(i), WordVO.class);
            jsonArray.put(value);
        }

        if (!values.isEmpty()) {
            editor.putString(key, jsonArray.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
    }

    public static ArrayList getIncorrectWordsPref(Context context, String key){
        SharedPreferences prefs =  context.getSharedPreferences(EXAM_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(key, null);

        ArrayList<WordVO> list = new ArrayList<WordVO>();
        Gson gson =new GsonBuilder().create();

        if (json != null) {
            try {
                JSONArray arr = new JSONArray(json);
                for (int i = 0; i < arr.length(); i++) {
                    WordVO wv = gson.fromJson(arr.get(i).toString() , WordVO.class);
                    list.add(wv);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public static void removeExamResult(Context context){
        SharedPreferences prefs = context.getSharedPreferences(EXAM_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.clear();
        editor.commit();
    }
}
