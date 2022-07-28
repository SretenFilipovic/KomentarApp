package com.cubes.komentarapp.ui.detail.item;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.cubes.komentarapp.data.source.datarepository.DataContainer;
import com.cubes.komentarapp.databinding.RvItemNewsDetailShareBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailAdapter;

public class RvItemDetailShare implements RvItemDetail{

    private News news;
    private Context context;

    public RvItemDetailShare(Context context, News news) {
        this.context = context;
        this.news = news;
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void bind(NewsDetailAdapter.NewsDetailViewHolder holder) {

        RvItemNewsDetailShareBinding binding = (RvItemNewsDetailShareBinding) holder.binding;

        binding.imageViewFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareOnSocialNetworks("com.facebook.katana");
            }
        });

        binding.imageViewViber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareOnSocialNetworks("com.viber.voip");
            }
        });

        binding.imageViewWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareOnSocialNetworks("com.whatsapp");
            }
        });

        binding.imageViewInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareOnSocialNetworks("com.instagram.android");
            }
        });

        binding.imageViewTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareOnSocialNetworks("com.twitter.android");
            }
        });

    }

    // metoda za deljenje na drustvene mreze
    private void shareOnSocialNetworks(String networkUrl){
        try {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, DataContainer.BASE_URL);
            i.setType("text/plain");
            i.setPackage(networkUrl);
            context.startActivity(i);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Nemate instaliranu neophodnu aplikaciju.",  Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
