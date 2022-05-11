package com.shahir.cryptoalerm.MainFragments.Dashboard;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shahir.cryptoalerm.databinding.FragmentDashboardBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentDashboard extends Fragment {

    Context mContext;
    FragmentDashboardBinding binding;

    private List<TokenPriseItem> tokenPriseItems;
    private RecyclerView.Adapter tokenPriceAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        getCoinData();

        tokenPriseItems =new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        binding.tokenPriseRecyclerView.setLayoutManager(manager);
        tokenPriceAdapter = new TokenPriseAdapted(tokenPriseItems,mContext,this);
        binding.tokenPriseRecyclerView.setAdapter(tokenPriceAdapter);

        return binding.getRoot();
    }

    public void getCoinData(){
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://data.messari.io/api/v1/assets?fields=id,slug,symbol,metrics/market_data/price_usd";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d(TAG, "onResponse: @@@@@@@@@@          Full Response : "  + response);

                tokenPriseItems.clear();

                try {
                    //JSONObject jsonObject = response.getJSONObject("data");

                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i<jsonArray.length(); i++){

                        JSONObject mainObject = jsonArray.getJSONObject(i);

                        JSONObject metrics = mainObject.getJSONObject("metrics");

                        JSONObject market_data = metrics.getJSONObject("market_data");


                        TokenPriseItem data = new TokenPriseItem(
                                mainObject.getString("symbol"),
                                market_data.getString("price_usd")
                        );

                        tokenPriseItems.add(data);

                        Log.d(TAG, "onResponse: @@@@@@@@@@             Token name : " + mainObject.getString("symbol") + " || Token Price : " +
                                market_data.getString("price_usd"));

                    }
                    tokenPriceAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    Log.d(TAG, "onResponse: @@@@@@@@@@            Object Error : " + e);
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: @@@@@@@@              Volley Error : " + error);
            }
        });

        queue.add(jsonObjectRequest);
    }
}