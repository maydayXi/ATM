package com.home.atm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class investmentHelper extends SQLiteOpenHelper {

    public investmentHelper(Context context) {
        this(context, "ATMDB", null, 1);
    }

    // 建構子
    // <param name='context'> 使用資料庫的 Activity </param>
    // <param name='name'> 資料庫名稱 </param>
    // <param name='factory'> 資料庫名稱 </param>
    // <param name='version'> 資料庫版本 </param>
    private investmentHelper(@Nullable Context context,
                     @Nullable String name,
                     @Nullable SQLiteDatabase.CursorFactory factory,
                     int version) {
        super(context, name, factory, version);
    }

    // <summary> 產生資料表 </summary>
    // <param name='sqLiteDatabase'> 資料庫物件，對資料庫進行操作 </param>
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // execSQL("sqlCommand") 執行 SQL 語法
        sqLiteDatabase.execSQL("CREATE TABLE investment(_id INTEGER NOT NULL PRIMARY KEY, " +
                "idate VARCHAR NOT NULL, " +
                "description VARCHAR," +
                "amount INTEGER)");
    }

    // <summary> 資料庫更新時的版本資訊 </summary>
    // <param name='i"> 舊版本 </summary>
    // <param name='i1"> 新版本 </summary>
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
