package com.example.memorizes.word;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.memorizes.databinding.ActivityInputWordBinding;
import com.example.memorizes.db.controller.WordTableController;
import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.db.vo.WordVO;
import com.example.memorizes.group.SelectGroupActivity;
import com.example.memorizes.intro.SharedPreferencesManager;

import java.util.List;

public class InputWordFromGroupActivity extends AppCompatActivity {
    private ActivityInputWordBinding binding;
    SQLiteHelper helper;
    WordTableController db;
    String group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        helper = new SQLiteHelper(this, "MemorizeDB", null, 1);
        db = new WordTableController(helper);

        Intent intent = getIntent();
        String BeforeGroup = intent.getStringExtra("group");
        binding.tvGroup.setText(BeforeGroup);

        binding.imgX.setOnClickListener(view->{
            Intent exitIntent = new Intent(this, GroupByWordActivity.class);
            exitIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(exitIntent);
            finish();
        });

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        group = SharedPreferencesManager.getGroupName(InputWordFromGroupActivity.this);
                        binding.tvGroup.setText(group);
                        SharedPreferencesManager.setGroupName(InputWordFromGroupActivity.this, "");
                    }
                }
        );

        binding.tvGroup.setOnClickListener(view->{
            Intent intent2 = new Intent(this, SelectGroupActivity.class);
            resultLauncher.launch(intent2);
        });

        binding.button.setTextColor(Color.WHITE);

        binding.button.setOnClickListener(view ->{
            String word = binding.etWord.getText().toString();
            String mean = binding.etMean.getText().toString();

            if (word.isEmpty()){
                Toast.makeText(this, "단어를 입력해 주세요", Toast.LENGTH_SHORT).show();
            } else if (mean.isEmpty()) {
                Toast.makeText(this, "뜻을 입력해 주세요", Toast.LENGTH_SHORT).show();
            }else {
                if (checkDupleWord(word)){
                    db.insert(new WordVO(word, mean, binding.tvGroup.getText().toString()));
                    Toast.makeText(this, "추가 완료", Toast.LENGTH_SHORT).show();
                    binding.etWord.requestFocus();
                    binding.etWord.setText("");
                    binding.etMean.setText("");
                }else Toast.makeText(this, "이미 존재 하는 단어 입니다!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean checkDupleWord(String word){
        List<String> list = db.getWordList();
        if (list.contains(word)) return false;
        return true;
    }
}