package com.example.memorizes.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.memorizes.R;
import com.example.memorizes.adapter.ExamWordAdapter;
import com.example.memorizes.adapter.WordAdapter;
import com.example.memorizes.databinding.FragmentAllWordBinding;
import com.example.memorizes.db.vo.WordVO;
import com.example.memorizes.exam.ExamActivity;
import com.example.memorizes.intro.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

// 리사이클러 뷰를 이용해 DB에 저장 된 단어들을 보여주는 fragment
public class AllWordFragment extends Fragment {
    ExamWordAdapter adapter;
    ArrayList list = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAllWordBinding binding = FragmentAllWordBinding.inflate(inflater, container, false);

        list = SharedPreferencesManager.getAllWordsPref(requireContext(), "ALL_WORD");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new ExamWordAdapter(requireContext(), list);

        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }
}