package bestbuy.com.br.bestbuycatalog.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bestbuy.com.br.bestbuycatalog.model.ReviewTO;

public class ReviewDAO {
    private SQLiteDatabase db;
    private Database       banco;

    public ReviewDAO(Context context) {
        banco = new Database(context);
    }

    public boolean insertReview(ReviewTO reviewTO){
        ContentValues values;
        long          result;

        db = banco.getWritableDatabase();
        values = new ContentValues();
        values.put(Database.SKU, reviewTO.getSku());
        values.put(Database.NAME, reviewTO.getName());
        values.put(Database.LOCATION, reviewTO.getLocation());
        values.put(Database.RATING, reviewTO.getRating());
        values.put(Database.REVIEW, reviewTO.getReview());

        result = db.insert(Database.TABLE,null,values);
        db.close();

        if (result == -1)
            return false;
        else
            return true;
    }

    public List<ReviewTO> getReviews(String sku) {
        List<ReviewTO> reviews = new ArrayList<>();
        ReviewTO       reviewTO;
        Cursor         cursor;

        String[] columns = {Database.SKU, Database.LOCATION, Database.NAME, Database.RATING, Database.REVIEW};
        String   where   = Database.SKU + " LIKE " + sku;

        db = banco.getReadableDatabase();
        cursor = db.query(Database.TABLE, columns, where, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                reviewTO = new ReviewTO();
                reviewTO.setSku(cursor.getString(cursor.getColumnIndex(Database.SKU)));
                reviewTO.setLocation(cursor.getString(cursor.getColumnIndex(Database.LOCATION)));
                reviewTO.setName(cursor.getString(cursor.getColumnIndex(Database.NAME)));
                reviewTO.setRating(cursor.getInt(cursor.getColumnIndex(Database.RATING)));
                reviewTO.setReview(cursor.getString(cursor.getColumnIndex(Database.REVIEW)));

                reviews.add(reviewTO);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return reviews;
    }
}
