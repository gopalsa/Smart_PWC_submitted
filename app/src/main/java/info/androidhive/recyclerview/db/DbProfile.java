package info.androidhive.recyclerview.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DbProfile extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "profileManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "profile";

    // Contacts Table Columns names
    private static final String KEY_vrpID = "vrpid";
    private static final String KEY_NAME = "name";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_DATA = "data";

    public DbProfile(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_vrpID + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_CONTACT + " TEXT,"
                + KEY_DATA + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new shgName
    public void addData(String vrpid, String name, String contact, String data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_vrpID, vrpid); // ShgName Name
        values.put(KEY_NAME, name); // ShgName Name
        values.put(KEY_CONTACT, contact); // ShgName Name
        values.put(KEY_DATA, data); // ShgName Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public ArrayList<String> getData(String vrpid, String name, String contact) {
        SQLiteDatabase db = this.getReadableDatabase();

        final Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_vrpID, KEY_NAME,
                        KEY_CONTACT, KEY_DATA}, KEY_vrpID + "=? AND " + KEY_NAME + "=? AND " + KEY_CONTACT + "=?",
                new String[]{vrpid, name, contact}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return new ArrayList<String>() {{
                add(cursor.getString(0));
                add(cursor.getString(1));
                add(cursor.getString(2));
                add(cursor.getString(3));
            }};
        }
        return null;
    }


    public ArrayList<String> getDataByName(String vrpid, String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        final Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_vrpID, KEY_NAME,
                        KEY_CONTACT, KEY_DATA}, KEY_vrpID + "=? AND " + KEY_NAME + "=?",
                new String[]{vrpid, name}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return new ArrayList<String>() {{
                add(cursor.getString(0));
                add(cursor.getString(1));
                add(cursor.getString(2));
                add(cursor.getString(3));
            }};
        }
        return null;
    }

    public List<ArrayList<String>> getAllData() {
        List<ArrayList<String>> dataList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        final Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ArrayList<String>() {{
                    add(cursor.getString(0));
                    add(cursor.getString(1));
                    add(cursor.getString(2));
                    add(cursor.getString(3));
                }});
            } while (cursor.moveToNext());
        }
        return dataList;
    }

    // Updating single shgName
    public int updatedata(String vrpid, String name,String oldContact, String newContact, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_vrpID, vrpid); // ShgName Name
        values.put(KEY_NAME, name); // ShgName Name
        values.put(KEY_CONTACT, newContact); // ShgName Name
        values.put(KEY_DATA, data); // ShgName Phone
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_vrpID + "=? AND " + KEY_NAME + "=? AND " + KEY_CONTACT + "=?",
                new String[]{vrpid, name, oldContact});
    }

    // Deleting single shgName
    public void deleteData(String vrpid, String name, String contact, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_vrpID + "=? AND " + KEY_NAME + "=? AND " + KEY_CONTACT + "=?",
                new String[]{vrpid, name, contact});
        db.close();
    }

    // Deleting single shgName
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, null,
                null);
        db.close();
    }
}
