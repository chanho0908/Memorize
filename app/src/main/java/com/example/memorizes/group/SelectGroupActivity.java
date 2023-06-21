package com.example.memorizes.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.memorizes.adapter.GroupAdapter;
import com.example.memorizes.databinding.ActivitySelectGroupBinding;
import com.example.memorizes.db.controller.GroupTableController;
import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.intro.SharedPreferencesManager;
import com.example.memorizes.word.InputWordActivity;


import java.util.ArrayList;
import java.util.List;

public class SelectGroupActivity extends AppCompatActivity {
    SQLiteHelper helper;
    GroupTableController db;
    private ActivitySelectGroupBinding binding;
    List<String> list = new ArrayList<>();
    GroupAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgX.setOnClickListener(view->{
            SharedPreferencesManager.setGroupName(this, "그룹 미지정");
            finish();
        });
        binding.fab.setOnClickListener(view->{
            Intent intent = new Intent(this, MakeGroupActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("tag", "FromSelectGroup");
            startActivity(intent);
            finish();
        });

        // DB 참조 객체 생성
        helper = new SQLiteHelper(
                this, // context
                "MemorizeDB", // DBNAME
                null, // factory
                1 // DB 버전
        );

        db = new GroupTableController(helper);
        // 레이 아웃 설정
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 어댑터 등록
        adapter = new GroupAdapter(this, list);
        binding.recyclerView.setAdapter(adapter);

        getAllGroups();

        binding.button.setOnClickListener(view->{
            Intent intent = getIntent();
            String data = new GroupAdapter().getSelectedGroup();
            if (data == ""){
                Toast.makeText(this, "그룹을 선택해 주세요!", Toast.LENGTH_SHORT).show();
            }else{
                SharedPreferencesManager.setGroupName(this, data);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

        });
    }

    public void getAllGroups(){
        Cursor cursor = db.readAllData();
        while (cursor.moveToNext()){
            list.add(cursor.getString(0));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}