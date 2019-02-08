package bestbuy.com.br.bestbuycatalog.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import bestbuy.com.br.bestbuycatalog.R;
import bestbuy.com.br.bestbuycatalog.model.Product;

public class ClickedItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_item);

        //pra poder iniciar a activity mesmo com a tela do telefone travada.
        //USAR ISSO SÓ DURANTE O DESENVOLVIMENTO
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED +
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON +
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        TextView mName        = (TextView) findViewById(R.id.product_name);
        TextView mDescription = (TextView) findViewById(R.id.product_description);
        TextView mPrice       = (TextView) findViewById(R.id.product_price);
        TextView mShipping    = (TextView) findViewById(R.id.product_shipping);
        ImageView mImage      = (ImageView) findViewById(R.id.product_image);

        Bundle  bundle  = this.getIntent().getExtras();
        Product product = (Product) bundle.getSerializable("PRODUCTS");

        mName.setText(product.getName());
        mDescription.setText(product.getDescription());
        mPrice.setText(product.getPrice());
        mShipping.setText(product.getShipping());
        mImage.setImageResource(product.getImage());
    }
}
