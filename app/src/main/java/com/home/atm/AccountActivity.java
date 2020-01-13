package com.home.atm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {

    ListView accountFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        accountFunctions = findViewById(R.id.actFuctions);
        accountFunctions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case  0:
                        Intent contactIntent = new Intent(AccountActivity.this,
                                ContactActivity.class);
                        startActivity(contactIntent);
                        break;
                    case 1:
                        break;
                    default:
                        break;
                }
            }
        });
    }


}
