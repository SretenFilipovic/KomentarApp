package com.cubes.komentarapp.ui.comments;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.Comments;
import com.cubes.komentarapp.databinding.RvItemCommentBinding;
import com.cubes.komentarapp.databinding.RvItemCommentChildBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.comments.item.RvItemCommentsChild;
import com.cubes.komentarapp.ui.comments.item.RvItemCommentsParent;
import com.cubes.komentarapp.ui.tools.RvItem;
import com.cubes.komentarapp.ui.tools.listeners.CommentsListener;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final ArrayList<RvItem> items = new ArrayList<>();
    private CommentsListener listener;

    public CommentsAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == R.layout.rv_item_comment_child) {
            binding = RvItemCommentChildBinding.inflate(inflater, parent, false);
        } else {
            binding = RvItemCommentBinding.inflate(inflater, parent, false);
        }
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.items.get(position).bind(holder);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void updateList(ArrayList<Comments> commentsData) {

        for (Comments comment : commentsData){

            if (comment.parent_comment.equals("0")) {
                items.add(new RvItemCommentsParent(comment, listener));
            }
            else {
                items.add(new RvItemCommentsChild(comment, listener));
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    public void setCommentListener(CommentsListener commentsListener) {
        this.listener = commentsListener;
    }


//    public void updateUpvote(int position){
//        Comments comment = comments.get(position);
//        comment.positive_votes = comment.positive_votes +1;
//        notifyItemChanged(position);
//    }
//
//    public void updateDownvote(int position){
//        Comments comment = comments.get(position);
//        comment.negative_votes = comment.negative_votes +1;
//        notifyItemChanged(position);
//    }

}
