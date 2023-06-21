package com.example.memorizes.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.memorizes.R;
import com.example.memorizes.adapter.ExamWordAdapter;
import com.example.memorizes.adapter.WordAdapter;
import com.example.memorizes.databinding.FragmentIncorrectBinding;
import com.example.memorizes.db.vo.WordVO;
import com.example.memorizes.intro.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;


public class IncorrectFragment extends Fragment {
    ExamWordAdapter adapter;
    List<WordVO> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentIncorrectBinding binding = FragmentIncorrectBinding.inflate(inflater, container, false);

        list = SharedPreferencesManager.getIncorrectWordsPref(requireContext(), "INCORRECT_WORD");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new ExamWordAdapter(requireContext(), list);

        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }
}