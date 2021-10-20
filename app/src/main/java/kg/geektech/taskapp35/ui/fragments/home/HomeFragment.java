package kg.geektech.taskapp35.ui.fragments.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import kg.geektech.taskapp35.models.News;
import kg.geektech.taskapp35.R;
import kg.geektech.taskapp35.databinding.FragmentHomeBinding;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HomeFragment extends Fragment implements NewsAdapter.OnLongClick{

    private FragmentHomeBinding binding;
    private NewsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();
        readData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = binding.fab;
        binding.fab.setOnClickListener(v -> {
            openFragment();
        });

        getParentFragmentManager().setFragmentResultListener("rk_news", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                News news = (News) result.getSerializable("news");
                int position = result.getInt("position");
                boolean isEdited = result.getBoolean("bool");
                // Log.e("HomeFragment Sent text", "text = " + news.getTitle()); // logCat
                if (isEdited){
                    adapter.editItem(position, news);
                } else {
                    adapter.addItem(news);
                }
            }
        });
        initList();
    }

    private void initList() {
        adapter.initListeners(this);
        binding.recyclerView.setAdapter(adapter);
    }

    private void openFragment() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.taskFragment);
    }

    @Override
    public void OnLongClick(int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());
        dialog.setTitle("Attention !!")
                .setMessage("Delete item ? " + "\"" + adapter.getItem(position).getTitle() + "\"")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.removeItem(position);
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show() ;
    }

    private void readData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("news").get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<News> list = queryDocumentSnapshots.toObjects(News.class);
            adapter.addItems(list);
        });
    }

    private void readDataLive() {

    }

    @Override
    public void OnClick(int position) {
        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putSerializable("news", adapter.getItem(position));
        getParentFragmentManager().setFragmentResult("rk_edit_news", b);
        openFragment();
    }
}