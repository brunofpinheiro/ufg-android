package bestbuy.com.br.bestbuycatalog.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bestbuy.com.br.bestbuycatalog.R;
import bestbuy.com.br.bestbuycatalog.model.ReviewTO;


public class ReviewAdapter extends BaseAdapter {

    private final List<ReviewTO> reviews;
    private final Activity       act;

    public ReviewAdapter(List<ReviewTO> reviews, Activity act) {
        this.reviews = reviews;
        this.act = act;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int i) {
        return reviews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.product_reviews_list, parent, false);

        //pegando as referências das Views
        TextView review   = view.findViewById(R.id.reviewListReview);
        TextView name     = view.findViewById(R.id.reviewListName);
        TextView rating   = view.findViewById(R.id.reviewListRating);
        TextView location = view.findViewById(R.id.reviewListLocation);

        //populando as Views
        review.setText(reviews.get(position).getReview());
        name.setText(reviews.get(position).getName());
        rating.setText(" - " + reviews.get(position).getRating() + " stars ");
        location.setText(" (" + reviews.get(position).getLocation() + ")");

        return view;
    }
}
