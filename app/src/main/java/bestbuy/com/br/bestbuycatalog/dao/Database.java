package bestbuy.com.br.bestbuycatalog.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final String DB_NAME  = "bestbuycatalog.db";
    private static final int    VERSION  = 1;
    public  static final String TABLE    = "reviews";
    public  static final String ID       = "id";
    public  static final String SKU      = "sku";
    public  static final String NAME     = "name";
    public  static final String LOCATION = "location";
    public  static final String RATING   = "rating";
    public  static final String REVIEW   = "review";

    public Database(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SKU + " TEXT,"
                + NAME + " TEXT,"
                + LOCATION + " TEXT,"
                + RATING + " INTEGER,"
                + REVIEW + " TEXT" + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}