package bestbuy.com.br.bestbuycatalog.network;

import bestbuy.com.br.bestbuycatalog.network.ApiObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;


public interface RetrofitInterface {

    @GET("v2/5a96abc232000057005e2ed7")
    public Call<List<ApiObject>> getAllPost();

}