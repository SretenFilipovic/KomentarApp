package com.cubes.komentarapp.ui.main.menu.item;

import android.view.View;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.databinding.RvItemMenuItemsBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.listeners.MenuListener;

public class RvItemMenuItems implements RvItemMenu {

    private final MenuListener listener;
    private final boolean isOn;

    public RvItemMenuItems(MenuListener listener, boolean isOn) {
        this.listener = listener;
        this.isOn = isOn;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_menu_items;
    }

    @Override
    public void bind(ViewHolder holder) {

        RvItemMenuItemsBinding binding = (RvItemMenuItemsBinding) holder.binding;

        binding.textViewContact.setOnClickListener(view -> listener.onContactClicked());
        binding.textViewTermsAndConditions.setOnClickListener(view -> listener.onTermsAndConditionsClicked());
        binding.textViewMarketing.setOnClickListener(view -> listener.onMarketingClicked());
        binding.textViewCurrency.setOnClickListener(view -> listener.onCurrencyClicked());
        binding.textViewHoroscope.setOnClickListener(view -> listener.onHoroscopeClicked());
        binding.textViewWeather.setOnClickListener(view -> listener.onWeatherClicked());
        binding.textMyNews.setOnClickListener(view -> listener.onMyNewsClicked());

        binding.switchNotifications.setChecked(isOn);
        binding.switchNotifications.setOnCheckedChangeListener((compoundButton, b) -> listener.onNotificationClicked(b));

    }
}
