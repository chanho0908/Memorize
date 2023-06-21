package com.example.memorizes.group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.memorizes.databinding.ActivityUpdateGroupBinding;
import com.example.memorizes.db.controller.GroupTableController;
import com.example.memorizes.db.helper.SQLiteHelper;


public class UpdateGroupActivity extends AppCompatActivity {
    private ActivityUpdateGroupBinding binding;
    SQLiteHelper helper;
    GroupTableController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityUpdateGroupBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.imgX.setOnClickListener(view->{
            finish();
        });

        helper = new SQLiteHelper(this, "MemorizeDB", null, 1);
        db = new GroupTableController(helper);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String tag = intent.getStringExtra("tag");
        binding.etWord.setText(name);

        binding.button.setOnClickListener(view->{
            String mName = binding.etWord.getText().toString();

            if (mName.isEmpty()){
                Toast.makeText(this, "그룹 명을 입력 하세요.", Toast.LENGTH_SHORT).show();
            } else {
                db.update("groupName", mName, name);
                db.updateAllGroup(name, mName);
                switch (tag){
                    case "FromSelectGroup":
                        startActivity(new Intent(this, SelectGroupActivity.class));
                        finish(); break;
                    case "FromGroupList":
                        startActivity(new Intent(this, GroupListActivity.class));
                        finish(); break;
                }
            }
        });
    }

}