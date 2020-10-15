package com.example.mainactivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
    SharedPreferences sharedPreferences;

    private static final String TAG = "Database";

    private static final String TABLE_NAME = "Users3";
    private static final String COL1 = "id";
    private static final String COL2 = "name";
    private static final String COL3 = "email";
    private static final String COL4 = "password";
    private static final String COL5 = "birthday";

    public Database(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL2 + " TEXT, " +
                        COL3 + " TEXT, " +
                        COL4 + " TEXT, " +
                        COL5 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean addUserToDatabase(String name, String email, String password, String birthday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name);
        contentValues.put(COL3, email);
        contentValues.put(COL4, password);
        contentValues.put(COL5, birthday);

        Log.d(TAG, "addData: Adding " + name + ", " + email + ", " + password + ", " + birthday + ", " + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateUserInDatabase(String id, String newName, String newBirthday, String newEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, newName);
        contentValues.put(COL3, newEmail);
        contentValues.put(COL5, newBirthday);

        Log.d(TAG, "addData: Updated " + newName + ", " + newEmail + ", " + newBirthday + ", " + " in " + TABLE_NAME);

        String whereClause = "id=?";
        String whereArgs[] = {id};
        //long result = db.insert(TABLE_NAME, null, contentValues);
        long result = db.update(TABLE_NAME, contentValues, "id=?", new String[]{whereClause});
        if (result == -1)
            return false;
        else
            return true;
    }
}
