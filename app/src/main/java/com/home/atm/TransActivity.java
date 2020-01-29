package com.home.atm;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TransActivity extends AppCompatActivity {

    private static final String TAG = TransActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);

//        TransTask transTask = new TransTask();
//        transTask.execute("http://atm201605.appspot.com/h");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://atm201605.appspot.com/h")
                .build();

        Call call =  client.newCall(request);
        call.enqueue(new Callback() {

            // <summary> 執行失敗的時候自動呼叫 </summary>
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            // <summary> 執行成功的時候呼叫 </summary>
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "onResponse: " + Objects.requireNonNull(response.body()).string());
            }
        });
    }

    class TransTask extends AsyncTask<String, Void, String> {

        // <summary> 執行背景工作 </summary>
        // <param name='strings'> 傳入的參數陣列 </param>
        // <return> 回傳值，會傳到 onPostExecute 方法當參數 </return>
        @Override
        protected String doInBackground(String... strings) {

            StringBuilder sb = new StringBuilder();

            try {
                URL url = new URL(strings[0]);
                InputStream is = url.openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String lineStr = br.readLine();

                while (lineStr != null) {
                    sb.append(lineStr);
                    lineStr = br.readLine();
                }
                Log.d(TAG, "doInBackground: " + sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return sb.toString();
        }

        // <summary> 背景工作執行完後要執行的工作 </summary>
        // <param name='s'> doInBackground 方法傳過來的參數 </param>
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: " + s);
        }
    }
}
