package first.com.movie_player;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawan on 14-Aug-16.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 13;
    private static final String DB_NAME = "DATABASE";


    private static final String TABLE_RECORD = "RECORD";
    private static final String TABLE_DATA = "DATA";


    private static final String KEY_NAME = "Name";
    private static final String KEY_lOCATION = "Location";

    private static final String KEY_POSITION = "Position";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_NOTES_TABLE = "Create Table " + TABLE_RECORD + "("
                + KEY_NAME + " Text,"
                + KEY_lOCATION + " Text " + ")";

        db.execSQL(CREATE_NOTES_TABLE);

        String CREATE_DATA_TABLE = "Create Table " + TABLE_DATA + "("
                + KEY_POSITION + " Text " + ")";

        db.execSQL(CREATE_DATA_TABLE);

       }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }

    public void new_note(String Name, String loacation ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Name);
        values.put(KEY_lOCATION, loacation);

        db.insert(TABLE_RECORD, null, values);
        Log.d("query", String.valueOf(TABLE_RECORD));

    }

    public void add_data(int position){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_POSITION, position);

        db.insert(TABLE_DATA, null, values);

    }

    public List<String> access_data(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> pos_note=new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_RECORD;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            do {

                pos_note.add(cursor.getString(1));

            } while(cursor.moveToNext());

        }
        cursor.close();
        Log.d("note123", String.valueOf(pos_note));
        return pos_note;

    }



    public List<String> access_position(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> note=new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_DATA;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            do {

                note.add(cursor.getString(0));

            } while(cursor.moveToNext());

        }
        cursor.close();
        Log.d("note12", String.valueOf(note));
        return note;

    }




    public void resetTable_Records() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_RECORD, null, null);
        db.delete(TABLE_DATA, null, null);

    }

}

