package com.example.memorizes.exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.memorizes.NavActivity;
import com.example.memorizes.R;
import com.example.memorizes.adapter.TabViewPagerAdapter;
import com.example.memorizes.databinding.ActivityExResultBinding;
import com.example.memorizes.db.vo.WordVO;
import com.example.memorizes.intro.SharedPreferencesManager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExResultActivity extends AppCompatActivity {
    ActivityExResultBinding binding;
    final List<String> list = Arrays.asList("        단어", "        정답", "        오답");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgX.setOnClickListener(view->{
            startActivity(new Intent(this, NavActivity.class));
            finish();
        });

        binding.button3.setOnClickListener(view->{
            startActivity(new Intent(this, ExamActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        });

        TabViewPagerAdapter adapter = new TabViewPagerAdapter(this);
        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView tv = new TextView(ExResultActivity.this);
                tv.setTextAppearance(R.style.GuideUnselectedTabText);
                tv.setText(list.get(position));
                tab.setCustomView(tv);
            }
        }).attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferencesManager.removeExamResult(this);
    }
}