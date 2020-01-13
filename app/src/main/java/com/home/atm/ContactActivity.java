package com.home.atm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// <summary> 聯絡人管理類別 </summary>
// <Detail> 可存取聯絡人資訊作為帳號轉帳等其他用途 </Detail>
public class ContactActivity extends AppCompatActivity {

    private static final String TAG = ContactActivity.class.getSimpleName();
    private int REQUEST_CONTACTS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

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

        // 建立一個聯絡人的集合物件
        List<Contact> contactList = new ArrayList<>();
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
                Contact contact = new Contact(id,name);

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

                            contact.getContact_lst().add(phoneNumberStr);
                        }
                        phoneCursor.close();
                    }
                }
                // 集合加入聯絡人物件
                contactList.add(contact);
            }

            ContactAdapter contactAdapter =new ContactAdapter(contactList);
            RecyclerView phones = findViewById(R.id.phones);
            phones.setAdapter(contactAdapter);
            phones.setHasFixedSize(true);
            phones.setLayoutManager(new LinearLayoutManager(this));

            contactCursor.close();
        } else {
            Toast.makeText(this, "No Contacts data",
                    Toast.LENGTH_LONG).show();
        }

    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder>{

        List<Contact> contacts;
        ContactAdapter(List<Contact> contactList) {
            contacts = contactList;
        }

        // 初始化畫面元件
        @NonNull
        @Override
        public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    android.R.layout.simple_list_item_2, parent, false);
            return new ContactHolder(view);
        }

        // 畫面元件綁定資料
        @Override
        public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
            Contact contact = contacts.get(position);
            holder.txtUserName.setText(contact.name);

            StringBuilder sb = new StringBuilder();
            for (String number : contact.getContact_lst()) {
                sb.append(number);
                sb.append(" ");
            }
            holder.txtUserPhone.setText(sb.toString());
        }

        @Override
        public int getItemCount() {
            return contacts.size();
        }

        class ContactHolder extends RecyclerView.ViewHolder {
            // 顯示聯絡人姓名、電話
            TextView txtUserName, txtUserPhone;

            // 初始化顯示畫面上的元件
            ContactHolder(@NonNull View itemView) {
                super(itemView);
                txtUserName = itemView.findViewById(android.R.id.text1);
                txtUserPhone = itemView.findViewById(android.R.id.text2);
            }
        }
    }
}
