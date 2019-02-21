package bestbuy.com.br.bestbuycatalog.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import bestbuy.com.br.bestbuycatalog.R;
import bestbuy.com.br.bestbuycatalog.adapter.ProductAdapter;
import bestbuy.com.br.bestbuycatalog.model.ProductTO;
import bestbuy.com.br.bestbuycatalog.network.ApiHelper;

public class ProductFilterActivity extends AppCompatActivity {
    private Spinner       mCategories;
    private Button        mBtnSearch;
    private String        categoryID;
    private String        searchUrlBegin = "https://api.bestbuy.com/v1/products(customerReviewAverage>=4&(categoryPath.id=";
    private String        searchUrlEnd   = "))?apiKey=JRljNcnI9lWoA6w92awbhEmF&sort=sku.asc&show=sku,name,regularPrice," +
                                           "salePrice,onSale,freeShipping,image,longDescription&pageSize=20&format=json;";
    private List<ProductTO> products = null;
    private ListView        mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_filter);

        mCategories = findViewById(R.id.cmbCategorias);
        mBtnSearch  = findViewById(R.id.btnSearch);
        mList       = findViewById(R.id.lstFilteredProducts);

        List<String> categories = new ArrayList<String>();
        categories.add(getResources().getString(R.string.desktop));
        categories.add(getResources().getString(R.string.headphones));
        categories.add(getResources().getString(R.string.tablets));
        categories.add(getResources().getString(R.string.laptops));
        categories.add(getResources().getString(R.string.tvs));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategories.setAdapter(dataAdapter);

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    categoriesAction();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                itemClickAction(position);
            }
        });
    }


    private void categoriesAction() throws MalformedURLException {
        String selectedItem = mCategories.getSelectedItem().toString();

        if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.desktop)))
            categoryID = "abcat0501000";
        else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.headphones)))
            categoryID = "abcat0204000";
        else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.tablets)))
            categoryID = "pcmcat209000050006";
        else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.laptops)))
            categoryID = "abcat0502000";
        else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.tvs)))
            categoryID = "abcat0101000";

        ApiHelper apiHelper = new ApiHelper();
//        products = apiHelper.getProducts(getApplicationContext(), new URL(searchUrlBegin + categoryID + searchUrlEnd));
        products = apiHelper.getProducts(getApplicationContext(), new URL("https://api.github.com/"));
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProductAdapter productAdapter = new ProductAdapter(products, ProductFilterActivity.this);
                        mList.setAdapter(productAdapter);
                    }
                });
            }
        });
    }

    /**
     * Item click action method
     * @param position
     */
    private void itemClickAction(int position) {
        Intent intent = new Intent(ProductFilterActivity.this, ClickedItemActivity.class);
        intent.putExtra("PRODUCTS", products.get(position));
        startActivity(intent);
    }
}