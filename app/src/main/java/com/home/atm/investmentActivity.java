package com.home.atm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class investmentActivity extends AppCompatActivity {

    private RecyclerView dataList;
    private dataAdapter adapter;
    private investmentHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddIntent = new Intent(investmentActivity.this,
                        AddActivity.class);
                startActivity(AddIntent);
            }
        });


        dataHelper = new investmentHelper(this);
        Cursor dataCursor = dataHelper.getReadableDatabase().query("investment",
                null , null, null,
                null, null, null);
        adapter = new dataAdapter(dataCursor);


        dataList = findViewById(R.id.dataList);
        dataList.setHasFixedSize(true);
        dataList.setLayoutManager(new LinearLayoutManager(this));
        dataList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor dataCursor = dataHelper.getReadableDatabase().query("investment",
                null , null, null,
                null, null, null);
        adapter = new dataAdapter(dataCursor);
        dataList.setAdapter(adapter);

    }

    class dataAdapter extends RecyclerView.Adapter<dataAdapter.ViewHolder> {

        Cursor data;

        dataAdapter(Cursor data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(
                    R.layout.data_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            data.moveToPosition(position);
            String dateStr = data.getString(data.getColumnIndex("idate"));
            String descriptionStr = data.getString(data.getColumnIndex("description"));
            String amountStr = String.valueOf(data.getInt(data.getColumnIndex("amount")));

            holder.dataDate.setText(dateStr);
            holder.dataDescription.setText(descriptionStr);
            holder.dataAmount.setText(amountStr);
        }

        @Override
        public int getItemCount() {
            return data.getCount();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView dataDate, dataDescription, dataAmount;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                dataDate = itemView.findViewById(R.id.dataDate);
                dataDescription = itemView.findViewById(R.id.dataDescription);
                dataAmount = itemView.findViewById(R.id.dataAmount);
            }
        }
    }
}
