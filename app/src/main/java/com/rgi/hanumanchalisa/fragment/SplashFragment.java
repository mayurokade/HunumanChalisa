package com.rgi.hanumanchalisa.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rgi.hanumanchalisa.R;
import com.rgi.hanumanchalisa.utils.Constant;


public class SplashFragment extends Fragment {

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        final NavController navController = Navigation.findNavController(view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "run: inside splash ");
              /*  navController.navigate(R.id.action_splashFragment_to_homeFragment3, null, new NavOptions.Builder()
                        .setPopUpTo(R.id.splashFragment,
                                true).build());*/
            }
        }, Constant.SPLASH_TIME_OUT);
    }
}