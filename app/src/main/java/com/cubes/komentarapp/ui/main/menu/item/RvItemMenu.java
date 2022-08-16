package com.cubes.komentarapp.ui.main.menu.item;

import com.cubes.komentarapp.ui.main.menu.MenuAdapter;

public interface RvItemMenu {

    int getType();
    void bind(MenuAdapter.MenuHolder holder);
}
