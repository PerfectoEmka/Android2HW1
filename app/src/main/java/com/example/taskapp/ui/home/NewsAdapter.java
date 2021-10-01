package com.example.taskapp.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.News;
import com.example.taskapp.databinding.ItemNewsBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> list = new ArrayList<>();
    OnLongClick onLongClick;

    void addItem(News news){
        list.add(0, news);
        notifyItemInserted(0); // обновляет 1 элемент
        //notifyDataSetChanged(); обновляет все элементы
        notifyDataSetChanged();
    }

    public void initListeners(OnLongClick onLongClick){
        this.onLongClick = onLongClick;
    }

    public void removeItem(int position){
        list.remove(position);
        notifyDataSetChanged();
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
            if (((float) position % 2)==0){
                binding.container.setBackgroundColor(Color.GRAY);
            }
            binding.titleTv.setText(s.getTitle());
            binding.timeTv.setText(getCurrentTime());

            itemView.setOnLongClickListener(view -> {
                    onLongClick.OnLongClick(position);
                return true;
            });

            itemView.setOnClickListener(view -> {
                onLongClick.OnClick(list.get(position), position);
            });
        }

        private String getCurrentTime(){
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("h:mm a");
            String date = df.format(Calendar.getInstance().getTime());
            return date;
        }
    }




    public interface OnLongClick {
        public void OnLongClick(int position);
        public void OnClick(News news ,int position);
    }
}
