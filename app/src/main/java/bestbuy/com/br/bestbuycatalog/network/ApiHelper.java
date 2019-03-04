package bestbuy.com.br.bestbuycatalog.network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.JsonReader;
import android.util.JsonToken;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import bestbuy.com.br.bestbuycatalog.model.ProductTO;

public class ApiHelper {
    private ProductTO            productTO        = null;
    private String               sku              = "";
    private String               name             = "";
    private String               description      = "";
    private String               price            = "";
    private Boolean              shipping         = false;
    private String               image            = "";
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
                        InputStream       responseBody       = myConnection.getInputStream();
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                        JsonReader        jsonReader         = new JsonReader(responseBodyReader);

                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String key = jsonReader.nextName();

                            if (key.equalsIgnoreCase("products")) {
                                jsonReader.beginArray();
                                while (jsonReader.hasNext()) {
                                    jsonReader.beginObject();
                                    while (jsonReader.hasNext()) {
                                        String productKey = jsonReader.nextName();

                                        if (productKey.equalsIgnoreCase("sku"))
                                            sku = jsonReader.nextString();
                                        else if (productKey.equalsIgnoreCase("name"))
                                            name = jsonReader.nextString();
                                        else if (productKey.equalsIgnoreCase("longDescription"))
                                            description = jsonReader.nextString();
                                        else if (productKey.equals("regularPrice"))
                                            price = jsonReader.nextString();
                                        else if (productKey.equals("freeShipping")) {
                                            JsonToken peek = jsonReader.peek();
                                            if (peek == JsonToken.NULL) {
                                                shipping = false;
                                                jsonReader.skipValue();
                                            } else {
                                                shipping = jsonReader.nextBoolean();
                                            }
                                        } else if (productKey.equals("image")) {
                                            JsonToken peek = jsonReader.peek();
                                            if (peek == JsonToken.NULL) {
                                                image = "";
                                                jsonReader.skipValue();
                                            } else {
                                                image = jsonReader.nextString();
                                            }
                                        } else if (jsonReader.peek() == JsonToken.NULL){
                                            jsonReader.skipValue();
                                        } else {
                                            jsonReader.skipValue();
                                        }
                                    }

                                    productTO = new ProductTO(sku, name, description, price, shipping, image);
                                    productsFromJSON.add(productTO);
                                    jsonReader.endObject();
                                }

                                jsonReader.endArray();
                            }

                            if (jsonReader.hasNext()) {
                                jsonReader.skipValue();
                            }
                        }
                        jsonReader.close();
                    } else {
                        Looper.prepare();
                        System.out.println("response code: " + myConnection.getResponseCode());
                        Toast.makeText(context, "A busca de produtos falhou. Tente novamente mais tarde.",
                                Toast.LENGTH_SHORT).show();
                    }

                    myConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return productsFromJSON;
    }
}