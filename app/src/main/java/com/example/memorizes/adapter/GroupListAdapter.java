
package com.example.memorizes.adapter;

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
import com.example.memorizes.db.helper.SQLiteHelper;
import com.example.memorizes.group.UpdateGroupActivity;
import com.example.memorizes.word.GroupByWordActivity;

import java.util.ArrayList;
import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ListItemHolder>{

    private List<String> list;
    private ArrayList<GroupItemBinding> mHolderList;
    private Context mContext;
    int selectedPos = -1;
    static String groupName="";

    private SQLiteHelper helper;
    GroupTableController db;

    public GroupListAdapter(){};

    // 어댑터 생성자
    public GroupListAdapter(Context context, List<String> list) {
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
        binding.groupName.setText(list.get(position));

        //binding.wordCount.setTextColor(Color.parseColor(#6495ED));

        holder.binding.getRoot().setOnClickListener(view->{
            selectedPos = holder.getBindingAdapterPosition();
            if (selectedPos != RecyclerView.NO_POSITION){
                groupName = list.get(position);
                Intent intent = new Intent(mContext, GroupByWordActivity.class);
                intent.putExtra("groupName", groupName);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class ListItemHolder extends RecyclerView.ViewHolder{
        GroupItemBinding binding;
        int pos;
        public ListItemHolder(@NonNull GroupItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


            binding.btnModify.setOnClickListener(view->{
                 pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(mContext, UpdateGroupActivity.class);
                        intent.putExtra("name", list.get(pos));
                        intent.putExtra("tag", "FromGroupList");
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        mContext.startActivity(intent);
                    }
            });

            binding.btnDel.setOnClickListener(view->{
                 pos = getBindingAdapterPosition();

                    AlertDialog dialog = new AlertDialog.Builder(mContext)
                            .setMessage("삭제 하시겠습니까?")
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

