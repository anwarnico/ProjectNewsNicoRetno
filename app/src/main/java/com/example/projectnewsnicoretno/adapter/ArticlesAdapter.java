package com.example.projectnewsnicoretno.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectnewsnicoretno.R;
import com.example.projectnewsnicoretno.model.ArticlesItem;
import com.example.projectnewsnicoretno.room.tables.Articles;
import com.example.projectnewsnicoretno.ui.DetailNewsFragment;
import com.example.projectnewsnicoretno.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {
    private List<Articles> articleList;
    Context context;
    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFERENCE_NAME = "com.example.projectnewsnicoretno.sharedpref";

    public ArticlesAdapter(List<Articles> articleList){
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_article_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articles articles = articleList.get(position);
        LocalDateTime localDateTime = LocalDateTime.parse(articles.publishedAt, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        String formattedDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        holder.tvTitle.setText(articles.title);
        holder.tvPublishedAt.setText(formattedDate);
        Glide.with(context)
                .load(articles.urlToImage)
                .circleCrop()
                .into(holder.newsImage);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle, tvPublishedAt;
        ImageView newsImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPublishedAt = itemView.findViewById(R.id.tvPublishedAt);
            newsImage = itemView.findViewById(R.id.newsImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAbsoluteAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                Articles articles = articleList.get(position);
                ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, new DetailNewsFragment(), "DetailNewsFragment")
                        .addToBackStack("DetailNewsFragment").commit();
                sharedPreferences.edit().putString("article", articles.title).apply();
            }
        }
    }

}
