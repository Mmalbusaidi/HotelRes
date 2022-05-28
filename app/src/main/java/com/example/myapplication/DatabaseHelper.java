package com.example.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME="hotel.db";
    public static final String TABLE_NAME="Room";
    public static final String COL_1="ID";
    public static final String COL_2="Room_Number";
    public static final String COL_3="Room_Type";
    public static final String COL_4="Floor_Number";
    public static final String COL_5="Number_of_Days";
    public static final String COL_6="Availability_of_Room";
    public DatabaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,1);
    }
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " +TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Room_Number INTEGER,Room_Type TEXT,Floor_Number INTEGER, Number_of_Days INTEGER, Availability_of_Room INTEGER )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db); }

        public boolean insertData(String RoNum, String RoType, String Floor, String days, String availability)
{
    SQLiteDatabase db=this.getReadableDatabase();
    ContentValues contentValues=new ContentValues();
    contentValues.put(COL_2,RoNum);
    contentValues.put(COL_3,RoType);
    contentValues.put(COL_4,Floor);
    contentValues.put(COL_5,days);
    contentValues.put(COL_6,availability);
    long result=db.insert(TABLE_NAME,null,contentValues);
    if(result==-1) return false;
    else return true;
}
public boolean updateData(String id, String RoNum, String RoType, String Floor, String days, String availability)
{ SQLiteDatabase db=this.getWritableDatabase();
ContentValues contentValues=new ContentValues();
contentValues.put(COL_1,id);
contentValues.put(COL_2,RoNum);
contentValues.put(COL_3,RoType);
contentValues.put(COL_4,Floor);
contentValues.put(COL_5,days);
contentValues.put(COL_6,availability);
db.update(TABLE_NAME,contentValues,"ID=?",new String[]{id});
        return true;
}
    public Integer deleteData(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
    return db.delete(TABLE_NAME,"ID=?",new String[]{id});
    }
public Cursor getAllData()
{
    SQLiteDatabase db=this.getWritableDatabase();
    Cursor cursor=db.rawQuery("select * from " +TABLE_NAME,null);
    return cursor;
}
}