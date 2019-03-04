package bestbuy.com.br.bestbuycatalog.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bestbuy.com.br.bestbuycatalog.R;
import bestbuy.com.br.bestbuycatalog.model.ProductTO;

public class ClickedItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_item);

        TextView  mName        = findViewById(R.id.product_name);
        TextView  mDescription = findViewById(R.id.product_description);
        TextView  mPrice       = findViewById(R.id.product_price);
        TextView  mShipping    = findViewById(R.id.product_shipping);
        ImageView mImage       = findViewById(R.id.product_image);
        Button    mReview      = findViewById(R.id.btn_review);

        Bundle    bundle    = this.getIntent().getExtras();
        final ProductTO productTO = (ProductTO) bundle.getSerializable("PRODUCTS");

        if (productTO != null) {
            mName.setText(productTO.getName());
            mDescription.setText(productTO.getDescription());
            mPrice.setText(productTO.getPrice());
            mShipping.setText(productTO.getFreeShipping() ? "Free shipping" : "See estimates for shipping");
            if (!productTO.getImage().isEmpty())
                Picasso.get().load(productTO.getImage()).into(mImage);
        }

        mReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnReviewAction(productTO);
            }
        });
    }

    /**
     * Cria um intent para a tela de review passando o SKU do produto.
     * @param productTO
     */
    private void btnReviewAction(ProductTO productTO) {
        Intent intent = new Intent(ClickedItemActivity.this, ReviewActivity.class);
        intent.putExtra("SKU", productTO.getSku());
        startActivity(intent);
    }
}