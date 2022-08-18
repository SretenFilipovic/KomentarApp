package com.cubes.komentarapp.ui.main.menu.item;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.databinding.RvItemMenuSocialNetworkBinding;
import com.cubes.komentarapp.ui.tools.RvItem;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.tools.listeners.MenuListener;

public class RvItemMenuSocialNetwork implements RvItem {

    private final MenuListener listener;

    public RvItemMenuSocialNetwork(MenuListener listener) {
        this.listener = listener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_menu_social_network;
    }

    @Override
    public void bind(ViewHolder holder) {
        RvItemMenuSocialNetworkBinding binding = (RvItemMenuSocialNetworkBinding) holder.binding;

        binding.imageViewFacebook.setOnClickListener(view -> listener.onShareClicked("com.facebook.katana"));

        binding.imageViewInstagram.setOnClickListener(view -> listener.onShareClicked("com.instagram.android"));
        binding.imageViewTwitter.setOnClickListener(view -> listener.onShareClicked("com.twitter.android"));
        binding.imageViewViber.setOnClickListener(view -> listener.onShareClicked("com.viber.voip"));
        binding.imageViewWhatsapp.setOnClickListener(view -> listener.onShareClicked("com.whatsapp"));
    }
}
