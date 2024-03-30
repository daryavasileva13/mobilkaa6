package com.example.practice6;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.Manifest;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.practice6.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {
    private static final int PERMISSION_NOTIFICATION = 1;
    private static final int PERMISSION_OVERLAY = 2;
    private NotificationChannel channel;
    private NotificationManager manager;
    private FragmentMainBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonGetNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
                drawBanner();
            }
        });
        channel = new NotificationChannel(
                "MyNotification", "Notification", NotificationManager.IMPORTANCE_DEFAULT);
        manager = getContext().getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }
    public void showNotification(){
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.POST_NOTIFICATIONS},
                    PERMISSION_NOTIFICATION);
            return;
        }
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(getContext(), channel.getId())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Практическая работа №6")
                .setContentText("Уведомление :)")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(1, builder.build());
    }

    public void drawBanner(){
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {
                            Manifest.permission.SYSTEM_ALERT_WINDOW
                    },
                    PERMISSION_OVERLAY);
        }
        getContext().startService(new Intent(getActivity(), MyService.class));
    }
}
