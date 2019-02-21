package bestbuy.com.br.bestbuycatalog.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import bestbuy.com.br.bestbuycatalog.R;
import bestbuy.com.br.bestbuycatalog.model.ProductTO;

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

        TextView  mName        = findViewById(R.id.product_name);
        TextView  mDescription = findViewById(R.id.product_description);
        TextView  mPrice       = findViewById(R.id.product_price);
        TextView  mShipping    = findViewById(R.id.product_shipping);
        ImageView mImage       = findViewById(R.id.product_image);
        Button    mReview      = findViewById(R.id.btn_review);

        Bundle  bundle  = this.getIntent().getExtras();
        ProductTO productTO = (ProductTO) bundle.getSerializable("PRODUCTS");

        if (productTO != null) {
            mName.setText(productTO.getName());
            mDescription.setText(productTO.getDescription());
            mPrice.setText(productTO.getPrice());
            mShipping.setText(productTO.getShipping());
            mImage.setImageResource(productTO.getImage());
        }

        mReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnReviewAction();
            }
        });
    }

    private void btnReviewAction() {
        Intent intent = new Intent(ClickedItemActivity.this, ReviewActivity.class);
        startActivity(intent);
    }

}
