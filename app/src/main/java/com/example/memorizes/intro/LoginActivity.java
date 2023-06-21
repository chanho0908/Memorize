package com.example.memorizes.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.memorizes.NavActivity;
import com.example.memorizes.databinding.ActivityLoginBinding;
import com.example.memorizes.db.controller.UserTableController;
import com.example.memorizes.db.helper.SQLiteHelper;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    SQLiteHelper helper;
    UserTableController sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        helper = new SQLiteHelper(
                this, // context
                "MemorizeDB", // DBNAME
                null, // factory
                1 // DB 버전4
        );

        sqlite = new UserTableController(helper);

        binding.txtJoin.setOnClickListener(view->{
            Intent intent = new Intent(this, JoinActivity.class);
            startActivity(intent);
        });

        binding.btnSubmit.setOnClickListener(view-> {
            String userId = binding.userID.getText().toString();
            String userPw = binding.password.getText().toString();

            checkLoginProcess(userId, userPw);
        });
    }

    private void checkLoginProcess(String userId, String userPw){
        List<String> list = sqlite.getUserIDs();

        if(userId.isEmpty()){
            Toast.makeText(this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }else if(userPw.isEmpty()){
            Toast.makeText(this, "비밀 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else if (!list.contains(userId)){
            Toast.makeText(this, "존재 하지 않는 아이디 입니다.", Toast.LENGTH_SHORT).show();
        } else{
            String pw = sqlite.getUserPassword(userId);

                if (!userPw.equalsIgnoreCase(pw)) {
                Toast.makeText(this, "비밀 번호가 틀립니다.", Toast.LENGTH_SHORT).show();
            } else{
                sqlite.db_close();
                // 로그인 성공 시 Sharedpreference에 로그인 정보 저장
                SharedPreferencesManager.setLoginInfo(this, userId ,pw);
                startActivity(new Intent(this, NavActivity.class));
            }
        }
    }
}