package kg.example.taskapp.ui.fragments.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import kg.example.taskapp.models.News;
import kg.geektech.taskapp35.R;
import kg.geektech.taskapp35.databinding.FragmentNewsBinding;

import com.google.firebase.firestore.FirebaseFirestore;

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
            saveData();
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

    private void saveData() {
        String txt = Objects.requireNonNull(binding.editText.getText()).toString();
        News news = new News(txt);
        news.setCreatedAt(System.currentTimeMillis());

        saveToFireStore(news);

        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putBoolean("bool", bool);
        b.putSerializable("news", news);
        getParentFragmentManager().setFragmentResult("rk_news", b);
    }

    private void saveToFireStore(News news) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("news").add(news).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Error " + task.getException(), Toast.LENGTH_SHORT).show();
            }
            close();
        });
    }

    private void close() {
    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
    navController.navigateUp();
    }
}