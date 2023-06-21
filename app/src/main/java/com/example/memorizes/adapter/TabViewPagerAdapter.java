package com.example.memorizes.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.memorizes.fragment.AllWordFragment;
import com.example.memorizes.fragment.AnswerFragment;
import com.example.memorizes.fragment.IncorrectFragment;

import java.util.ArrayList;
import java.util.List;

public class TabViewPagerAdapter extends FragmentStateAdapter {
    List<Fragment> fragmentList;

    public TabViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragmentList = new ArrayList<>();
        fragmentList.add(new AllWordFragment());
        fragmentList.add(new AnswerFragment());
        fragmentList.add(new IncorrectFragment());
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
