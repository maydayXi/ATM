package com.home.atm;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = AddActivity.class.getSimpleName();
    EditText edtDate, edtDescription, edtAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        processView();
    }

    private void processView() {
        edtDate = findViewById(R.id.edtDate);
        edtDescription = findViewById(R.id.edtDescription);
        edtAmount = findViewById(R.id.edtAmount);
    }

    public void add(View view) {
        String dateStr = edtDate.getText().toString();
        String descriptionStr = edtDescription.getText().toString();
        int amountValue = Integer.parseInt(edtAmount.getText().toString());

        // 建立資料庫操作物件
        investmentHelper dataHelper = new investmentHelper(this);

        // 產生資料物件
        ContentValues values = new ContentValues();
        // put 方法：將資料打包
        // 參數一：欄位名稱
        // 參數二：資料值
        values.put("idate", dateStr);
        values.put("description", descriptionStr);
        values.put("amount", amountValue);

        // 新增資料，新增成功傳回資料的 id 值
        // insert 方法：新增資料
        // 參數一：資料表名稱
        // 參數二：
        // 參數三：要新增的資料物件
        long _id = dataHelper.getWritableDatabase().insert("investment",
                null, values);
        Log.d(TAG, "add: ID = " + _id);
        if (_id > -1) {
            Toast.makeText(this, "資料新增成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "資料新增失敗", Toast.LENGTH_LONG).show();
        }
    }
}
