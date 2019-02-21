package bestbuy.com.br.bestbuycatalog.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import bestbuy.com.br.bestbuycatalog.R;
import bestbuy.com.br.bestbuycatalog.model.ProductTO;


public class ProductAdapter extends BaseAdapter {

    private final List<ProductTO> products;
    private final Activity act;

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
        TextView  name     = view.findViewById(R.id.product_list_item_name);
        TextView  shipping = view.findViewById(R.id.product_list_item_shipping);
        TextView  price    = view.findViewById(R.id.product_list_item_price);
        ImageView image    = view.findViewById(R.id.product_list_item_image);

        //populando as Views
        name.setText(products.get(position).getName());
        shipping.setText(products.get(position).getShipping());
        price.setText(products.get(position).getPrice());
        image.setImageResource(products.get(position).getImage());

        return view;
    }
}
