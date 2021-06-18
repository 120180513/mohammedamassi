package com.iug.jerusalem.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iug.jerusalem.R;
import com.iug.jerusalem.pogo.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {

    private Context context;
    private List<Article> articleList;

    public ArticleAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_recyclerview_design, parent, false);
        return new ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        Article article = articleList.get(position);

        holder.textView.setText(article.getDetails().trim());
        holder.textView.setTextSize(getFontSize());

        Glide.with(context)
                .load(article.getImage())
                .centerCrop()
                .into(holder.image_background);

        if (article.getContainsVideo() == 1) {
            holder.image_play.setVisibility(View.VISIBLE);
        } else {
            holder.image_play.setVisibility(View.GONE);
        }

        holder.image_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("url", article.getVideo());
                context.startActivity(intent);
            }
        });

        holder.buttonReadAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleDetailsActivity.class);
                intent.putExtra("details", article.getDetails());
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(0, 0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class ArticleHolder extends RecyclerView.ViewHolder {

        ImageView image_background, image_play;
        TextView textView;
        Button buttonReadAll;

        public ArticleHolder(@NonNull View itemView) {
            super(itemView);

            image_background = itemView.findViewById(R.id.image_background);
            image_play = itemView.findViewById(R.id.image_play);
            textView = itemView.findViewById(R.id.textView);
            buttonReadAll = itemView.findViewById(R.id.buttonReadAll);

        }
    }

    private int getFontSize() {
        SharedPreferences sp = context.getSharedPreferences("Setting", Context.MODE_PRIVATE);
        return  sp.getInt("FontSize", 18);
    }

}
