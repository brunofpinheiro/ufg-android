package bestbuy.com.br.bestbuycatalog.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import bestbuy.com.br.bestbuycatalog.R;
import bestbuy.com.br.bestbuycatalog.model.ProductTO;

public class ApiHelper {
    private ProductTO productTO = null;
    private String             name             = "";
    private String             description      = "";
    private String             price            = "";
    private String             shipping         = "";
    private int                image            = 0;
    private ArrayList<ProductTO> productsFromJSON = null;


    /**
     * Get a list of products from the API
     * @param context
     * @param endPoint
     * @return
     */
    public ArrayList<ProductTO> getProducts(final Context context, final URL endPoint) {
        productsFromJSON = new ArrayList<>();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                HttpsURLConnection myConnection;

                try {
                    myConnection = (HttpsURLConnection) endPoint.openConnection();
                    myConnection.setRequestProperty("User-Agent", "bestbuy-catalog-v0.1");

                    if (myConnection.getResponseCode() == 200) {
                        InputStream responseBody       = myConnection.getInputStream();
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                        JsonReader jsonReader         = new JsonReader(responseBodyReader);

                        jsonReader.beginObject(); // Start processing the JSON object
                        while (jsonReader.hasNext()) { // Loop through all keys
                            String key = jsonReader.nextName(); // Fetch the next key

                            if (key.equals("current_user_url"))
                                name = jsonReader.nextString();
                            else if (key.equals("organization_url"))
                                description = jsonReader.nextString();
                            else
                                jsonReader.skipValue(); // Skip values of other keys


                            price = "5.00";
                            shipping = "FREE SHIPPING";
                            image = R.drawable.beats_solo3;

                        }
                        productTO = new ProductTO(name, description, price, shipping, image);
                        productsFromJSON.add(productTO);

                        jsonReader.close();
                    } else {
                        Toast.makeText(context, "A busca de produtos falhou. Tente novamente mais tarde.",
                                Toast.LENGTH_SHORT).show();
                    }

                    myConnection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return productsFromJSON;
    }

}
