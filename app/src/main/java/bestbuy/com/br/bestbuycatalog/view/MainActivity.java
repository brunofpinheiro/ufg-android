package bestbuy.com.br.bestbuycatalog.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import bestbuy.com.br.bestbuycatalog.adapter.ProductAdapter;
import bestbuy.com.br.bestbuycatalog.R;
import bestbuy.com.br.bestbuycatalog.model.ProductTO;
import bestbuy.com.br.bestbuycatalog.network.ApiHelper;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ProductTO> products = null;
    private ListView             mList    = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = findViewById(R.id.lstProducts);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                itemClickAction(position);
            }
        });

        try {
            String url = "https://api.bestbuy.com/v1/products(bestSellingRank<=50)?apiKey=" + getResources().getString(R.string.api_key) +
                    "&sort=sku.asc&show=sku,name,regularPrice,salePrice,onSale,freeShipping,image,longDescription&pageSize=20&format=json";
            ApiHelper apiHelper = new ApiHelper();
            products = apiHelper.getProducts(getApplicationContext(), new URL(url));
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ProductAdapter productAdapter = new ProductAdapter(products, MainActivity.this);
                            mList.setAdapter(productAdapter);
                        }
                    });
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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

    /**
     * Item click action method
     * @param position
     */
    private void itemClickAction(int position) {
        Intent intent = new Intent(MainActivity.this, ClickedItemActivity.class);
        intent.putExtra("PRODUCTS", products.get(position));
        startActivity(intent);
    }

}