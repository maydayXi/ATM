package com.home.atm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TransActivity extends AppCompatActivity {

    private static final String TAG = TransActivity.class.getSimpleName();
    private List<Transaction> transactions;
    private RecyclerView transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);

        processViews();

//        TransTask transTask = new TransTask();
//        transTask.execute("http://atm201605.appspot.com/h");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://atm201605.appspot.com/h")
                .build();

        client.newCall(request).enqueue(new Callback() {

            // <summary> 執行失敗的時候自動呼叫 </summary>
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            // <summary> 執行成功的時候呼叫 </summary>
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String jsonStr = Objects.requireNonNull(response.body()).string();
                Log.d(TAG, "onResponse: targetStr = " + jsonStr);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        parseJSON(jsonStr);
                        parseGSON(jsonStr);
                    }
                });
            }
        });
    }

    private void processViews() {
        transactionList = findViewById(R.id.transactionList);
        transactionList.setHasFixedSize(true);
        transactionList.setLayoutManager(new LinearLayoutManager(this));
        transactionList.setAdapter(null);
    }

    private void parseGSON(String jsonStr) {
        Gson gson = new Gson();
        Type transType = new TypeToken<ArrayList<Transaction>>(){}.getType();
        transactions = gson.fromJson(jsonStr, transType);

        for (int i = 0; i < transactions.size(); i++) {
            Log.d(TAG, "parseGSON: transactions[" + i + "].Account = "
                + transactions.get(i).getAccountStr() +
                "\tDate = " + transactions.get(i).getDateStr()
                + "\tAmount = " + transactions.get(i).getAmountValue()
                + "\tType = " + transactions.get(i).getTypeValue());
        }

        transAdapter adapter = new transAdapter();
        transactionList.setAdapter(adapter);
    }

    private void parseJSON(String json) {

        transactions = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                transactions.add(new Transaction(jsonObject));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        transAdapter adapter = new transAdapter();
        transactionList.setAdapter(adapter);
    }

    class transAdapter extends RecyclerView.Adapter<transAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView transDate, transAmount,  transType;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                transDate = itemView.findViewById(R.id.transDate);
                transAmount = itemView.findViewById(R.id.transAmount);
                transType = itemView.findViewById(R.id.transType);
            }

            void bindTo(Transaction transaction) {
                transDate.setText(transaction.getDateStr());
                transAmount.setText(String.valueOf(transaction.getAmountValue()));
                transType.setText(String.valueOf(transaction.getTypeValue()));
            }
        }

        @NonNull
        @Override
        public transAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().
                    inflate(R.layout.trans_item, parent,false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull transAdapter.ViewHolder holder, int position) {
            Transaction transaction = transactions.get(position);
            holder.bindTo(transaction);
        }

        @Override
        public int getItemCount() {
            return transactions.size();
        }
    }

//    class TransTask extends AsyncTask<String, Void, String> {
//
//        // <summary> 執行背景工作 </summary>
//        // <param name='strings'> 傳入的參數陣列 </param>
//        // <return> 回傳值，會傳到 onPostExecute 方法當參數 </return>
//        @Override
//        protected String doInBackground(String... strings) {
//
//            StringBuilder sb = new StringBuilder();
//
//            try {
//                URL url = new URL(strings[0]);
//                InputStream is = url.openStream();
//                BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                String lineStr = br.readLine();
//
//                while (lineStr != null) {
//                    sb.append(lineStr);
//                    lineStr = br.readLine();
//                }
//                Log.d(TAG, "doInBackground: " + sb.toString());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return sb.toString();
//        }
//
//        // <summary> 背景工作執行完後要執行的工作 </summary>
//        // <param name='s'> doInBackground 方法傳過來的參數 </param>
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            Log.d(TAG, "onPostExecute: " + s);
//        }
//    }
}
