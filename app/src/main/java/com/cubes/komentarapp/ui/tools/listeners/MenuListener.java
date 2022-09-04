package com.cubes.komentarapp.ui.tools.listeners;

public interface MenuListener {

    void onSubcategoryClicked(int categoryId, int subcategoryId);

    void onItemClicked();

    void onHoroscopeClicked();

    void onCurrencyClicked();

    void onWeatherClicked();

    void onShareClicked(String networkUrl);

    void onNotificationClicked(boolean isOn);

}
