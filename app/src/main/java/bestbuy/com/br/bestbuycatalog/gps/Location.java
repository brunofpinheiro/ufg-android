package bestbuy.com.br.bestbuycatalog.gps;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import bestbuy.com.br.bestbuycatalog.model.GpsLocationTO;


public class Location {
    public static final int REQUEST_LOCATION = 1;

    private Context         context;
    private Activity        activity;
    public GpsLocationTO gpsLocationTO;
    private LocationManager locationManager;

    public Location(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void getCurrentLocation() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            android.location.Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                getAddressFromLocation(context, lat, lon);
            } else {
                Toast.makeText(context, "Unable to trace your location!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getAddressFromLocation(Context context, double LATITUDE, double LONGITUDE) {
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {
                gpsLocationTO = new GpsLocationTO();
                gpsLocationTO.setAddress(addresses.get(0).getAddressLine(0)); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                gpsLocationTO.setCity(addresses.get(0).getLocality());
                gpsLocationTO.setState(addresses.get(0).getAdminArea());
                gpsLocationTO.setCountry(addresses.get(0).getCountryName());
                gpsLocationTO.setPostalCode(addresses.get(0).getPostalCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    protected void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Please turn ON your GPS")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
