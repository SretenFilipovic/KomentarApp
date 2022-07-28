package com.cubes.komentarapp.ui.main.home.headnews.item;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemHeadMostReadBinding;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsAdapter;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsMostReadVPAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class RvItemHeadMostRead implements RvItemHead{

    private Context context;
    private HeadNewsMostReadVPAdapter adapter;
    private ArrayList<News> mostReadList;
    private ArrayList<News> latestList;
    private ArrayList<News> mostCommentedList;

    public RvItemHeadMostRead(Context context,ArrayList<News> latest, ArrayList<News> mostRead, ArrayList<News> mostCommented) {
        this.context = context;
        this.mostCommentedList = mostCommented;
        this.mostReadList = mostRead;
        this.latestList = latest;
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void bind(HeadNewsAdapter.HeadNewsViewHolder holder) {

        // OVDE SE SETUJE ADAPTER ZA VIEWPAGER KOJI PRIKAZUJE NAJNOVIJE, NAJCITANIJE, NAJKOMENTARISANIJE
        RvItemHeadMostReadBinding binding = (RvItemHeadMostReadBinding) holder.binding;

        FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
        Lifecycle lc = ((AppCompatActivity) context).getLifecycle();

        adapter = new HeadNewsMostReadVPAdapter(fm, lc, latestList, mostReadList, mostCommentedList);
        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            if (position == 0){
                tab.setText("NAJNOVIJE");
            }
            else if (position == 1){
                tab.setText("NAJČITANIJE");
            }
            else{
                tab.setText("KOMENTARI");
            }
        }).attach();


    }
}
