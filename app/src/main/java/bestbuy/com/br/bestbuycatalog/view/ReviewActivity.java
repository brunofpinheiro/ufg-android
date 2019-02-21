package bestbuy.com.br.bestbuycatalog.view;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bestbuy.com.br.bestbuycatalog.R;
import bestbuy.com.br.bestbuycatalog.adapter.ReviewAdapter;
import bestbuy.com.br.bestbuycatalog.dao.ReviewDAO;
import bestbuy.com.br.bestbuycatalog.gps.Location;
import bestbuy.com.br.bestbuycatalog.model.ReviewTO;

public class ReviewActivity extends AppCompatActivity {
    List<ReviewTO> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        TextView mLocation     = findViewById(R.id.location);
        Button   mSubmit       = findViewById(R.id.submitReview);
        TextView mMsgNoReviews = findViewById(R.id.msgNoReviews);
        ListView mReviewsList  = findViewById(R.id.lstReviews);

        ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, Location.REQUEST_LOCATION);

        Location loc = new Location(ReviewActivity.this, ReviewActivity.this);
        loc.getCurrentLocation();
        if (loc.gpsLocationTO != null)
            mLocation.setText(loc.gpsLocationTO.getCity());
        else
            mLocation.setText("Goiânia"); //usado pra testar no emulador

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAction();
            }
        });

        getProductReviews();
        if (reviews.isEmpty())
            mMsgNoReviews.setVisibility(View.VISIBLE);

        ReviewAdapter reviewAdapter = new ReviewAdapter(reviews, ReviewActivity.this);
        mReviewsList.setAdapter(reviewAdapter);

    }

    private void submitAction() {
        ReviewDAO reviewDAO = new ReviewDAO(ReviewActivity.this);
        ReviewTO reviewTO   = new ReviewTO();

        TextView  mSku      = findViewById(R.id.sku);
        TextView  mLocation = findViewById(R.id.location);
        EditText  mName     = findViewById(R.id.editName);
        RatingBar mRating   = findViewById(R.id.ratingBar);
        EditText  mReview   = findViewById(R.id.editReview);

        reviewTO.setSku(mSku.getText().toString());
        reviewTO.setLocation(mLocation.getText().toString());
        reviewTO.setName(mName.getText().toString());
        reviewTO.setRating(((int) mRating.getRating()));
        reviewTO.setReview(mReview.getText().toString());

        if (reviewDAO.insertReview(reviewTO)) {
            Toast.makeText(ReviewActivity.this, "Thank you for your review!", Toast.LENGTH_SHORT).show();
            resetFields();
        } else {
            Toast.makeText(ReviewActivity.this, "There was an error. Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetFields() {
        TextView  mLocation = findViewById(R.id.location);
        EditText  mName     = findViewById(R.id.editName);
        RatingBar mRating   = findViewById(R.id.ratingBar);
        EditText  mReview   = findViewById(R.id.editReview);

        mLocation.setText("");
        mName.setText("");
        mRating.setRating(0);
        mReview.setText("");
    }

    private void getProductReviews() {
        ReviewDAO reviewDAO = new ReviewDAO(ReviewActivity.this);
        TextView  mSku      = findViewById(R.id.sku);
        reviews             = new ArrayList<>();

        reviews = reviewDAO.getReviews(mSku.getText().toString());
    }

}
