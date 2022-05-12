package com.shahir.cryptoalerm.MainFragments.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.shahir.cryptoalerm.MainFragments.Dashboard.TokenDetails.FragmentTokenDetails;
import com.shahir.cryptoalerm.R;
import com.shahir.cryptoalerm.databinding.TokenPriceRecyclerItemBinding;

import java.util.List;

public class TokenPriseAdapted extends RecyclerView.Adapter<TokenPriseAdapted.ViewHolder> {

    private final List<TokenPriseItem> tokenPriseItems;
    private final Context context;
    private final FragmentDashboard parent;

    public TokenPriseAdapted(List<TokenPriseItem> tokenPriseItems, Context context, FragmentDashboard parent) {
        this.tokenPriseItems = tokenPriseItems;
        this.context = context;
        this.parent = parent;
    }


    @NonNull
    @Override
    public TokenPriseAdapted.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TokenPriceRecyclerItemBinding binding = TokenPriceRecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TokenPriseAdapted.ViewHolder holder, int position) {
        TokenPriseItem data = tokenPriseItems.get(position);

        holder.binding.tvTokenSymbol.setText(data.getSymbol());
        holder.binding.tvTokenPrice.setText(data.getPrice_usd());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new FragmentTokenDetails();
                Bundle arguments = new Bundle();
                arguments.putString("tokenSymbol",data.getSymbol());
                fragment.setArguments(arguments);

                FragmentTransaction fragmentTransaction = parent.requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer, fragment).commit();
                fragmentTransaction.addToBackStack(null);

                //Toast.makeText(context, "Working on Details Fragment", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return tokenPriseItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TokenPriceRecyclerItemBinding binding;
        public ViewHolder(@NonNull TokenPriceRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
