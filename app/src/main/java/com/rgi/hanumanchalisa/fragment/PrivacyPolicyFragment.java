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
import android.view.View;
import android.view.ViewGroup;

import com.rgi.hanumanchalisa.R;
import com.rgi.hanumanchalisa.databinding.FragmentPrivacyPolicyBinding;


public class PrivacyPolicyFragment extends Fragment {
    FragmentPrivacyPolicyBinding binding;

    public PrivacyPolicyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_privacy_policy, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            PrivacyPolicyFragmentArgs args = PrivacyPolicyFragmentArgs.fromBundle(getArguments());
            if (args.getType().equalsIgnoreCase("contact")) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Contact Us");
                binding.llContact.setVisibility(View.VISIBLE);
            } else if(args.getType().equalsIgnoreCase("privacy")){
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Privacy Policy");
                binding.llPrivacy.setVisibility(View.VISIBLE);
            }else if(args.getType().equalsIgnoreCase("terms")){
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getText(R.string.str_terms));
                binding.llTerm.setVisibility(View.VISIBLE);
            }else{
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("About Us");
            }
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CD5526")));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}