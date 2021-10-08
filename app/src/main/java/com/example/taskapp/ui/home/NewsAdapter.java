package com.example.taskapp.ui.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.models.News;
import com.example.taskapp.databinding.ItemNewsBinding;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> list = new ArrayList<>();
    OnLongClick onLongClick;

    public void initListeners(OnLongClick onLongClick){
        this.onLongClick = onLongClick;
    }

    public News getItem(int position){
        return list.get(position);
    }

    public void addItem(News news){
        list.add(0, news);
        notifyItemInserted(0); // обновляет 1 элемент
        //notifyDataSetChanged(); обновляет все элементы
        notifyDataSetChanged();
    }


    public void removeItem(int position){
        list.remove(position);
        notifyDataSetChanged();
        notifyItemRemoved(position);
    }

    public void editItem(int position, News news){
        list.set(position,news);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemNewsBinding binding;

        public ViewHolder(@NonNull ItemNewsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void onBind(News s, int position) {
            if (((float) position % 2)!=0){
                binding.container.setBackgroundColor(Color.GRAY);
            }
            binding.titleTv.setText(s.getTitle());
            binding.timeTv.setText(s.getDate());

            itemView.setOnLongClickListener(view -> {
                    onLongClick.OnLongClick(position);
                return true;
            });

            itemView.setOnClickListener(view -> {
                onLongClick.OnClick(position);
            });
        }
    }




    public interface OnLongClick {
        public void OnLongClick(int position);
        public void OnClick(int position);
    }
}
