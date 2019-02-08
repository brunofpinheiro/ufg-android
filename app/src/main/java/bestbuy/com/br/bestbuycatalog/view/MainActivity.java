package bestbuy.com.br.bestbuycatalog.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import bestbuy.com.br.bestbuycatalog.adapter.ProductAdapter;
import bestbuy.com.br.bestbuycatalog.R;
import bestbuy.com.br.bestbuycatalog.model.Product;

public class MainActivity extends AppCompatActivity {
    private Product            product          = null;
    private String             name             = "";
    private String             description      = "";
    private String             price            = "";
    private String             shipping         = "";
    private int                image            = 0;
    private ArrayList<Product> productsFromJSON = null;
    private ArrayList<Product> products         = null;
    private ListView           mList            = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //pra poder iniciar a activity mesmo com a tela do telefone travada.
        //USAR ISSO SÓ DURANTE O DESENVOLVIMENTO
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED +
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON +
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        try {
            products = getProducts(new URL("https://api.github.com/"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mList = (ListView) findViewById(R.id.lstProducts);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ClickedItemActivity.class);
                intent.putExtra("PRODUCTS", products.get(position));

                startActivity(intent);
            }
        });
    }

    /**
     * Cria o menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Gerencia os cliques nos itens do menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter_products:
                Intent intent = new Intent(MainActivity.this, ProductFilterActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private ArrayList<Product> getProducts(final URL endPoint) {
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
                        product = new Product(name, description, price, shipping, image);
                        productsFromJSON.add(product);

                        jsonReader.close();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProductAdapter productAdapter = new ProductAdapter(products, MainActivity.this);
                                mList.setAdapter(productAdapter);
                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "A busca de produtos falhou. Tente novamente mais tarde.",
                                Toast.LENGTH_SHORT).show();
                    }

                    myConnection.disconnect();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return productsFromJSON;
    }
}