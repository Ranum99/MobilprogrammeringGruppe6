package com.example.mainactivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static final String TAG = "Database";
    SharedPreferences sharedPreferences;

    // Felles for alle tabeller
    public static final String COLUMN_ID = "id";

    /*
                    USER
     */
    // Tabell USER m/ kolonner
    public static final String TABLE_USER = "Users3";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_BIRTHDAY = "birthday";
    public static final String COLUMN_MOBILNR = "mobilnr";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FAMILY = "family";

    // Lage tabellen USER
    private static final String CREATE_TABLE_USER = " CREATE TABLE " + TABLE_USER +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_BIRTHDAY + " TEXT, " +
            COLUMN_MOBILNR + " TEXT, " +
            COLUMN_PASSWORD + " TEXT, " +
            COLUMN_FAMILY + " INTEGER " +
            ")";

    /*
                    FAMILY
     */
    // Tabell FAMILY m/ kolonner
    public static final String TABLE_FAMILY = "family";
    public static final String COLUMN_FAMILY_NAME = "familyName";
    private static final String COLUMN_FAMILY_PASSWORD = "password";
    public static final String COLUMN_FAMILY_ADMIN_ID = "adminID";

    // Lage tabellen FAMILY
    private static final String CREATE_TABLE_FAMILY = " CREATE TABLE " + TABLE_FAMILY +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FAMILY_NAME + " TEXT, " +
            COLUMN_FAMILY_PASSWORD + " TEXT, " +
            COLUMN_FAMILY_ADMIN_ID + " INTEGER " +
            ")";

    /*
                    CONVERSATION
     */
    // Tabell CONVERSATION
    public static final String TABLE_CONVERSATION = "Conversation";
    public static final String COLUMN__USER_FROM = "userFrom";
    public static final String COLUMN__USER_TO = "userTo";
    public static final String COLUMN__CONVERSATION_NAME = "conversationName";

    // Lage tabellen CONVERSATION
    private static final String CREATE_TABLE_CONVERSATION = " CREATE TABLE " + TABLE_CONVERSATION +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN__USER_FROM + " INTEGER, " +
            COLUMN__USER_TO + " INTEGER, " +
            COLUMN__CONVERSATION_NAME + " TEXT " +
            ")";

    /*
                    MESSAGES
     */
    // Tabell MESSAGES
    public static final String TABLE_MESSAGES = "Messages";
    public static final String COLUMN__MESSAGE_PART_OF_CONVERSATIONID = "conversationID";
    public static final String COLUMN__MESSAGE_USER_FROM = "userFrom";
    public static final String COLUMN__MESSAGE_TEXT = "message";

    // Lage tabellen MESSAGES
    private static final String CREATE_TABLE_MESSAGES = " CREATE TABLE " + TABLE_MESSAGES +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN__MESSAGE_PART_OF_CONVERSATIONID + " INTEGER, " +
            COLUMN__MESSAGE_USER_FROM + " INTEGER, " +
            COLUMN__MESSAGE_TEXT + " TEXT " +
            ")";

    /*
                    WISHLIST
     */
    // Tabell WISHLIST
    public static final String TABLE_WISHLIST = "Wishlist";
    public static final String COLUMN__USER_ID_WISHLIST = "userIDWithlist";
    public static final String COLUMN__NAME_WISHLIST = "nameOfWishlist";

    // Lage tabellen WISHLIST
    private static final String CREATE_TABLE_WISHLIST = " CREATE TABLE " + TABLE_WISHLIST +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN__USER_ID_WISHLIST + " INTEGER, " +
            COLUMN__NAME_WISHLIST + " TEXT " +
            ")";

    /*
                    WISH
     */
    // Tabell WISH
    public static final String TABLE_WISH = "Wish";
    public static final String COLUMN__WISHLIST_ID = "wishlistID";
    public static final String COLUMN__NAME_WISH = "nameOfWish";
    public static final String COLUMN__WISH_CHECKED = "wishIsChecked";
    public static final String COLUMN__WISH_USER_ID = "userIDForWish";

    // Lage tabellen WISH
    private static final String CREATE_TABLE_WISH = " CREATE TABLE " + TABLE_WISH +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN__WISHLIST_ID + " INTEGER, " +
            COLUMN__NAME_WISH + " TEXT, " +
            COLUMN__WISH_CHECKED + " INTEGER, " +
            COLUMN__WISH_USER_ID + " INTEGER " +
            ")";

    /*
                    BIRTHDAY
     */
    // Tabell BIRTHDAY m/ kolonner
    public static final String TABLE_BIRTHDAY = "Bursdag";
    public static final String COLUMN_NAME_BIRTHDAY = "Navn";
    public static final String COLUMN_BIRTHDAY_DATE = "Dato";
    public static final String COLUMN_BIRTHDAY_FAMILYID = "FamilieId";
    public static final String COLUMN_BIRTHDAY_USERID = "UserID";
    public static final String COLUMN_BIRTHDAY_MADEBY_USERID = "MadeByUserID";

    // Lage tabellen BIRTHDAY
    private static final String CREATE_TABLE_BIRTHDAY = " CREATE TABLE " + TABLE_BIRTHDAY +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME_BIRTHDAY + " TEXT, " +
            COLUMN_BIRTHDAY_DATE + " TEXT, " +
            COLUMN_BIRTHDAY_FAMILYID + " TEXT, " +
            COLUMN_BIRTHDAY_USERID + " TEXT, " +
            COLUMN_BIRTHDAY_MADEBY_USERID + " TEXT " +
            ")";


    /*
                    HANDLELISTE
     */
    // Tabell HANDLELISTE m/ kolonner
    public static final String TABLE_HANDLELISTE = "Handleliste";
    public static final String COLUMN_HANDLELISTE_TITTEL = "Tittel";
    public static final String COLUMN_HANDLELISTE_USERID = "BrukerID";

    // Lage tabellen HANDLELISTE
    private static final String CREATE_TABLE_HANDLELISTE = " CREATE TABLE " + TABLE_HANDLELISTE +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_HANDLELISTE_TITTEL + " TEXT, " +
            COLUMN_HANDLELISTE_USERID + " INTEGER " +
            ")";


    /*
                    HANDLELISTE-LISTE
     */
    // Tabell HANDLELISTE m/ kolonner
    public static final String TABLE_HANDLELISTE_LISTE = "HandlelisteListe";
    public static final String COLUMN_HANDLELISTELISTE_ID = "ListeID";
    public static final String COLUMN_HANDLELISTELISTE_VARE = "Vare";

    // Lage tabellen HANDLELISTE
    private static final String CREATE_TABLE_HANDLELISTE_LISTE = " CREATE TABLE " + TABLE_HANDLELISTE_LISTE +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_HANDLELISTELISTE_ID + " TEXT, " +
            COLUMN_HANDLELISTELISTE_VARE + " TEXT " +
            ")";

    /*
                    MATPLAN
     */
    // Tabell MATPLAN m/ kolonner
    public static final String TABLE_MATPLAN = "Matplan";
    public static final String COLUMN_MATPLAN_UKE = "Uke_nr";

    private static final String CREATE_TABLE_MATPLAN = " CREATE TABLE " + TABLE_MATPLAN +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MATPLAN_UKE + " TEXT " +
            ")";


            // MATPLAN - LEGGER INN ANTALL DAGER OG STARTDAG

    public static final String TABLE_OPPRETT_MATPLAN = "OpprettMatplan";
    public static final String COLUMN_STARTDAG = "Startdag";
    public static final String COLUMN_ANTALL_DAGER = "Antalldager";

    private static final String CREATE_TABLE_OPPRETT_MATPLAN = " CREATE TABLE " + TABLE_OPPRETT_MATPLAN +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_STARTDAG + " TEXT, " +
            COLUMN_ANTALL_DAGER + " TEXT " +
            ")";


    /*
                    KALENDER
     */

    public static final String TABLE_CALENDAR_ACTIVITY = "KalenderActivity";
    public static final String COLUMN__CALENDAR_ACTIVITY_DATE_FROM = "DateFrom";
    public static final String COLUMN__CALENDAR_ACTIVITY_DATE_TO = "DateTo";
    public static final String COLUMN__CALENDAR_ACTIVITY_TIME_FROM = "TimeFrom";
    public static final String COLUMN__CALENDAR_ACTIVITY_TIME_TO = "TimeTo";
    public static final String COLUMN__CALENDAR_ACTIVITY_USER_ID = "UserID";
    public static final String COLUMN__CALENDAR_ACTIVITY_ACTIVITY = "Activity";

    private static final String CREATE_TABLE_CALENDAR = " CREATE TABLE " + TABLE_CALENDAR_ACTIVITY +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN__CALENDAR_ACTIVITY_DATE_FROM + " TEXT, " +
            COLUMN__CALENDAR_ACTIVITY_DATE_TO + " TEXT, " +
            COLUMN__CALENDAR_ACTIVITY_TIME_FROM + " TEXT, " +
            COLUMN__CALENDAR_ACTIVITY_TIME_TO + " TEXT, " +
            COLUMN__CALENDAR_ACTIVITY_USER_ID + " INTEGER, " +
            COLUMN__CALENDAR_ACTIVITY_ACTIVITY + " TEXT " +
            ")";





    public Database(Context context) {
        super(context, TAG, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CONVERSATION);
        db.execSQL(CREATE_TABLE_MESSAGES);
        db.execSQL(CREATE_TABLE_BIRTHDAY);
        db.execSQL(CREATE_TABLE_WISHLIST);
        db.execSQL(CREATE_TABLE_WISH);
        db.execSQL(CREATE_TABLE_FAMILY);
        db.execSQL(CREATE_TABLE_MATPLAN);
        db.execSQL(CREATE_TABLE_OPPRETT_MATPLAN);
        db.execSQL(CREATE_TABLE_HANDLELISTE);
        db.execSQL(CREATE_TABLE_HANDLELISTE_LISTE);
        db.execSQL(CREATE_TABLE_CALENDAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONVERSATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIRTHDAY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HANDLELISTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HANDLELISTE_LISTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISHLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAMILY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATPLAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALENDAR_ACTIVITY);
        onCreate(db);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER;
        return db.rawQuery(query, null);
    }

    public Cursor getIdOfUserData(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER +
                " WHERE " + COLUMN_EMAIL + " = ?";
        return db.rawQuery(query, new String[]{email});
    }

    public Cursor getData(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + table;
        return db.rawQuery(query, null);
    }

    public Cursor getData(String table, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + table + " WHERE " + COLUMN_ID + " = " + id;
        return db.rawQuery(query, null);
    }

    public Cursor getAllMessageFromConversation(int samtaleID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MESSAGES + " WHERE " + COLUMN__MESSAGE_PART_OF_CONVERSATIONID + " = " + samtaleID;
        return db.rawQuery(query, null);
    }

    public Cursor getAllWishesFromWishlist(int wishlistID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_WISH + " WHERE " + COLUMN__WISHLIST_ID + " = " + wishlistID;
        return db.rawQuery(query, null);
    }

    public Cursor getAlleVarerFraHandleliste(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HANDLELISTE_LISTE + " WHERE " + COLUMN_HANDLELISTELISTE_ID + " = " + id;
        return db.rawQuery(query, null);
    }


    public boolean deleteRowFromTableById(String table, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(table, COLUMN_ID + " = " + id, null);

        return result != -1;
    }

    public boolean addWishlistToUser(int userId, String whislistName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN__USER_ID_WISHLIST, userId);
        contentValues.put(COLUMN__NAME_WISHLIST, whislistName);

        long result = db.insert(TABLE_WISHLIST, null, contentValues);

        return result != -1;
    }

    public boolean updateOneColumnFromTable(String table, String column, String newColumnValue, String whereClauseColumn, String whereArgsValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column, newColumnValue);

        long result = db.update(table, contentValues, whereClauseColumn + " = " + whereArgsValue, null);

        return result != -1;
    }

    public boolean updateUserFamily(int userId, Integer familyId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FAMILY, familyId);

        long result = db.update(TABLE_USER, contentValues, COLUMN_ID + " = " + userId, null);

        return result != -1;
    }

    public boolean updateFamilyName(int familyId, String newFamilyName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FAMILY_NAME, newFamilyName);

        long result = db.update(TABLE_FAMILY, contentValues, COLUMN_ID + " = " + familyId, null);

        return result != -1;
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

    public boolean addFamilyToDatabase(String name, String password, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FAMILY_NAME, name);
        contentValues.put(COLUMN_FAMILY_PASSWORD, password);
        contentValues.put(COLUMN_FAMILY_ADMIN_ID, userId);

        Log.d(TAG, "addData: Adding " + name + ", " + password + ", admin: " + userId + ", to " + TABLE_FAMILY);

        long result = db.insert(TABLE_FAMILY, null, contentValues);

        return result != -1;
    }

    public Cursor sjekkOmFamilieEksisterer(String familyID, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FAMILY +
                " WHERE " + COLUMN_ID + " = " + familyID +
                " AND " + COLUMN_FAMILY_PASSWORD + " = " + password;
        return db.rawQuery(query, null);
    }

    public Cursor getFamilyIdByLastRow() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FAMILY  + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
        return db.rawQuery(query, null);
    }

    public long makeNewConversation(int meID, User otherUser, String conversationName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN__USER_FROM, meID);
        values.put(COLUMN__USER_TO, otherUser.id);
        values.put(COLUMN__CONVERSATION_NAME, conversationName);

        System.out.println("New conversation (Database): " + meID + " - " + otherUser.id + " named: " + conversationName);

        return db.insert(TABLE_CONVERSATION, null, values);
    }

    public long sendMessage(int samtaleId, int meID, String message) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN__MESSAGE_PART_OF_CONVERSATIONID, samtaleId);
        values.put(COLUMN__MESSAGE_USER_FROM, meID);
        values.put(COLUMN__MESSAGE_TEXT, message);

        System.out.println("New conversation (Database): \nIn conversation " + samtaleId + " \nFrom: " + meID + " \nMessage: " + message);

        return db.insert(TABLE_MESSAGES, null, values);
    }

    public Cursor getAllConversations(int meID) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CONVERSATION +
                " WHERE " + COLUMN__USER_FROM + " = " + meID +
                " OR " + COLUMN__USER_TO + " = " + meID;

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
        String[] whereArgs = {id};
        //long result = db.insert(TABLE_NAME, null, contentValues);
        long result = db.update(TABLE_USER, contentValues, COLUMN_ID + " = " + id, null);
        return result != -1;
    }

    public boolean makeNewMessageChannelWith(User selectedUser) {
        System.out.println("BRUKER: " + selectedUser);
        return true;
    }


    public Cursor checkIfUserIsAdminOfFamily(String familyID, String userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FAMILY +
                " WHERE " + COLUMN_ID + " = " + familyID +
                " AND " + COLUMN_FAMILY_ADMIN_ID + " = " + userID;
        return db.rawQuery(query, null);
    }



    /*
                BURSDAGER
     */
    // LEGG TIL BURSDAG
    public boolean addUserToDatabaseBIRTHDAY(String name, String date, String familyId, String meID, String madeByUserID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_BIRTHDAY, name);
        contentValues.put(COLUMN_BIRTHDAY_DATE, date);
        contentValues.put(COLUMN_BIRTHDAY_FAMILYID, familyId);
        contentValues.put(COLUMN_BIRTHDAY_USERID, meID);
        contentValues.put(COLUMN_BIRTHDAY_MADEBY_USERID, madeByUserID);

        Log.d(TAG, "addData: Adding " + name + ", " + date + ", " + familyId + ", " + " to " + TABLE_BIRTHDAY);

        long result = db.insert(TABLE_BIRTHDAY, null, contentValues);
        return result != -1;
    }
    // OPPDATER BURSDAG
    public boolean updateBirthday(String id, String newName, String newBirthday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_BIRTHDAY, newName);
        contentValues.put(COLUMN_BIRTHDAY_DATE, newBirthday);


        Log.d(TAG, "Birthday updated: " + newName + ", " + newBirthday + ", " + " in " + TABLE_BIRTHDAY);

        String whereClause = "id=?";
        String whereArgs[] = {id};
        long result = db.update(TABLE_BIRTHDAY, contentValues, "id=?", whereArgs);
        return result != -1;
    }


    /*
                    MATPLANER
     */

    // LEGGER INN MIDLERTIDIG DATA FOR REGISTRERING AV MATPLAN
    public boolean addTempDataMatplan(String id, String antallDager, String startDag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ANTALL_DAGER, antallDager);
        contentValues.put(COLUMN_STARTDAG, startDag);

        Log.d(TAG, "TempDataMatPlan updated: " + antallDager + ", " + startDag + ", " + " in " + TABLE_OPPRETT_MATPLAN);

        String whereClause = "id=?";
        String whereArgs[] = {id};
        long result = db.update(TABLE_OPPRETT_MATPLAN, contentValues, "id=?", whereArgs);
        return result != -1;
    }



    // LEGGER UKENE INN I MATPLAN
    public boolean addWeekToMatplan(String uke) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MATPLAN_UKE, uke);

        Log.d(TAG, "addData: Adding " + uke + ", " + " to " + TABLE_MATPLAN);

        long result = db.insert(TABLE_MATPLAN, null, contentValues);
        return result != -1;
    }
    // OPPDATERER UKENE I DATABASEN
    public boolean changeWeekMatplan (String id, String nyUke) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MATPLAN_UKE, nyUke);


        Log.d(TAG, "Matplan updated: " + nyUke + ", " + " in " + TABLE_MATPLAN);

        String whereClause = "id=?";
        String whereArgs[] = {id};
        //long result = db.insert(TABLE_NAME, null, contentValues);
        long result = db.update(TABLE_MATPLAN, contentValues, "id=?", whereArgs);
        return result != -1;

    }

    /*
                    HANDLELISTE
     */
    public boolean weekHandleliste(String tittel, int userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_HANDLELISTE_TITTEL, tittel);
        contentValues.put(COLUMN_HANDLELISTE_USERID, userID);

        Log.d(TAG, "Handleliste updated: " + tittel +  " in " + TABLE_HANDLELISTE);

        long result = db.insert(TABLE_HANDLELISTE, null, contentValues);
        return result != -1;
    }
    public long addvarerHandleliste(String vare, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_HANDLELISTELISTE_VARE, vare);
        contentValues.put(COLUMN_HANDLELISTELISTE_ID, id);

        Log.d(TAG, "Handleliste updated: " + vare + " in " + TABLE_HANDLELISTE_LISTE);

        return  db.insert(TABLE_HANDLELISTE_LISTE, null, contentValues);
    }

    public long makeNewWishlist(int meID, String wishlistName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN__USER_ID_WISHLIST, meID);
        values.put(COLUMN__NAME_WISHLIST, wishlistName);

        System.out.println("New wishlist (Database): " + meID + " named: " + wishlistName);

        return db.insert(TABLE_WISHLIST, null, values);
    }

    public long addWishToWishlist(int wishlistID, String wish, int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN__WISHLIST_ID, wishlistID);
        values.put(COLUMN__NAME_WISH, wish);
        values.put(COLUMN__WISH_CHECKED, 0);
        values.put(COLUMN__WISH_USER_ID, userID);

        System.out.println("New wish (Database): " + wish + "\nIn wishlist " + wishlistID + "\nIsChecked " + 0 + "\nUserID " + userID);

        return db.insert(TABLE_WISH, null, values);

    }

    public boolean updateCheckBoxForWish(int wishID, int isChecked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN__WISH_CHECKED, isChecked);

        long result = db.update(TABLE_WISH, contentValues, COLUMN_ID + " = " + wishID, null);

        return result != -1;
    }

    public boolean editActivityInCalandar(int activityID, String dateFrom, String dateTo, String timeFrom, String timeTo, String theActivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN__CALENDAR_ACTIVITY_DATE_FROM, dateFrom);
        contentValues.put(COLUMN__CALENDAR_ACTIVITY_DATE_TO, dateTo);
        contentValues.put(COLUMN__CALENDAR_ACTIVITY_TIME_FROM, timeFrom);
        contentValues.put(COLUMN__CALENDAR_ACTIVITY_TIME_TO, timeTo);
        contentValues.put(COLUMN__CALENDAR_ACTIVITY_ACTIVITY, theActivity);

        long result = db.update(TABLE_CALENDAR_ACTIVITY, contentValues, COLUMN_ID + " = " + activityID, null);

        return result != -1;
    }

    public long addActivityToCalandar(String dateFrom, String dateTo, String timeFrom, String timeTo, int meID, String theActivity) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN__CALENDAR_ACTIVITY_DATE_FROM, dateFrom);
        values.put(COLUMN__CALENDAR_ACTIVITY_DATE_TO, dateTo);
        values.put(COLUMN__CALENDAR_ACTIVITY_TIME_FROM, timeFrom);
        values.put(COLUMN__CALENDAR_ACTIVITY_TIME_TO, timeTo);
        values.put(COLUMN__CALENDAR_ACTIVITY_USER_ID, meID);
        values.put(COLUMN__CALENDAR_ACTIVITY_ACTIVITY, theActivity);

        System.out.println("New activity in calendar (Database): " + theActivity + " for user: " + meID);

        return db.insert(TABLE_CALENDAR_ACTIVITY, null, values);
    }
}
