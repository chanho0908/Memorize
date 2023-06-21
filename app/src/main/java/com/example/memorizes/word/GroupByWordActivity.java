package com.example.memorizes.word;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.example.memorizes.R;
import com.example.memorizes.adapter.GroupByWordAdapter;
import com.example.memorizes.adapter.GroupListAdapter;
import com.example.memorizes.adapter.WordAdapter;
import com.example.memorizes.databinding.ActivityGroupByWordBinding;
import com.example.memorizes.db.controller.WordTableController;
import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.db.vo.WordVO;
import com.example.memorizes.group.GroupListActivity;

import java.util.ArrayList;
import java.util.List;

public class GroupByWordActivity extends AppCompatActivity {
    ActivityGroupByWordBinding binding;
    GroupByWordAdapter adapter;

    SQLiteHelper helper;
    WordTableController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupByWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // DB 참조 객체 생성
        helper = new SQLiteHelper(
                this, // context
                "MemorizeDB", // DBNAME
                null, // factory
                1 // DB 버전
        );

        db = new WordTableController(helper);

        String groupName = new GroupListAdapter().getSelectedGroup();
        List<WordVO> list = db.readGroupData(groupName);

        adapter = new GroupByWordAdapter(this, list);

        // 레이아웃 설정정
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.imgX.setOnClickListener(view->{
            finish();
        });
        // 어댑터 등록
        binding.recyclerView.setAdapter(adapter);

        binding.fab.setOnClickListener(view->{
            Intent intent2 = new Intent(this, InputWordFromGroupActivity.class);
            intent2.putExtra("group", groupName);
            startActivity(intent2);
            finish();
        });


        binding.allGroup.setOnClickListener(view->{
            startActivity(new Intent(this, GroupListActivity.class));
        });

    }

}