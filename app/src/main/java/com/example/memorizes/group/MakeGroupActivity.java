package com.example.memorizes.group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.memorizes.R;
import com.example.memorizes.databinding.ActivityMakeGroupBinding;
import com.example.memorizes.db.controller.GroupTableController;
import com.example.memorizes.db.controller.WordTableController;
import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.db.vo.WordVO;

import java.util.ArrayList;
import java.util.List;

// 그룹 생성 액티비티
public class MakeGroupActivity extends AppCompatActivity {

    private ActivityMakeGroupBinding binding;
    SQLiteHelper helper;
    GroupTableController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakeGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        helper = new SQLiteHelper(this, "MemorizeDB", null, 1);
        db = new GroupTableController(helper);

        binding.imgX.setOnClickListener(view->{
            finish();
        });

        Intent intent = getIntent();
        String tag = intent.getStringExtra("tag");

        binding.button.setOnClickListener(view->{
            String groupName = binding.etWord.getText().toString();

            if (groupName.isEmpty()){
                Toast.makeText(this, "그룹 이름을 입력해 주세요", Toast.LENGTH_SHORT).show();
            }else {
                if (checkDuple(groupName)){
                    db.insert(groupName);
                    switch (tag){
                        case "FromSelectGroup":
                            startActivity(new Intent(this, SelectGroupActivity.class));
                            finish(); break;
                        case "FromGroupList":
                            startActivity(new Intent(this, GroupListActivity.class));
                            finish(); break;
                    }
                }else Toast.makeText(this, "이미 존재 하는 그룹명 입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public Boolean checkDuple(String s){
        List<String> list = db.getAllGroups();
        return !list.contains(s);
    }
}