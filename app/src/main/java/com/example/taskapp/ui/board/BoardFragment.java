package com.example.taskapp.ui.board;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskapp.Prefs;
import com.example.taskapp.R;
import com.example.taskapp.databinding.FragmentBoardBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class BoardFragment extends Fragment implements BoardAdapter.OnClick {

    private FragmentBoardBinding binding;
    private DotsIndicator dot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewPager();

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                closeApp();
            }
        });

        binding.skipBtn.setOnClickListener(v -> {
            closeFragment();
        });

    }

    private void closeFragment(){
        Prefs prefs = new Prefs(requireContext());
        prefs.saveBoardState();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }

    private void initViewPager() {
        BoardAdapter adapter = new BoardAdapter();
        adapter.initListener(this);
        ViewPager2 viewPager2 = binding.viewPager;
        viewPager2.setAdapter(adapter);
        dot = binding.dotsIndicator;
        dot.setViewPager2(viewPager2);
    }

    private void closeApp(){
        requireActivity().finish();
    }

    @Override
    public void OnClick() {
        closeFragment();
    }
}