package com.rgi.hanumanchalisa.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rgi.hanumanchalisa.R;
import com.rgi.hanumanchalisa.databinding.FragmentChaliesTextBinding;


public class ChaliesTextFragment extends Fragment {
    FragmentChaliesTextBinding binding;
    boolean changeLang= false;

    public ChaliesTextFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chalies_text, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lang, menu);  // Use filter.xml from step 1
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_up){
            //Do whatever you want to do
            return true;
        }
        if(id == R.id.action_down){
            //Do whatever you want to do
            return true;
        }
        if(id == R.id.action_change_lang){
            //Do whatever you want to do
            if(changeLang){
                changeLang =false;
                binding.tvDohaHindi.setVisibility(View.VISIBLE);
                binding.tvDohaEng.setVisibility(View.GONE);
            }else{
                changeLang =true;
                binding.tvDohaHindi.setVisibility(View.GONE);
                binding.tvDohaEng.setVisibility(View.VISIBLE);
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void init() {
        binding.tvDohaHindi.setVisibility(View.VISIBLE);
        changeLang = false;
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Hanuman Chalisa");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CD5526")));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}