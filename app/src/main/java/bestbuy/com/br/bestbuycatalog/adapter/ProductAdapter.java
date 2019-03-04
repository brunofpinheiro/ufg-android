package bestbuy.com.br.bestbuycatalog.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import bestbuy.com.br.bestbuycatalog.R;
import bestbuy.com.br.bestbuycatalog.model.ProductTO;


public class ProductAdapter extends BaseAdapter {

    private final List<ProductTO> products;
    private final Activity        act;

    public ProductAdapter(List<ProductTO> products, Activity act) {
        this.products = products;
        this.act = act;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.product_list_item, parent, false);

        //pegando as referências das Views
        TextView  mName     = view.findViewById(R.id.product_list_item_name);
        TextView  mShipping = view.findViewById(R.id.product_list_item_shipping);
        TextView  mPrice    = view.findViewById(R.id.product_list_item_price);
        ImageView mImage    = view.findViewById(R.id.product_list_item_image);

        //populando as Views
        mName.setText(products.get(position).getName());
        mShipping.setText(products.get(position).getFreeShipping() ? "Free Shipping"  : "See estimates for shipping");
        mPrice.setText(products.get(position).getPrice());
        if (!products.get(position).getImage().isEmpty())
            Picasso.get().load(products.get(position).getImage()).into(mImage);

        return view;
    }
}