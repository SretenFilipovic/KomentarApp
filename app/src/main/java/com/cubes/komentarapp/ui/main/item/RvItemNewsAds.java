package com.cubes.komentarapp.ui.main.item;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.databinding.RvItemAdviewBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.google.android.gms.ads.AdRequest;

public class RvItemNewsAds implements RvItemNews {

    public RvItemNewsAds() {
    }

    @Override
    public int getType() {
        return R.layout.rv_item_adview;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemAdviewBinding binding = (RvItemAdviewBinding) holder.binding;

        AdRequest adRequest = new AdRequest.Builder().build();

        binding.adView.loadAd(adRequest);

    }
}
