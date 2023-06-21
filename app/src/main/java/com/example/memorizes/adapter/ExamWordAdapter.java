package com.example.memorizes.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorizes.databinding.ExamWordItemBinding;
import com.example.memorizes.databinding.WordItemBinding;
import com.example.memorizes.db.controller.WordTableController;
import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.db.vo.WordVO;
import com.example.memorizes.word.UpdateWordActivity;

import java.util.List;
import java.util.Locale;

public class ExamWordAdapter extends RecyclerView.Adapter<ExamWordAdapter.MainViewHolder>{
    private List<WordVO> list;
    private Context mContext;

    TextToSpeech tts;

    public ExamWordAdapter(Context context, List<WordVO> list) {
        this.mContext = context;
        this.list = list;
        notifyDataSetChanged();


        tts = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        Log.d("TTsError", "지원하지 않는 언어입니다.");
                    }
                }
            }
        });
    }
    public class MainViewHolder extends RecyclerView.ViewHolder{
        ExamWordItemBinding binding;
        int flag;
        int pos;

        public MainViewHolder(@NonNull ExamWordItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.btnTts.setOnClickListener(view->{
                tts.speak(binding.tvWord.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            });


            binding.tvWord.setOnClickListener(view->{
                if (flag == 0){
                    binding.tvMean.setVisibility(View.VISIBLE);
                    flag = 1;
                }else {
                    binding.tvMean.setVisibility(View.INVISIBLE);
                    flag = 0;
                }
            });
        }
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExamWordItemBinding binding = ExamWordItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        WordVO vo = list.get(position);
        holder.binding.tvWord.setText(vo.getWord());
        holder.binding.tvMean.setText(vo.getMean());
        holder.binding.tvGroup.setText(vo.getGroup());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
