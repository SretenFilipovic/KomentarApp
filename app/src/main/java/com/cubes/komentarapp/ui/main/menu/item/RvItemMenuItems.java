package com.cubes.komentarapp.ui.main.menu.item;

import com.cubes.komentarapp.databinding.RvItemMenuItemsBinding;
import com.cubes.komentarapp.ui.ViewHolder.RvItem;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.listeners.MenuListener;

public class RvItemMenuItems implements RvItem {

    private MenuListener listener;

    public RvItemMenuItems(MenuListener listener) {
        this.listener = listener;
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemMenuItemsBinding binding = (RvItemMenuItemsBinding) holder.binding;

        binding.textViewContact.setOnClickListener(view -> listener.onItemClicked());
        binding.textViewTermsAndConditions.setOnClickListener(view -> listener.onItemClicked());
        binding.textViewPushNotification.setOnClickListener(view -> listener.onItemClicked());
        binding.textViewMarketing.setOnClickListener(view -> listener.onItemClicked());
        binding.textViewCurrency.setOnClickListener(view -> listener.onCurrencyClicked());
        binding.textViewHoroscope.setOnClickListener(view -> listener.onHoroscopeClicked());
        binding.textViewWeather.setOnClickListener(view -> listener.onWeatherClicked());

    }
}
