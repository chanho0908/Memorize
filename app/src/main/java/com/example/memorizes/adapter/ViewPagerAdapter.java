package com.example.memorizes.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.memorizes.fragment.ExamFragment;
import com.example.memorizes.fragment.InfoFragment;
import com.example.memorizes.fragment.WordFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    List<Fragment> fragmentList;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragmentList = new ArrayList<>();
        fragmentList.add(new WordFragment());
        fragmentList.add(new ExamFragment());
        fragmentList.add(new InfoFragment());

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
