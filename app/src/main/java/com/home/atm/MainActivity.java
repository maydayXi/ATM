package com.home.atm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOGIN = 100;
    boolean logon = false;
    private List<mFunction> functions;

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN) {
            if(resultCode != RESULT_OK) {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!logon) {
            Intent loginIntent = new Intent(
                    this, LoginActivity.class);
            startActivityForResult(loginIntent, REQUEST_LOGIN);
        }

        findViews();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void findViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
//        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setLayoutManager(new GridLayoutManager(this,3));


        // Add Adapter
//        FunctionAdapter functionAdapter = new FunctionAdapter(this);
//        recycler.setAdapter(functionAdapter);

        // Set up functions
        setUpFunctions();
        ItemAdapter itemAdapter = new ItemAdapter();
        recycler.setAdapter(itemAdapter);
    }

    private void setUpFunctions() {

        // Get function string array
        String[] function_name_lst = getResources()
                .getStringArray(R.array.function_name);
        functions = new ArrayList<>();
        functions.add(new mFunction(function_name_lst[0],R.drawable.transaction));
        functions.add(new mFunction(function_name_lst[1],R.drawable.banlance));
        functions.add(new mFunction(function_name_lst[2],R.drawable.investment));
        functions.add(new mFunction(function_name_lst[3],R.drawable.management));
        functions.add(new mFunction(function_name_lst[4],R.drawable.logout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected class ItemAdapter extends RecyclerView.Adapter
            <ItemAdapter.itemViewHolder>{

        @NonNull
        @Override
        public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.item_icon,
                            parent,false);
            return new itemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull itemViewHolder holder, final int position) {
            holder.txtFunction.setText(
                    functions.get(position).getName());
            holder.imgFunction.setImageResource(
                    functions.get(position).getIcons_res());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClicked(functions.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return functions.size();
        }

        class itemViewHolder extends RecyclerView.ViewHolder {

            // Display function icon and function name
            ImageView imgFunction;
            TextView txtFunction;

            itemViewHolder(@NonNull View itemView) {
                super(itemView);

                imgFunction = itemView.findViewById(R.id.img_function);
                txtFunction = itemView.findViewById(R.id.txt_function);
            }
        }
    }

    private void itemClicked(mFunction function) {
        switch (function.getIcons_res()) {
            case R.drawable.transaction:
                Intent transIntent = new Intent(this, TransActivity.class);
                startActivity(transIntent);
                break;
            case R.drawable.banlance:
                break;
            case R.drawable.investment:
                Intent investmentIntent = new Intent(this,
                        investmentActivity.class);
                startActivity(investmentIntent);
                break;
            case R.drawable.management:     // 帳戶管理
                Intent accountIntent = new Intent(this,
                        AccountActivity.class);
                startActivity(accountIntent);
                break;
            case R.drawable.logout:         // 登出(離開)
                finish();
                break;
        }
    }
}
