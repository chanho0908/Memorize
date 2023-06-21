package com.example.memorizes.exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import com.example.memorizes.R;
import com.example.memorizes.databinding.ActivityExamBinding;
import com.example.memorizes.db.controller.WordTableController;
import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.db.vo.WordVO;
import com.example.memorizes.intro.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 영단어 시험 Activity
public class ExamActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    ActivityExamBinding binding;
    Dialog dialog;
    SQLiteHelper helper;
    WordTableController db;
    List<WordVO> list = new ArrayList<>();
    ArrayList<WordVO> correctWordList = new ArrayList<>();
    ArrayList<WordVO> incorrectWordList = new ArrayList<>();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.imgX.setOnClickListener(view->{
            showDialog();
        });

        // DB 참조 객체 생성
        helper = new SQLiteHelper(
                this, // context
                "MemorizeDB", // DBNAME
                null, // factory
                1 // DB 버전
        );

        db = new WordTableController(helper);
        getAllWords();
        Collections.shuffle(list);

        binding.etWord.setOnEditorActionListener(this);
        binding.tvWord.setText(list.get(0).getMean());
        Log.d("asdas", String.valueOf(list.size()));
    }

    public void getAllWords(){
        Cursor cursor = db.readAllData();
        while (cursor.moveToNext()){
            WordVO vo = new WordVO(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            list.add(vo);
        }
    }

    public void showDialog(){
        dialog.show();

        dialog.findViewById(R.id.noBtn).setOnClickListener(view->{
            dialog.dismiss();
        });

        dialog.findViewById(R.id.yesBtn).setOnClickListener(view->{
            finish();
        });
    }
    //키보드 완료 버튼 리스너
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId()==R.id.et_word && actionId == EditorInfo.IME_ACTION_DONE){

            String userAnswer = binding.etWord.getText().toString();

            if (userAnswer.equalsIgnoreCase(list.get(i).getWord())){
                MediaPlayer.create(this, R.raw.damm).start();
                binding.tvResult.setText("Damm~");
                binding.imgResult.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.icon_jesus, null));
                correctWordList.add(new WordVO(list.get(i).getWord(),list.get(i).getMean(), list.get(i).getGroup()));
            }else {
                MediaPlayer.create(this, R.raw.snd_crycat).start();
                binding.tvResult.setText("Holy...");
                binding.imgResult.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.icon_upper, null));
                incorrectWordList.add(new WordVO(list.get(i).getWord(),list.get(i).getMean(), list.get(i).getGroup()));
            }
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.tvResult.setText("");
                    binding.imgResult.setImageDrawable(null);
                }
            }, 3000);

            binding.etWord.setText("");
            i++;

            if (i >= list.size()){
                Intent intent = new Intent(this, ExResultActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                SharedPreferencesManager.setCorrectWordsPref(this, "CORRECT_WORD", correctWordList);
                SharedPreferencesManager.setIncorrectWordsPref(this,"INCORRECT_WORD", incorrectWordList);
                startActivity(intent);
                finish();
            }else binding.tvWord.setText(list.get(i).getMean());

        }

        return false;
    }
}