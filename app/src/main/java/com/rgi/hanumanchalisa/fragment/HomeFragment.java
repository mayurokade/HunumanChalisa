package com.rgi.hanumanchalisa.fragment;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.rgi.hanumanchalisa.R;
import com.rgi.hanumanchalisa.adapter.SliderAdapter;
import com.rgi.hanumanchalisa.databinding.FragmentHomeBinding;
import com.rgi.hanumanchalisa.services.MediaplayerService;
import com.smarteist.autoimageslider.SliderView;
import com.whinc.widget.ratingbar.RatingBar;

import java.util.ArrayList;
import java.util.Timer;

import static com.rgi.hanumanchalisa.app.App.mediaPlayer;


public class HomeFragment extends Fragment {
    public static FragmentHomeBinding binding;
    Dialog dialog;
    ArrayList<Integer> advertiseList = new ArrayList<>();
    Handler handler = new Handler();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item, menu);
        menu.findItem(R.id.action_down).setVisible(false);
        menu.findItem(R.id.action_up).setVisible(false);
        menu.findItem(R.id.action_change_lang).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        final NavController navController = Navigation.findNavController(binding.getRoot());
        if (id == R.id.action_menu) {
            navController.navigate(R.id.action_homeFragment_to_menuFragment);
            return true;
        }

        if (id == R.id.action_feedback) {
            Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(myAppLinkToMarket);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getActivity(), " unable to find market app", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (id == R.id.action_privacy) {
            HomeFragmentDirections.ActionHomeFragmentToPrivacyPolicyFragment action =
                    HomeFragmentDirections.actionHomeFragmentToPrivacyPolicyFragment();
            action.setType("privacy");
            navController.navigate(action);
            return true;
        }
        if (id == R.id.action_terms) {
            HomeFragmentDirections.ActionHomeFragmentToPrivacyPolicyFragment action =
                    HomeFragmentDirections.actionHomeFragmentToPrivacyPolicyFragment();
            action.setType("terms");
            navController.navigate(action);
            return true;
        }
        if (id == R.id.action_contact) {
            HomeFragmentDirections.ActionHomeFragmentToPrivacyPolicyFragment action =
                    HomeFragmentDirections.actionHomeFragmentToPrivacyPolicyFragment();
            action.setType("contact");
            navController.navigate(action);
            return true;
        }
        if (id == R.id.action_about) {
            HomeFragmentDirections.ActionHomeFragmentToPrivacyPolicyFragment action =
                    HomeFragmentDirections.actionHomeFragmentToPrivacyPolicyFragment();
            action.setType("about");
            navController.navigate(action);
            return true;
        }

        if (id == R.id.action_rating) {
            ratingDialog();
            return true;
        }
        if (id == R.id.action_Rating) {
            ratingDialog();
            return true;
        }
        if (id == R.id.action_whatsapp) {
            whatsAppShare();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void whatsAppShare() {
        PackageManager pm = getActivity().getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            String path = "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
            waIntent.setType("text/plain");
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            waIntent.setPackage("com.whatsapp");
            waIntent.putExtra(Intent.EXTRA_TEXT, path);
            startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ratingDialog() {
        // custom dialog
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.feedback_dialog);
        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        TextView tvNoThanks = dialog.findViewById(R.id.tvNoThanks);

        btnSubmit.setOnClickListener(v -> {
            if (ratingBar.getCount() > 0) {
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), " unable to find market app", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "Please select Rating", Toast.LENGTH_SHORT).show();
            }
        });

        tvNoThanks.setOnClickListener(v -> {
            dialog.dismiss();
        });

        // set the custom dialog components - text, image and button


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
    }


    private void init() {
        //  setSupportActionBar();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);


        binding.seekBar.setMax(100);
        binding.ivRightBell.setOnClickListener(v -> {
            final NavController navController = Navigation.findNavController(binding.getRoot());
            navController.navigate(R.id.action_homeFragment_to_chaliesTextFragment);

        });


        MediaPlayer mpShank = MediaPlayer.create(getActivity(), R.raw.shankh_sound);
        binding.ivShank.setOnClickListener(v -> {
            Animation animZoomOut = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_out);
            binding.ivShank.startAnimation(animZoomOut);
            if (!mpShank.isPlaying())
                mpShank.start();
        });


        MediaPlayer mpBell = MediaPlayer.create(getActivity(), R.raw.temple_bell);
        binding.ivLeftBell.setOnClickListener(v -> {
            Animation rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
            binding.ivLeftBell.setAnimation(rotate);
            if (!mpBell.isPlaying())
                mpBell.start();
        });


        binding.ivPlay.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                handler.removeCallbacks(updater);
                mediaPlayer.pause();
                binding.ivPlay.setImageResource(R.drawable.ic_play);
                getActivity().stopService(new Intent(getActivity(),MediaplayerService.class));
            } else {
                mediaPlayer.start();
                binding.ivPlay.setImageResource(R.drawable.ic_pause);
                updateSeekBar();
                getActivity().startService(new Intent(getActivity(), MediaplayerService.class));
            }
        });

        binding.ivNext.setOnClickListener(v->{resetMedia();});
        binding.ivPrevious.setOnClickListener(v->{resetMedia();});


        //   loadMusic();
        if (!mediaPlayer.isPlaying())
            prepareMediaPlayer();

        binding.seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar seekBar = (SeekBar) v;
                int playposition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playposition);
                binding.tvCompleted.setText(millisecondToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                binding.seekBar.setSecondaryProgress(percent);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                resetMedia();
            }
        });
    }

    private void resetMedia() {
        if (mediaPlayer != null) {
            binding.seekBar.setProgress(0);
            binding.ivPlay.setImageResource(R.drawable.ic_play);
            binding.tvCompleted.setText("0.00");
            mediaPlayer.reset();
            getActivity().stopService(new Intent(getActivity(),MediaplayerService.class));
            prepareMediaPlayer();

        }
    }

    public void prepareMediaPlayer() {

        try {
            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.hanumanchalisa);
//            mediaPlayer.prepare();
            binding.tvTotalTime.setText(millisecondToTimer(mediaPlayer.getDuration()));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            binding.tvCompleted.setText(millisecondToTimer(currentDuration));
        }
    };

    public void updateSeekBar() {
        if (mediaPlayer.isPlaying()) {
            binding.seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater, 1000);
        }
    }

    public String millisecondToTimer(long milliseconds) {
        String timeStarting = "";
        String secondsString;
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours > 0) {
            timeStarting = hours + ":";
        }
        if (seconds < 10)
            secondsString = "0" + seconds;
        else
            secondsString = "" + seconds;

        timeStarting = timeStarting + minutes + ":" + secondsString;
        return timeStarting;
    }


    private void loadBanner() {

      //  advertiseList.add(R.drawable.ic_feedback);
        advertiseList.add(R.drawable.image_three);
        advertiseList.add(R.drawable.image_four);
        advertiseList.add(R.drawable.image_five);
        advertiseList.add(R.drawable.image_two);

        SliderAdapter adapter = new SliderAdapter(getActivity(), advertiseList);
        binding.slider.setSliderAdapter(adapter);
        binding.slider.setScrollTimeInSec(3);
        binding.slider.setAutoCycle(true);
        binding.slider.startAutoCycle();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mediaPlayer.isPlaying()) {
            binding.ivPlay.setImageResource(R.drawable.ic_pause);
            updateSeekBar();
            binding.tvTotalTime.setText(millisecondToTimer(mediaPlayer.getDuration()));
        }
        loadBanner();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}