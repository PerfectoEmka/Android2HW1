package com.example.taskapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskapp.databinding.FragmentNewsBinding;

import java.util.Objects;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private int position;
    private boolean bool = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getData();

        binding.btnSave.setOnClickListener(view1 -> {
            sendData();
        });
    }

    private void getData(){
        getParentFragmentManager().setFragmentResultListener("rk_edit_news", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                News news = (News) result.getSerializable("news");
                position = result.getInt("position");
                binding.editText.setText(news.getTitle().toString());
                bool = true;
            }
        });
    }

    private void sendData() {
        String txt = Objects.requireNonNull(binding.editText.getText()).toString();
        News news = new News(txt);
        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putBoolean("bool", bool);
        b.putSerializable("news", news);
        getParentFragmentManager().setFragmentResult("rk_news", b);
        close();
    }

    private void close() {
    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
    navController.navigateUp();
    }
}