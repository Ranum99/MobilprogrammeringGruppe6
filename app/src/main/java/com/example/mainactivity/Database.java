package com.example.mainactivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    SharedPreferences sharedPreferences;

    private static final String TAG = "Database";

    // Felles for alle tabeller
    private static final String COLUMN_ID = "id";

    // Tabell USER m/ kolonner
    private static final String TABLE_USER = "Users3";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_BIRTHDAY = "birthday";
    private static final String COLUMN_MOBILNR = "mobilnr";
    private static final String COLUMN_PASSWORD = "password";

    // Lage tabellen USER
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER +
            "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_BIRTHDAY + " TEXT, " +
                COLUMN_MOBILNR + " TEXT, " +
                COLUMN_PASSWORD + " TEXT " +
            ")";


    // Tabell CONVERSATION
    private static final String TABLE_CONVERSATION = "Conversation";
    private static final String COLUMN__USER_FROM = "userFrom";
    private static final String COLUMN__USER_TO = "userTo";

    // Lage tabellen CONVERSATION
    private static final String CREATE_TABLE_CONVERSATION = "CREATE TABLE " + TABLE_CONVERSATION +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN__USER_FROM + " INTEGER, " +
            COLUMN__USER_TO + " INTEGER " +
            ")";


    // Tabell BIRTHDAY m/ kolonner
    private static final String TABLE_BIRTHDAY = "Bursdag";
    private static final String COLUMN_NAME_BIRTHDAY = "Navn";
    private static final String COLUMN_BIRTHDAY_DATE = "Dato";

    // Lage tabellen BIRTHDAY
    private static final String CREATE_TABLE_BIRTHDAY = "CREATE TABLE " + TABLE_BIRTHDAY +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME_BIRTHDAY + " TEXT, " +
            COLUMN_BIRTHDAY_DATE + " TEXT " +
            ")";


    public Database(Context context) {
        super(context, TAG, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CONVERSATION);
        db.execSQL(CREATE_TABLE_BIRTHDAY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONVERSATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIRTHDAY);
        onCreate(db);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER;
        return db.rawQuery(query, null);
    }

    public boolean addUserToDatabase(String name, String email, String birthday, String mobilnr, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_BIRTHDAY, birthday);
        contentValues.put(COLUMN_MOBILNR, mobilnr);
        contentValues.put(COLUMN_PASSWORD, password);

        Log.d(TAG, "addData: Adding " + name + ", " + email + ", " + birthday + ", " + mobilnr + ", " + password + ", " + " to " + TABLE_USER);

        long result = db.insert(TABLE_USER, null, contentValues);

        return result != -1;
    }

    public long makeNewConversation(User otherUser) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN__USER_FROM, sharedPreferences.getString(User.ID, null));
        values.put(COLUMN__USER_TO, otherUser.id);

        return db.insert(TABLE_CONVERSATION, null, values);
    }

    public Cursor getAllConversations() {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CONVERSATION;

        return db.rawQuery(selectQuery,  null);
    }

    public boolean updateUserInDatabase(String id, String newName, String newEmail, String newBirthday, String newMobilnr) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, newName);
        contentValues.put(COLUMN_EMAIL, newEmail);
        contentValues.put(COLUMN_BIRTHDAY, newBirthday);
        contentValues.put(COLUMN_MOBILNR, newMobilnr);

        Log.d(TAG, "addData: Updated " + newName + ", " + newEmail + ", " + newBirthday + ", " + newMobilnr + ", " + " in " + TABLE_USER);

        String whereClause = "id=?";
        String whereArgs[] = {id};
        //long result = db.insert(TABLE_NAME, null, contentValues);
        long result = db.update(TABLE_USER, contentValues, "id=?", new String[]{whereClause});
        return result != -1;
    }

    public boolean makeNewMessageChannelWith(User selectedUser) {
        System.out.println("BRUKER: " + selectedUser);
        return true;
    }

    public boolean addUserToDatabaseBIRTHDAY(String name, String phone, String date) {
        System.out.println(name + phone + date);
        return true;
    }
}
