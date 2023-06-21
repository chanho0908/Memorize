package com.example.memorizes.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.memorizes.R;
import com.example.memorizes.databinding.FragmentExamBinding;
import com.example.memorizes.exam.ExamActivity;

public class ExamFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentExamBinding binding = FragmentExamBinding.inflate(inflater, container, false);

        binding.card.setOnClickListener(view->{
            startActivity(new Intent(requireActivity(), ExamActivity.class));
        });
        return binding.getRoot();
    }
}