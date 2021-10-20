package kg.example.taskapp.ui.fragments.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import kg.example.taskapp.preference.Prefs;
import kg.geektech.taskapp35.R;
import kg.geektech.taskapp35.databinding.FragmentProfileBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ActivityResultLauncher<String> mGetContent;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();


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

        binding.btnSignOut.setOnClickListener(v -> {
            signOut();
        });

        binding.btnSignIn.setOnClickListener(view1 -> {
            signIn();
        });
    }

    private void signIn() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.loginFragment);
    }

    @SuppressLint("ResourceType")
    private void signOut() {
        if (user != null){
            AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());
            dialog.setTitle("Attention !")
                    .setMessage("Sign out? ")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(requireActivity(), "SignOut: " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            signOutUser();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show() ;
        }
    }

    private void signOutUser() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.loginFragment);
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