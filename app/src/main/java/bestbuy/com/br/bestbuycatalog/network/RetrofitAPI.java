package bestbuy.com.br.bestbuycatalog.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPI {

    public static Retrofit getRetrofit(String url) {
        Gson gson = new GsonBuilder().setLenient().create();

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static void getAllProducts() {
        ApiUtil.getServiceClass().getAllPost().enqueue(new Callback<List<ApiObject>>() {

            @Override
            public void onResponse(Call<List<ApiObject>> call, Response<List<ApiObject>> response) {
                if(response.isSuccessful()){
                    List<ApiObject> postList = response.body();
                    Log.d("BestBuy Catalog", "Returned count " + postList.size());
                }
            }

            @Override
            public void onFailure(Call<List<ApiObject>> call, Throwable t) {
                //showErrorMessage();
                Log.d("BestBuy Catalog", "error loading from API");
            }

        });
    }
}
