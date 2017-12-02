package info.androidhive.recyclerview.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbReport extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "reportManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "report";

    // Contacts Table Columns names
    private static final String KEY_VRPID = "vrpid";
    private static final String KEY_TOOLNAME = "toolname";
    private static final String KEY_PAGENAME = "pagename";
    private static final String KEY_DATA = "data";

    public DbReport(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_VRPID + " TEXT,"
                + KEY_TOOLNAME + " TEXT,"
                + KEY_PAGENAME + " TEXT,"
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


    public void addData(String vrpid, String toolname, String pagename, String data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VRPID, vrpid); // ShgName Name
        values.put(KEY_TOOLNAME, toolname); // ShgName Phone
        values.put(KEY_PAGENAME, pagename); // ShgName Phone
        values.put(KEY_DATA, data); // ShgName Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    public String getData(String vrpid, String toolname, String pagename) {

        SQLiteDatabase db = this.getReadableDatabase();

        final Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_DATA,
                }, KEY_VRPID + "=? AND " + KEY_TOOLNAME + "=? AND " + KEY_PAGENAME + "=?",
                new String[]{vrpid, toolname, pagename}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        return null;
    }

    public int updatedata(String vrpid, String toolname, String pagename, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATA, data); // ShgName Phone
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_VRPID + "=? AND " + KEY_TOOLNAME + "=? AND " + KEY_PAGENAME + "=?",
                new String[]{vrpid, toolname, pagename});
    }

}
