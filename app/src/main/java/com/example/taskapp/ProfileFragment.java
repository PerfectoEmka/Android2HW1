package com.example.taskapp;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.taskapp.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ActivityResultLauncher<String> mGetContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding = FragmentProfileBinding.inflate(inflater, container, false);
         return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLocalContent();
        initEditTextListeners();

        binding.avatarIv.setOnClickListener(v -> {
            pickImage();
        });
        setImage();
    }

    private void initEditTextListeners() {
        binding.nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Prefs prefs = new Prefs(requireContext());
                prefs.saveName(editable.toString());
            }
        });
    }

    private void setLocalContent() {
        Prefs prefs = new Prefs(requireContext());
        String uri = prefs.getImage();
        String name = prefs.getName();

        Glide.with(requireActivity())
                .load(uri)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.avatarIv);

        binding.nameEt.setText(name);
    }

    private void setImage() {
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    Glide.with(requireActivity())
                            .load(uri)
                            .circleCrop()
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(binding.avatarIv);
                    Prefs prefs = new Prefs(requireContext());
                    prefs.saveAvatarImage(uri);
                });
    }

    private void pickImage() {
        mGetContent.launch("image/*");
    }
}