package kg.geektech.taskapp35.ui.fragments.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kg.geektech.taskapp35.NewsComparator;
import kg.geektech.taskapp35.R;
import kg.geektech.taskapp35.models.News;
import kg.geektech.taskapp35.databinding.ItemNewsBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> list = new ArrayList<>();
    private OnLongClick onLongClick;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();

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

    public void addItems(List<News> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        list.remove(position);
        notifyDataSetChanged();
        notifyItemRemoved(position);
    }

    public void editItem(int position, News news){
        list.remove(position);
        list.add(news);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collections.sort(list, new NewsComparator());
        Collections.reverse(list);
        holder.onBind(position);
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

        public void onBind(int position) {
            if (position % 2 == 0){
                binding.container.setBackgroundColor(Color.LTGRAY);
            } else {
                binding.container.setBackgroundColor(Color.WHITE);
            }

            binding.titleTv.setText(list.get(position).getTitle());
            binding.timeTv.setText(String.valueOf(changeTypeOfDateToAgo(list.get(position).getCreatedAt())));

            if (list.get(position).getEmail() != null){
                binding.emailTv.setText(list.get(position).getEmail());
            } else {
                binding.emailTv.setText("User not found");
            }

            itemView.setOnLongClickListener(view -> {
                        onLongClick.OnLongClick(position);
                return true;
            });

            itemView.setOnClickListener(view -> {
                if (list.get(position).getEmail() != null){
                    if ((list.get(position).getEmail()).equals(user.getEmail())) {
                        onLongClick.OnClick(position);
                    }
                }
            });
        }

        private String changeTypeOfDateToAgo(Object createdAt) {

            long milliSecPerMinute = 60 * 1000; //Milliseconds Per Minute
            long milliSecPerHour = milliSecPerMinute * 60; //Milliseconds Per Hour
            long milliSecPerDay = milliSecPerHour * 24; //Milliseconds Per Day
            long milliSecPerMonth = milliSecPerDay * 30; //Milliseconds Per Month
            long milliSecPerYear = milliSecPerDay * 365; //Milliseconds Per Year
            String agoTime;
            //Difference in Milliseconds between two dates
            if (createdAt != null) {
                long msExpired = System.currentTimeMillis() - (Long) createdAt;
                //Second or Seconds ago calculation
                if (msExpired < milliSecPerMinute) {
                    if (Math.round(msExpired / 1000) == 1) {
                        return agoTime = String.valueOf(Math.round(msExpired / 1000)) + " second ago... ";
                    } else {
                        return agoTime = String.valueOf(Math.round(msExpired / 1000) + " seconds ago...");
                    }
                }
                //Minute or Minutes ago calculation
                else if (msExpired < milliSecPerHour) {
                    if (Math.round(msExpired / milliSecPerMinute) == 1) {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerMinute)) + " minute ago... ";
                    } else {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerMinute)) + " minutes ago... ";
                    }
                }
                //Hour or Hours ago calculation
                else if (msExpired < milliSecPerDay) {
                    if (Math.round(msExpired / milliSecPerHour) == 1) {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerHour)) + " hour ago... ";
                    } else {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerHour)) + " hours ago... ";
                    }
                }
                //Day or Days ago calculation
                else if (msExpired < milliSecPerMonth) {
                    if (Math.round(msExpired / milliSecPerDay) == 1) {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerDay)) + " day ago... ";
                    } else {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerDay)) + " days ago... ";
                    }
                }
                //Month or Months ago calculation
                else if (msExpired < milliSecPerYear) {
                    if (Math.round(msExpired / milliSecPerMonth) == 1) {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerMonth)) + "  month ago... ";
                    } else {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerMonth)) + "  months ago... ";
                    }
                }
                //Year or Years ago calculation
                else {
                    if (Math.round(msExpired / milliSecPerYear) == 1) {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerYear)) + " year ago...";
                    } else {
                        return agoTime = String.valueOf(Math.round(msExpired / milliSecPerYear)) + " years ago...";
                    }
                }
            }
            return agoTime = "time not found";
        };
    }

    public interface OnLongClick {
        public void OnLongClick(int position);
        public void OnClick(int position);
    }
}
