package com.example.memorizes.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.memorizes.R;
import com.example.memorizes.databinding.ActivityJoinBinding;
import com.example.memorizes.db.controller.UserTableController;
import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.db.vo.UserVO;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class JoinActivity extends AppCompatActivity {

    private ActivityJoinBinding binding;
    SQLiteHelper helper;
    UserTableController sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        helper = new SQLiteHelper(
                this, // context
                "MemorizeDB", // DBNAME
                null, // factory
                1 // DB 버전
        );

        sqlite = new UserTableController(helper);

        binding.btnSubmit.setOnClickListener(view -> {
            String id = binding.userId.getText().toString();
            String pw = binding.password.getText().toString();
            String name = binding.name.getText().toString();
            String phone = binding.phone.getText().toString();

            if (id.length() == 0){
                Toast.makeText(this, "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show();
            } else if (pw.length() == 0) {
                Toast.makeText(this, "비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
            }else if (name.length() == 0) {
                Toast.makeText(this, "이름을 입력해 주세요", Toast.LENGTH_SHORT).show();
            }else if (phone.length() == 0) {
                Toast.makeText(this, "전화번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
            }else{
                if (checkDupleId(id)){
                    sqlite.join(new UserVO(id, pw, name, phone));
                    sqlite.db_close();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else Toast.makeText(this, "이미 사용 중인 아이디 입니다.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //아이디 중복 확인
    private Boolean checkDupleId(String id){
        List<String> list = sqlite.getUserIDs();
        return !list.contains(id);
    }

}