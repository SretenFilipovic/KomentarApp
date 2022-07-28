package com.cubes.komentarapp.ui.detail;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentarapp.databinding.RvItemTagBinding;
import com.cubes.komentarapp.data.model.Tags;
import com.cubes.komentarapp.ui.tags.TagActivity;

import java.util.ArrayList;

// Ovaj adapter sluzi za prikaz liste tagova
// setuje se na RV u RvItemDetailTags

public class NewsDetailTagAdapter extends RecyclerView.Adapter<NewsDetailTagAdapter.TagHolder> {

    private ArrayList<Tags> tagList;

    public NewsDetailTagAdapter(ArrayList<Tags> tagList) {
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvItemTagBinding binding =
                RvItemTagBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewsDetailTagAdapter.TagHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {

            Tags tag = tagList.get(position);
            holder.binding.buttonTag.setText(tag.title);

        //klik na tag

        holder.binding.buttonTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(holder.itemView.getContext(), TagActivity.class);
                i.putExtra("tag", tag);
                holder.itemView.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }



    public class TagHolder extends RecyclerView.ViewHolder{

        public RvItemTagBinding binding;

        public TagHolder(@NonNull RvItemTagBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
