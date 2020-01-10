package com.home.atm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

// <summary> 帳號管理類別 </summary>
// <Detail> 可存取聯絡人資訊作為帳號轉帳等其他用途 </Detail>
public class AccountActivity extends AppCompatActivity {

    private static final String TAG = AccountActivity.class.getSimpleName();
    private int REQUEST_CONTACTS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // 取得使用者有沒有拿到聯絡人權限
        int hasPermission = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_CONTACTS);
        // 有就可以存取聯絡人
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            readContacts();
        // 沒有權限就詢問
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_CONTACTS);
        }
    }

    // <summary> 權限的 CallBack 方法 </summary>
    // <param name='requestCode'> 檢核碼 </param>
    // <param name='permissions'> 要求的權限 </param>
    // <param name='grantResults'> 是否同意(結果) </param>
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 如果使用者同意，回傳需求碼 = 傳過去的
        if (requestCode == REQUEST_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readContacts();
            }
        }
    }

    // 讀取聯絡人資料
    private void readContacts() {
        Cursor contactCursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null ,
                null,
                null
        );

        if (contactCursor != null) {
            while (contactCursor.moveToNext()) {

                // 取得系統聯絡人 id
                int id = contactCursor.getInt(
                        contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
                // 取得聯絡人姓名
                String name = contactCursor.getString(contactCursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                // 取得聯絡人是否有電話
                // 至少有一筆號碼回傳 1，沒有號碼回傳 0
                int hasPhone = contactCursor.getInt(
                        contactCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                // 產生一個新的聯絡人物件
                Account account = new Account(id,name);

                if (hasPhone == 1) {
                    Cursor phoneCursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            new String[]{String.valueOf(id)},
                            null
                    );
                    if (phoneCursor != null) {
                        while (phoneCursor.moveToNext()) {
                            // 取得電話號碼
                            String phoneNumberStr = phoneCursor.getString(
                                    phoneCursor.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Phone.DATA));
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "No Contacts data",
                    Toast.LENGTH_LONG).show();
        }
    }
}
