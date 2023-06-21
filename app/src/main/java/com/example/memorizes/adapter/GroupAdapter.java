
package com.example.memorizes.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorizes.databinding.GroupItemBinding;
import com.example.memorizes.db.controller.GroupTableController;
import com.example.memorizes.db.controller.WordTableController;
import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.group.UpdateGroupActivity;
import com.example.memorizes.word.UpdateWordActivity;


import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ListItemHolder>{

    private List<String> list;
    private ArrayList<GroupItemBinding> mHolderList;
    private Context mContext;
    int selectedPos = -1;
    static String groupName="";

    private SQLiteHelper helper;
    GroupTableController db;

    public GroupAdapter(){

    }

    // 어댑터 생성자
    public GroupAdapter(Context context, List<String> list) {
        this.list = list;
        this.mContext = context;
        mHolderList = new ArrayList<>();
        notifyDataSetChanged();

        helper = new SQLiteHelper(
                mContext, // context
                "MemorizeDB", // DBNAME
                null, // factory
                1 // DB 버전
        );
        db = new GroupTableController(helper);
    }

    // ViewHolder 객체 반환
    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        GroupItemBinding binding = GroupItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ListItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {

        // View를 참조할 binding을 얻는다
        final GroupItemBinding binding = holder.binding;


        // 그룹 이름 Set
        binding.groupName.setText(list.get(position));
        binding.container.setTag("none");

        if (!mHolderList.contains(holder)) mHolderList.add(holder.binding);

        if(selectedPos != -1 && position == selectedPos) {

            binding.container.setBackgroundColor(Color.YELLOW);
            binding.container.setTag("select");

        }

        binding.container.setOnClickListener(view->{
            // 선택 되지 않았을 떄
            if (binding.container.getTag().equals("none")){
                for (int i = 0; i < mHolderList.size(); i++) {
                    mHolderList.get(i).container.setCardBackgroundColor(Color.WHITE);
                    mHolderList.get(i).container.setTag("none");
                    mHolderList.get(i).groupName.setTextColor(Color.BLACK);
                    mHolderList.get(i).btnDel.setVisibility(View.VISIBLE);
                    mHolderList.get(i).btnModify.setVisibility(View.VISIBLE);
                }

                binding.container.setCardBackgroundColor(Color.parseColor("#8b00ff"));
                binding.groupName.setTextColor(Color.WHITE);
                binding.btnDel.setVisibility(View.INVISIBLE);
                binding.btnModify.setVisibility(View.INVISIBLE);
                binding.container.setTag("select");

                selectedPos = holder.getBindingAdapterPosition();
                groupName = list.get(position);

            }else {
                binding.container.setCardBackgroundColor(Color.WHITE);
                binding.groupName.setTextColor(Color.BLACK);
                binding.btnDel.setVisibility(View.VISIBLE);
                binding.btnModify.setVisibility(View.VISIBLE);
                binding.container.setTag("none");
                selectedPos = -1;
            }
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class ListItemHolder extends RecyclerView.ViewHolder{
        GroupItemBinding binding;

        public ListItemHolder(@NonNull GroupItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.btnModify.setOnClickListener(view->{
                int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(mContext, UpdateGroupActivity.class);
                        intent.putExtra("name", list.get(pos));
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("tag", "FromSelectGroup");
                        mContext.startActivity(intent);

                    }
            });

            binding.btnDel.setOnClickListener(view->{
                int pos = getBindingAdapterPosition();

                    AlertDialog dialog = new AlertDialog.Builder(mContext)
                            .setMessage("삭제 하시겠 습니까?")
                            .setPositiveButton(
                                    "삭제", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            db.delete(list.get(pos));
                                            list.remove(pos);
                                            notifyItemRemoved(pos);
                                            db.db_close();
                                        }
                                    }
                            ).setNegativeButton(
                                    "취소", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {}
                                    }
                            ).create();
                    dialog.show();

            });

        }
    }

    public String getSelectedGroup(){
        return groupName;
    }


}

