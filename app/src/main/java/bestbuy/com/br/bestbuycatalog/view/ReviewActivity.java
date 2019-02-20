package bestbuy.com.br.bestbuycatalog.view;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import bestbuy.com.br.bestbuycatalog.R;
import bestbuy.com.br.bestbuycatalog.gps.Location;

public class ReviewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        TextView mLocation = findViewById(R.id.location);

        ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, Location.REQUEST_LOCATION);

        Location loc = new Location(ReviewActivity.this, ReviewActivity.this);
        loc.getCurrentLocation();
        if (loc.gpsLocation != null)
            mLocation.setText(loc.gpsLocation.getCity());
    }

}
