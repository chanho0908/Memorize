package com.example.memorizes.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.memorizes.NavActivity;
import com.example.memorizes.adapter.GroupListAdapter;
import com.example.memorizes.databinding.ActivityGroupListBinding;
import com.example.memorizes.db.controller.GroupTableController;
import com.example.memorizes.db.helper.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class GroupListActivity extends AppCompatActivity {

    SQLiteHelper helper;
    GroupTableController db;
    private ActivityGroupListBinding binding;
    List<String> list = new ArrayList<>();
    GroupListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgX.setOnClickListener(view->{
            startActivity(new Intent(this, NavActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });

        binding.fab.setOnClickListener(view->{
            Intent intent = new Intent(this, MakeGroupActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("tag", "FromGroupList");
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
        adapter = new GroupListAdapter(this, list);
        binding.recyclerView.setAdapter(adapter);

        getAllGroups();

    }

    public void getAllGroups(){
        Cursor cursor = db.readAllData();
        while (cursor.moveToNext()){
            list.add(cursor.getString(0));
            adapter.notifyDataSetChanged();
        }
    }
}