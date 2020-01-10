package com.home.atm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FunctionAdapter extends
        RecyclerView.Adapter<FunctionAdapter.FunctionViewHolder>{
    private final String[] function_names;
    private Context context;

    FunctionAdapter(Context context) {
        this.context = context;
        function_names = context.getResources().getStringArray(R.array.function_name);
    }

    @NonNull
    @Override
    public FunctionViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        // 使用 LayoutInflater 產生一個 View 的畫面
        View functionView = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1
                        ,parent,false);

        return new FunctionViewHolder(functionView);
    }

    // To display data on the view
    @Override
    public void onBindViewHolder(@NonNull FunctionViewHolder holder, int position) {
        holder.txtName.setText(function_names[position]);
    }

    // 回傳資料筆數
    @Override
    public int getItemCount() {
        return function_names.length;
    }

    class FunctionViewHolder extends
            RecyclerView.ViewHolder {

        // Display function name
        TextView txtName;

        FunctionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(android.R.id.text1);
        }
    }
}
