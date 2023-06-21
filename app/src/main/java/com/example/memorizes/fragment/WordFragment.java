package com.example.memorizes.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.memorizes.adapter.WordAdapter;
import com.example.memorizes.databinding.FragmentWordBinding;
import com.example.memorizes.db.controller.WordTableController;
import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.db.vo.WordVO;
import com.example.memorizes.group.GroupListActivity;
import com.example.memorizes.intro.SharedPreferencesManager;
import com.example.memorizes.word.InputWordActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class WordFragment extends Fragment {
    SQLiteHelper helper;
    WordTableController db;
    WordAdapter adapter;

    ArrayList<WordVO> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentWordBinding binding = FragmentWordBinding.inflate(inflater,container, false);

        // DB 참조 객체 생성
        helper = new SQLiteHelper(
                requireActivity(), // context
                "MemorizeDB", // DBNAME
                null, // factory
                1 // DB 버전
        );

        db = new WordTableController(helper);
        // 레이아웃 설정정
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        // 어댑터 등록
        adapter = new WordAdapter(requireActivity(), list);
        binding.recyclerView.setAdapter(adapter);

        binding.fab.setOnClickListener(view->{
            startActivity(new Intent(requireActivity(), InputWordActivity.class));
        });

        getAllWords();

        adapter.notifyDataSetChanged();
        binding.allGroup.setOnClickListener(view->{
            startActivity(new Intent(requireActivity(), GroupListActivity.class));
        });

        return binding.getRoot();
    }

    public void getAllWords(){
        Cursor cursor = db.readAllData();
        while (cursor.moveToNext()){
            WordVO vo = new WordVO(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            list.add(vo);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferencesManager.setAllWordsPref(requireActivity(), "ALL_WORD", list);
    }
}