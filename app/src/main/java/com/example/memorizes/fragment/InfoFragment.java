package com.example.memorizes.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.memorizes.R;
import com.example.memorizes.databinding.FragmentInfoBinding;
import com.example.memorizes.db.controller.UserTableController;
import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.db.vo.UserVO;
import com.example.memorizes.intro.SharedPreferencesManager;

import java.util.Map;


public class InfoFragment extends Fragment {
    SQLiteHelper helper;
    UserTableController sqlite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentInfoBinding binding = FragmentInfoBinding.inflate(inflater, container, false);

        helper = new SQLiteHelper(
                requireActivity(), // context
                "MemorizeDB", // DBNAME
                null, // factory
                1 // DB 버전
        );

        sqlite = new UserTableController(helper);

        Map<String, String> rs =  SharedPreferencesManager.getLoginInfo(requireActivity());
        String userId = rs.get("userID");

        UserVO vo = new UserTableController(helper).getUser(userId);

        binding.userId.setText(vo.getUserID());

        binding.phone.setText(vo.getUserPhone());
        binding.name.setText(vo.getUserName());

        return binding.getRoot();
    }
}