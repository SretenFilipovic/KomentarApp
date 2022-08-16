package com.cubes.komentarapp.ui.main.menu.item;

import com.cubes.komentarapp.databinding.RvItemMenuSocialNetworkBinding;
import com.cubes.komentarapp.ui.main.menu.MenuAdapter;
import com.cubes.komentarapp.ui.tools.listeners.MenuListener;

public class RvItemMenuSocialNetwork implements RvItemMenu{

    private MenuListener listener;

    public RvItemMenuSocialNetwork(MenuListener listener) {
        this.listener = listener;
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public void bind(MenuAdapter.MenuHolder holder) {
        RvItemMenuSocialNetworkBinding binding = (RvItemMenuSocialNetworkBinding) holder.binding;

        binding.imageViewFacebook.setOnClickListener(view -> listener.onShareClicked("com.facebook.katana"));

        binding.imageViewInstagram.setOnClickListener(view -> listener.onShareClicked("com.instagram.android"));
        binding.imageViewTwitter.setOnClickListener(view -> listener.onShareClicked("com.twitter.android"));
        binding.imageViewViber.setOnClickListener(view -> listener.onShareClicked("com.viber.voip"));
        binding.imageViewWhatsapp.setOnClickListener(view -> listener.onShareClicked("com.whatsapp"));
    }
}
