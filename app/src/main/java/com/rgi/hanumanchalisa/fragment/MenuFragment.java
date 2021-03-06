package com.rgi.hanumanchalisa.fragment;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rgi.hanumanchalisa.R;
import com.rgi.hanumanchalisa.databinding.FragmentMenuBinding;
import com.rgi.hanumanchalisa.receiver.AlarmReceiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import static android.content.Context.AUDIO_SERVICE;


public class MenuFragment extends Fragment {
    FragmentMenuBinding binding;
    TimePickerDialog timePickerDialog;
    final static int RQS_1 = 1;
    String path;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private String fNmae = "hanuman_chalisa.mp3";
    private String fPAth = "android.resource://com.rgi.hanumanchalisa/raw/hanuman_chalisa";

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item, menu);
        menu.findItem(R.id.action_down).setVisible(false);
        menu.findItem(R.id.action_up).setVisible(false);
        menu.findItem(R.id.action_change_lang).setVisible(false);
        menu.findItem(R.id.action_whatsapp).setVisible(false);
        menu.findItem(R.id.action_rating).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

/*
        if(id== R.id.action_menu){

            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Menu");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CD5526")));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.llAlarm.setOnClickListener(v -> {
            openTimePickerDialog(false);
        });

        binding.llRington.setOnClickListener(v -> {
            Log.e("TAG", "init: click ringtone ");

           /* if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                setRingTone();
            } else {
                // Request permission from the user
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

            }*/
            if (Build.VERSION.SDK_INT >= 23) {

                if (checkPermission()) {
                    if (Settings.System.canWrite(getActivity()))
                    {
                        setRingtone();
                    }
                    else
                    {
                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS)
                                .setData(Uri.parse("package:" +getActivity().getPackageName()))
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }


                    Log.e("value", "Permission already Granted, Now you can save image.");
                } else {
                    requestPermission();
                }
            }else{
                setRingtone();
                Log.e("value", "Not required for requesting runtime permission");
            }
        });

        binding.llBackImage.setOnClickListener(v->{
            NavController navController = Navigation.findNavController(binding.getRoot());
            navController.navigate(R.id.action_menuFragment_to_setBackgroundFragment);
        });

        binding.llSms.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setType("vnd.android-dir/mms-sms");
            int flags = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP;
            intent.setFlags(flags);
            String path = "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
            intent.putExtra("sms_body", path);
            intent.setData(Uri.parse("content://sms/inbox"));
            getActivity().startActivity(intent);
        });

        binding.llWhatsApp.setOnClickListener(v->{whatsAppMsg();});
    }

    private void whatsAppMsg() {
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


    private void setRingToneNew() {
        String exStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        path = (exStoragePath + "/media/alarms/");
        // saveas();
        Log.e("TAG", "inside setRingTone: ");
        saveas(RingtoneManager.TYPE_RINGTONE);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(getActivity(), "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can save image .");
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (Settings.System.canWrite(getActivity())) {
                            setRingtone();
                        } else {
                            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS)
                                    .setData(Uri.parse("package:" + getActivity().getPackageName()))
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                } else {
                    Log.e("value", "Permission Denied, You cannot save image.");
                }
                break;
        }
    }

    public boolean saveas(int type) {
        Log.e("TAG", "inside saveas: ");
        byte[] buffer = null;
        InputStream fIn = getActivity().getResources().openRawResource(
                R.raw.hanuman_chalisa);
        int size = 0;

        try {
            size = fIn.available();
            buffer = new byte[size];
            fIn.read(buffer);
            fIn.close();
        } catch (IOException e) {
            Log.e("TAG", "saveas: error 1" + e.getMessage());
            e.printStackTrace();
            return false;
        }


        String filename = "sound";

        boolean exists = (new File(path)).exists();
        if (!exists) {
            new File(path).mkdirs();
        }

        FileOutputStream save;
        try {
            save = new FileOutputStream(path + filename);
            save.write(buffer);
            save.flush();
            save.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }

        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + path + filename + ".mp3"
                + Environment.getExternalStorageDirectory())));


        File k = new File(path, filename);

        ContentValues values = new ContentValues();
        long current = System.currentTimeMillis();
        values.put(MediaStore.MediaColumns.DATA, path + filename);
        values.put(MediaStore.MediaColumns.TITLE, filename);
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp3");

        //new
        values.put(MediaStore.Audio.Media.ARTIST, "cssounds ");
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_ALARM, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        Log.e("TAG", "saveas: path " + path);

        Uri newUri = getActivity().getContentResolver()
                .insert(MediaStore.Audio.Media.getContentUriForPath(k
                        .getAbsolutePath()), values);
        RingtoneManager.setActualDefaultRingtoneUri(getActivity(), RingtoneManager.TYPE_RINGTONE, newUri);


        return true;

    }

    private void openTimePickerDialog(boolean is24r) {
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                getActivity(),
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.setTitle("Set Alarm Time");

        timePickerDialog.show();
    }


    OnTimeSetListener onTimeSetListener
            = new OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {
                //Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }

            setAlarm(calSet);
        }
    };

    private void setAlarm(Calendar targetCal) {

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }


    private void setRingtone(){
        AssetFileDescriptor openAssetFileDescriptor;
        ((AudioManager)  getActivity().getSystemService(AUDIO_SERVICE)).setRingerMode(2);
        File file = new File(Environment.getExternalStorageDirectory() + "/appkeeda", this.fNmae);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Uri parse = Uri.parse(this.fPAth);
        ContentResolver contentResolver = getActivity().getContentResolver();
        try {
            openAssetFileDescriptor = contentResolver.openAssetFileDescriptor(parse, "r");
        } catch (FileNotFoundException e2) {
            openAssetFileDescriptor = null;
        }
        try {
            byte[] bArr = new byte[1024];
            FileInputStream createInputStream = openAssetFileDescriptor.createInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (int read = createInputStream.read(bArr); read != -1; read = createInputStream.read(bArr)) {
                fileOutputStream.write(bArr, 0, read);
            }
            fileOutputStream.close();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", file.getAbsolutePath());
        contentValues.put("title", "nkDroid ringtone");
        contentValues.put("mime_type", "audio/mp3");
        contentValues.put("_size", Long.valueOf(file.length()));
        contentValues.put("artist", Integer.valueOf(R.string.app_name));
        contentValues.put("is_ringtone", Boolean.valueOf(true));
        contentValues.put("is_notification", Boolean.valueOf(false));
        contentValues.put("is_alarm", Boolean.valueOf(false));
        contentValues.put("is_music", Boolean.valueOf(false));
        try {
            Toast.makeText(getActivity(), new StringBuilder().append("Ringtone set successfully"), Toast.LENGTH_LONG).show();
            RingtoneManager.setActualDefaultRingtoneUri(getActivity(), 1, contentResolver.insert(MediaStore.Audio.Media.getContentUriForPath(file.getAbsolutePath()), contentValues));
        } catch (Throwable th) {
            Toast.makeText(getActivity(), new StringBuilder().append("Ringtone feature is not working"), Toast.LENGTH_LONG).show();
        }
    }


}