package com.example.taskapp.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private String[] title = new String[] {"Fast","Free","Powerful","Secure"};
    private int[] logo = new int[] {R.drawable.fast,R.drawable.free,R.drawable.powerful,R.drawable.secure};
    private String[] description = new String[] {"Faster then WhatsApp","Completely free", "Stronger then WhatsApp", "Better security then WhatsApp"};
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
        private ImageView logiIv;
        private TextView descriptionTv;
        private Button startBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.textTitleTv);
            logiIv = itemView.findViewById(R.id.logoIv);
            descriptionTv = itemView.findViewById(R.id.textDescTv);
            startBtn = itemView.findViewById(R.id.startBtn);
        }

        public void onBind(int position) {
            titleTv.setText(title[position]);
            descriptionTv.setText(description[position]);
            logiIv.setImageResource(logo[position]);
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
