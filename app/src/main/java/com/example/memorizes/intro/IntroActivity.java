package com.example.memorizes.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.example.memorizes.NavActivity;
import com.example.memorizes.databinding.ActivityIntroBinding;

import java.util.Map;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /**
         * todo 어플리케이션 시작 시 sharedPreferences에 사용자 로그인 정보가
         * todo 저장 되어 있으면 해당 계정으로 자동 로그인
         * */
        getPreferences();
        binding.btnJoin.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
    }

    private void getPreferences() {
        Map<String, String> rs = SharedPreferencesManager.getLoginInfo(this);
        String userId = rs.get("userID");
        String userPassword = rs.get("userPassword");

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!userId.isEmpty() && !userPassword.isEmpty()) {
                    startActivity(new Intent(IntroActivity.this, NavActivity.class));
                    finish();
                } else {
                    binding.btnLogin.setVisibility(View.VISIBLE);
                    binding.btnJoin.setVisibility(View.VISIBLE);

                    binding.btnJoin.setOnClickListener(view -> {
                        startActivity(new Intent(IntroActivity.this, JoinActivity.class));
                    });
                    binding.btnLogin.setOnClickListener(view -> {
                        startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                    });

                }
            }
        }, 2000);

    }

    @Override
    public void onClick(View v) {
            // 회원 가입 버튼 클릭 시 회원 가입 화면으로 이동
            if (v == binding.btnJoin){
                Intent intent = new Intent(this, JoinActivity.class);
                startActivity(intent);
            } else if (v == binding.btnLogin) { // 로그인 버튼 클릭 시 로그인 화면으로 이동
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
    }
}