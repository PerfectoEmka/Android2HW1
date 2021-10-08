package com.example.taskapp.ui.board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskapp.R;
import com.example.taskapp.databinding.FragmentBoardBinding;
import com.example.taskapp.databinding.FragmentNewsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BoardFragment extends Fragment implements BoardAdapter.OnClick {

    FragmentBoardBinding binding;

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
            close();
        });

    }

    private void close(){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }

    private void initViewPager() {
        BoardAdapter adapter = new BoardAdapter();
        adapter.initListener(this);
        ViewPager2 viewPager2 = binding.viewPager;
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setIcon(R.drawable.dot);
            }
        }).attach();
    }

    private void closeApp(){
        requireActivity().finish();
    }

    @Override
    public void OnClick() {
        close();
    }
}