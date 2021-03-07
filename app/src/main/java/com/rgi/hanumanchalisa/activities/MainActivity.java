package com.rgi.hanumanchalisa.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.rgi.hanumanchalisa.R;
import com.rgi.hanumanchalisa.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    NavController navController;
    public static MediaPlayer mediaPlayer;
    public static int test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //((MainActivity) mcontext)
        mediaPlayer = new MediaPlayer();
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mainBinding.toolbar);
        navController = Navigation.findNavController(this, R.id.fragment2);
        //  NavigationUI.setupActionBarWithNavController(MainActivity.this, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    @Override
    public void onBackPressed() {
        if (Navigation.findNavController(this, R.id.fragment2)
                .getCurrentDestination().getId() == R.id.homeFragment) {
            // handle back button the way you want here
            showCloseAlert();

            return;
        }
        super.onBackPressed();

    }

    private void showCloseAlert() {
        new AlertDialog.Builder(this).setTitle(getString(R.string.str_exit))
                .setMessage(getString(R.string.str_are_sure))
                .setPositiveButton(getString(R.string.str_yes),
                        (dialog, which) -> {
                            // Perform Action & Dismiss dialog
                            finish();
                            System.exit(0);
                        }).setNegativeButton(getString(R.string.str_no), (dialog, which) -> {
            // Do nothing
            dialog.dismiss();
        }).create().show();
    }
}