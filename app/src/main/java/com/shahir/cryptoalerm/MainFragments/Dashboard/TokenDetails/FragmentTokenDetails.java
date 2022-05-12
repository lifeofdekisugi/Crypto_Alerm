package com.shahir.cryptoalerm.MainFragments.Dashboard.TokenDetails;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shahir.cryptoalerm.LoadingDialog;
import com.shahir.cryptoalerm.MainFragments.Dashboard.TokenPriseItem;
import com.shahir.cryptoalerm.databinding.FragmentTokenDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Currency;

public class FragmentTokenDetails extends Fragment {

    Context mContext;
    FragmentTokenDetailsBinding binding;

    String Symbol;
    String oneHourHighAPI, oneHourLowAPI, oneHourOpenAPI, oneHourCloseAPI;
    String oneHourHigh, oneHourLow, oneHourOpen, oneHourClose;

    String oneDayHigh, oneDayLow, oneDayOpen, oneDayClose;
    String oneDayHighAPI, oneDayLowAPI, oneDayOpenAPI, oneDayCloseAPI;

    LoadingDialog loadingDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_token_details, container, false);
        binding = FragmentTokenDetailsBinding.inflate(inflater, container, false);
        loadingDialog = new LoadingDialog(mContext);
        loadingDialog.show();

        Symbol = getArguments().getString("tokenSymbol");

        Log.d(TAG, "onCreateView: @@@@@@@@@@@        Fragment Token Details Symbol : " + Symbol);

        getTokenPriceDetails();

        binding.reloadAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.reloadAnimation.playAnimation();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getTokenPriceDetails();
                    }
                }, 2000);
            }
        });

        return binding.getRoot();
    }

    private void getTokenPriceDetails() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://data.messari.io/api/v1/assets/" + Symbol + "/metrics/market-data";
        Log.d(TAG, "getTokenPriceDetails: @@@@@@@@@@@@@@@                URL : " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingDialog.hide();
                Log.d(TAG, "getTokenPriceDetails: @@@@@@@@@@@@@            Full Response : " + response);

                try {
                    JSONObject dataObject = response.getJSONObject("data");
                    JSONObject assetObject = dataObject.getJSONObject("Asset");
                    JSONObject marketDataObject = dataObject.getJSONObject("market_data");
                    JSONObject last1HourObject = marketDataObject.getJSONObject("ohlcv_last_1_hour");
                    JSONObject last24HourObject = marketDataObject.getJSONObject("ohlcv_last_24_hour");

                    // Token Price Dollar Format
                    String tokenPriceString = marketDataObject.getString("price_usd");
                    double tokenPriceFromAPI = Double.parseDouble(tokenPriceString);
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    numberFormat.setMaximumFractionDigits(2);
                    Currency currency = Currency.getInstance("USD");
                    numberFormat.setCurrency(currency);
                    String tokenPrice = numberFormat.format(tokenPriceFromAPI);

                    binding.tvTokenName.setText(assetObject.getString("name"));
                    binding.tvTokenPrice.setText("$ " + tokenPrice);
                    binding.tvPercentLast24Hours.setText(marketDataObject.getString("percent_change_usd_last_24_hours"));


                    // Last 1 Hour
                    oneHourHighAPI = last1HourObject.getString("high");
                    oneHourLowAPI = last1HourObject.getString("low");
                    oneHourOpenAPI = last1HourObject.getString("open");
                    oneHourCloseAPI = last1HourObject.getString("close");

                    lastOneHourNumberFormat();

                    // Set Last One Hour
                    binding.tv1HourHigh.setText("$ " + oneHourHigh);
                    binding.tv1HourLow.setText("$ " + oneHourLow);
                    binding.tv1HourOpen.setText("$ " + oneHourOpen);
                    binding.tv1HourClose.setText("$ " + oneHourClose);

                    // 24 Hour
                    oneDayHighAPI = last24HourObject.getString("high");
                    oneDayLowAPI = last24HourObject.getString("low");
                    oneDayOpenAPI = last24HourObject.getString("open");
                    oneDayCloseAPI = last24HourObject.getString("close");

                    lastOneDayNumberFormat();

                    // Set Last 24 Hour
                    binding.tv24HourHigh.setText("$ " + oneDayHigh);
                    binding.tv24HourLow.setText("$ " + oneDayLow);
                    binding.tv24HourOpen.setText("$ " + oneDayOpen);
                    binding.tv24HourClose.setText("$ " + oneDayClose);

                    Log.d(TAG, "onResponse: @@@@@@@@@@@@@@@                 Last 1 Hour History : " + last1HourObject);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: @@@@@@@@@@@@               Error : " + error);
            }
        });

        queue.add(jsonObjectRequest);

    }

    private void lastOneDayNumberFormat() {
        double high, low, open, close;

        high = Double.parseDouble(oneDayHighAPI);
        low = Double.parseDouble(oneDayLowAPI);
        open = Double.parseDouble(oneDayOpenAPI);
        close = Double.parseDouble(oneDayCloseAPI);

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        Currency currency = Currency.getInstance("USD");
        numberFormat.setCurrency(currency);

        oneDayHigh = numberFormat.format(high);
        oneDayLow = numberFormat.format(low);
        oneDayOpen = numberFormat.format(open);
        oneDayClose = numberFormat.format(close);

    }

    private void lastOneHourNumberFormat() {

        double high, low, open, close;

        high = Double.parseDouble(oneHourHighAPI);
        low = Double.parseDouble(oneHourLowAPI);
        open = Double.parseDouble(oneHourOpenAPI);
        close = Double.parseDouble(oneHourCloseAPI);

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        Currency currency = Currency.getInstance("USD");
        numberFormat.setCurrency(currency);

        oneHourHigh = numberFormat.format(high);
        oneHourLow = numberFormat.format(low);
        oneHourOpen = numberFormat.format(open);
        oneHourClose = numberFormat.format(close);

    }
}