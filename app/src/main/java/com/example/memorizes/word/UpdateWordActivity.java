package com.example.memorizes.word;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.memorizes.NavActivity;
import com.example.memorizes.databinding.ActivityUpdateWordBinding;
import com.example.memorizes.db.controller.WordTableController;
import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.db.vo.WordVO;
import com.example.memorizes.group.SelectGroupActivity;

import java.util.Objects;

public class UpdateWordActivity extends AppCompatActivity {

    private ActivityUpdateWordBinding binding;
    SQLiteHelper helper;
    WordTableController db;
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        helper = new SQLiteHelper(this, "MemorizeDB", null, 1);
        db = new WordTableController(helper);

        Intent intent = getIntent();
        String word = intent.getStringExtra("word");
        String mean = intent.getStringExtra("mean");
        String group = intent.getStringExtra("group");

        binding.etWord.setText(word);
        binding.etMean.setText(mean);
        binding.tvGroup.setText(group);

        binding.imgX.setOnClickListener(view->{
            finish();
        });

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent intent = result.getData();

                        groupName = intent.getStringExtra("group");
                        if (Objects.equals(groupName, "") || Objects.equals(groupName, "그룹 미지정")){
                            binding.tvGroup.setText("그룹 미지정");
                        }else binding.tvGroup.setText(groupName);
                    }
                }
        );

        binding.tvGroup.setOnClickListener(view->{
            Intent intent2 = new Intent(this, SelectGroupActivity.class);
            resultLauncher.launch(intent2);
        });

        binding.button.setOnClickListener(view->{
            String mWord = binding.etWord.getText().toString();
            String mMean = binding.etMean.getText().toString();
            String mGroup = binding.tvGroup.getText().toString();

            if (mWord.isEmpty()){
                Toast.makeText(this, "단어를 입력 하세요.", Toast.LENGTH_SHORT).show();
            } else if (mMean.isEmpty()) {
                Toast.makeText(this, "의미를 입력 하세요.", Toast.LENGTH_SHORT).show();
            }else {
                db.update(new WordVO(mWord, mMean, mGroup), word);
                Intent myIntent = new Intent();
                myIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(myIntent);

            }
        });
    }
}