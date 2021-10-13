package com.example.taskapp.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.taskapp.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private String[] title = new String[] {"Fast","Free","Powerful","Secure"};
    private String[] description = new String[] {"Faster then WhatsApp","Completely free", "Stronger then WhatsApp", "Better security then WhatsApp"};
    private int[] animations = new int[] {R.raw.fast_animation, R.raw.free_animation, R.raw.powerful_animation, R.raw.secure_animation};
    private OnClick onClick;

    public void initListener(OnClick onClick){
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_board, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTv;
        private LottieAnimationView lottieAnimationView;
        private TextView descriptionTv;
        private Button startBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.textTitleTv);
            descriptionTv = itemView.findViewById(R.id.textDescTv);
            startBtn = itemView.findViewById(R.id.startBtn);
            lottieAnimationView = itemView.findViewById(R.id.fastAnimation);
        }

        public void onBind(int position) {
            titleTv.setText(title[position]);
            descriptionTv.setText(description[position]);
            lottieAnimationView.setAnimation(animations[position]);

            if (position == title.length - 1){
                startBtn.setVisibility(View.VISIBLE);
            } else {startBtn.setVisibility(View.GONE);}

            startBtn.setOnClickListener(view -> {
                onClick.OnClick();
            });
        }
    }
    public interface OnClick{
        void OnClick();
    }
}
