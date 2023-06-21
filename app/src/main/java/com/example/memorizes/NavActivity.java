package com.example.memorizes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import com.example.memorizes.adapter.ViewPagerAdapter;
import com.example.memorizes.adapter.ZoomOutPageTransformer;
import com.example.memorizes.databinding.ActivityNavBinding;


public class NavActivity extends AppCompatActivity {
    private ActivityNavBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPager.setAdapter(new ViewPagerAdapter(this));
        binding.viewPager.setPageTransformer(new ZoomOutPageTransformer());
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.navigationView.getMenu().getItem(position).setChecked(true);
            }

        });

        binding.navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_word:
                    binding.viewPager.setCurrentItem(0); break;
                case R.id.navigation_exam:
                    binding.viewPager.setCurrentItem(1); break;
                case R.id.navigation_info:
                    binding.viewPager.setCurrentItem(2); break;
            }
            return true;
        });

    }
}