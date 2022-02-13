package asd.datenbank2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {

    private ArrayList onlineFragen;

    // Logcat tag
    private static final String LOG = "asd.datenbank2.DatabaseHelper";
    private static final int DATABASE_VERSION = 33;

    // Database
    private static final String DATABASE_NAME = "Mydb";

    private static final String TABLE_USER = "wusers";
    private static final String KEY_U_ID = "id";
    private static final String COL_U_NAME = "name";
    private static final String COL_U_STUDIENGANG_ID = "studiengang_id";
    private static final String COL_PUNKTE = "punkte";

    private static final String TABLE_STUDIENGANG = "studiengaenge";
    private static final String KEY_S_ID = "id";
    private static final String COL_S_NAME = "name";

    private static final String TABLE_FRAGE = "fragen";
    private static final String KEY_F_ID = "id";
    private static final String COL_FRAGE = "frage";
    private static final String COL_RICHTIGE_ANTWORT = "antwort_a";
    private static final String COL_ANTWORT_B = "antwort_b";
    private static final String COL_ANTWORT_C = "antwort_c";
    private static final String COL_ANTWORT_D = "antwort_d";
    private static final String COL_HINWEIS = "hinweis";
    private static final String COL_F_STUDIENGANG_ID  = "studiengang_id";

    // Create
    private static final String CREATE_TABLE_WUSERS = "CREATE TABLE "
            + TABLE_USER + " (" +
            KEY_U_ID + " INTEGER PRIMARY KEY," +
            COL_U_NAME + " TEXT,"+
            COL_PUNKTE + " INTEGER NOT NULL, " +
            COL_U_STUDIENGANG_ID + " INTEGER NULL, " +
            "FOREIGN KEY("+COL_U_STUDIENGANG_ID+") REFERENCES "+TABLE_STUDIENGANG+"(ID))";

    private static final String CREATE_TABLE_STUDIENGAENGE = "CREATE TABLE "
            + TABLE_STUDIENGANG + " (" +
            KEY_S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_S_NAME + " TEXT )";

    private static final String CREATE_TABLE_FRAGEN = "CREATE TABLE "
            + TABLE_FRAGE + " (" + KEY_F_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_FRAGE + " TEXT, " +
            COL_RICHTIGE_ANTWORT + " TEXT," +
            COL_ANTWORT_B + " TEXT," +
            COL_ANTWORT_C + " TEXT," +
            COL_ANTWORT_D + " TEXT," +
            COL_HINWEIS + " TEXT," +
            COL_F_STUDIENGANG_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY("+COL_F_STUDIENGANG_ID+") REFERENCES "+TABLE_STUDIENGANG+"(ID))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_STUDIENGAENGE);
        db.execSQL(CREATE_TABLE_WUSERS);
        db.execSQL(CREATE_TABLE_FRAGEN);

        // STUDIENGÄNGE INSERTS
        db.execSQL("INSERT INTO " +TABLE_STUDIENGANG+ "("+ COL_S_NAME +") VALUES('CUSTOM')");

        //  FRAGE INSERTS
    //    db.execSQL("INSERT INTO " +TABLE_FRAGE+ "("+COL_FRAGE+","+ COL_RICHTIGE_ANTWORT+","+ COL_ANTWORT_B+","+ COL_ANTWORT_C+","+COL_ANTWORT_D+","+COL_HINWEIS+","+ COL_F_STUDIENGANG_ID +") VALUES('2+2=?', '4', '3', '5', '6', '2x2=??',2)");
    }

    public void updateStudiengang(String valueStringStudiengang, String valueStringFragen){
        SQLiteDatabase db = this.getWritableDatabase();
        onlineFragen =  new ArrayList(Arrays.asList(valueStringFragen.split("\\|")));
        db.execSQL("INSERT INTO " +TABLE_STUDIENGANG+ "("+ COL_S_NAME +") VALUES('"+valueStringStudiengang+"')");
        for(int i=0; i<onlineFragen.size(); i++) {
            db.execSQL("INSERT INTO " + TABLE_FRAGE + "(" + COL_FRAGE + "," + COL_RICHTIGE_ANTWORT + "," + COL_ANTWORT_B + "," + COL_ANTWORT_C + "," + COL_ANTWORT_D + "," + COL_HINWEIS + "," + COL_F_STUDIENGANG_ID + ") VALUES(" + onlineFragen.get(i) + ")");
            System.out.println(onlineFragen.get(i));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDIENGANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRAGE);
        onCreate(db);
    }

    // Wuser
    public Wuser getUserData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_USER,null);
        Wuser u = new Wuser();
        while (res.moveToNext()) {
            u.setId(Integer.parseInt(res.getString(0)));
            u.setName(res.getString(1));
            u.setPunkte(Integer.parseInt(res.getString(2)));
            u.setStudiengaeng_id(Integer.parseInt(res.getString(3)));
        }
        return u;
    }

    public Integer deleteUserData () {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USER, "ID = 1",new String[] {});
    }

    public long insertUserData(Wuser u){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_U_ID, u.id);
        values.put(COL_U_NAME, u.name);
        values.put(COL_PUNKTE, u.punkte);
        values.put(COL_U_STUDIENGANG_ID, u.studiengaeng_id);
        return db.insert(TABLE_USER, null, values);
    }

    // Fragen
    public long insertFrageData(Frage f){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FRAGE, f.frage);
        values.put(COL_RICHTIGE_ANTWORT, f.richtige_antwort);
        values.put(COL_ANTWORT_B, f.antwort_b);
        values.put(COL_ANTWORT_C, f.antwort_c);
        values.put(COL_ANTWORT_D, f.antwort_d);
        values.put(COL_HINWEIS, f.hinweis);
        values.put(COL_F_STUDIENGANG_ID, f.studiengaeng_id);
        return db.insert(TABLE_FRAGE, null, values);
    }

    public Frage getRandomFrage(long s) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_FRAGE +" where "+KEY_F_ID+"="+s,null);
        Frage u = new Frage();
        while (res.moveToNext()) {
            u.setId(Integer.parseInt(res.getString(0)));
            u.setFrage(res.getString(1));
            u.setRichtigeAntwort(res.getString(2));
            u.setAntwortB(res.getString(3));
            u.setAntwortC(res.getString(4));
            u.setAntwortD(res.getString(5));
            u.setHinweis(res.getString(6));
            u.setStudiengaengID(Integer.parseInt(res.getString(7)));
        }
        return u;
    }
    public Integer deleteUserCustoms () {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FRAGE, "studiengang_id = 1",new String[] {});
    }

    public Integer deleteallUserpakete() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FRAGE, null ,new String[] {});
    }

    public long getIDRandomFrage(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ID from "+TABLE_FRAGE +" ORDER BY RANDOM() LIMIT 1",null);
        long j=0;
        while (res.moveToNext()){

            j= Long.parseLong(res.getString(0));
        }
        return j;
    }

    // STUDIENGAENGE
    public Integer deleteallUserStudiengaenge() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STUDIENGANG, null ,new String[] {});
    }

    public Cursor getStudiengang() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_STUDIENGANG,null);
        return res;
    }

    public String[] gebeStudiengangArray(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select name from "+TABLE_STUDIENGANG,null);
        int menge = res.getCount();
        String[] interneStudiengaenge = new String[menge];
        while(res.moveToNext()){
            interneStudiengaenge[res.getPosition()] = res.getString(0);
            System.out.println("insgesamt "+menge+" studiengaenge und interneStudiengaenge["+res.getPosition()+"] ist "+interneStudiengaenge[res.getPosition()]);
        }
        return interneStudiengaenge;
    }
}