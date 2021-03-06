package com.rgi.hanumanchalisa.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rgi.hanumanchalisa.R;
import com.rgi.hanumanchalisa.adapter.ImageAdapter;
import com.rgi.hanumanchalisa.databinding.FragmentSetBackgroundBinding;

import java.util.ArrayList;


public class SetBackgroundFragment extends Fragment {
FragmentSetBackgroundBinding binding;
    ArrayList<Integer> advertiseList = new ArrayList<>();
    public SetBackgroundFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_background, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        //
    }

    private void init() {

        advertiseList.add(R.drawable.ic_feedback);
        advertiseList.add(R.drawable.image_three);
        advertiseList.add(R.drawable.image_four);
        advertiseList.add(R.drawable.image_five);
        advertiseList.add(R.drawable.image_two);

        binding.rvImages.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        binding.rvImages.setLayoutManager(layoutManager);
        binding.rvImages.setAdapter(new ImageAdapter(getActivity(),advertiseList));
    }
}