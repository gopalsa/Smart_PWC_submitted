package smart.cst.pwc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DbImage extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "imageManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "image";

    // Contacts Table Columns names
    private static final String KEY_VRPID = "vrpid";
    private static final String KEY_TOOLNAME = "toolname";
    private static final String KEY_PAGENAME = "pagename";
    private static final String KEY_IMAGENAME = "imagename";
    private static final String KEY_DATA = "data";

    public DbImage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_VRPID + " TEXT,"
                + KEY_TOOLNAME + " TEXT,"
                + KEY_PAGENAME + " TEXT,"
                + KEY_IMAGENAME + " TEXT,"
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


    public void addData(String vrpid, String toolname, String pagename, String imagename, String data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VRPID, vrpid); // ShgName Name
        values.put(KEY_TOOLNAME, toolname); // ShgName Phone
        values.put(KEY_PAGENAME, pagename); // ShgName Phone
        values.put(KEY_IMAGENAME, imagename); // ShgName Phone
        values.put(KEY_DATA, data); // ShgName Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    public String getData(String vrpid, String toolname, String pagename, String imagename) {

        SQLiteDatabase db = this.getReadableDatabase();

        final Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_DATA,
                }, KEY_VRPID + "=? AND " + KEY_TOOLNAME + "=? AND " + KEY_PAGENAME + "=? AND " + KEY_IMAGENAME + "=?",
                new String[]{vrpid, toolname, pagename, imagename}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        return null;
    }

    public int updatedata(String vrpid, String toolname, String pagename,
                          String oldimagename, String imagename, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATA, data); // ShgName Phone
        values.put(KEY_IMAGENAME, imagename); // ShgName Phone
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_VRPID + "=? AND "
                        + KEY_TOOLNAME + "=? AND " + KEY_PAGENAME + "=? AND " + KEY_IMAGENAME + "=?",
                new String[]{vrpid, toolname, pagename, oldimagename});
    }
    public int updatedataAlone(String vrpid, String toolname, String pagename,String imagename, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATA, data); // ShgName Phone
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_VRPID + "=? AND "
                        + KEY_TOOLNAME + "=? AND " + KEY_PAGENAME + "=? AND " + KEY_IMAGENAME + "=?",
                new String[]{vrpid, toolname, pagename, imagename});
    }

    public List<String> getAllData(String vrpid, String toolname, String pagename) {
        List<String> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_DATA}, KEY_VRPID
                        + "=? AND " + KEY_TOOLNAME + "=? AND " + KEY_PAGENAME + "=?",
                new String[]{vrpid, toolname, pagename}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return dataList;
    }

    public List<String> getAllData(String vrpid, String toolname) {
        List<String> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_DATA}, KEY_VRPID
                        + "=? AND " + KEY_TOOLNAME + "=?",
                new String[]{vrpid, toolname}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return dataList;
    }

    public List<String> getAllData(String vrpid) {
        List<String> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_DATA}, KEY_VRPID
                        + "=?",
                new String[]{vrpid}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return dataList;
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
                    add(cursor.getString(4));
                    add("image");
                }});
            } while (cursor.moveToNext());
        }
        return dataList;
    }
}
