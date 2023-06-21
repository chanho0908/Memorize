package com.example.memorizes.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.memorizes.R;
import com.example.memorizes.adapter.ExamWordAdapter;
import com.example.memorizes.adapter.WordAdapter;
import com.example.memorizes.databinding.FragmentAnswerBinding;
import com.example.memorizes.db.vo.WordVO;
import com.example.memorizes.intro.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;


public class AnswerFragment extends Fragment {
    ExamWordAdapter adapter;
    List<WordVO> list = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAnswerBinding binding = FragmentAnswerBinding.inflate(inflater, container, false);

        list = SharedPreferencesManager.getCorrectWordsPref(requireContext(), "CORRECT_WORD");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new ExamWordAdapter(requireContext(), list);

        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }
}