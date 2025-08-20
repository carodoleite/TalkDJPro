package com.example.talkdjpro;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private AudioService audioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioService = new AudioService(this);

        Button intercomButton = findViewById(R.id.button_start_intercom);
        Button djButton = findViewById(R.id.button_start_dj);

        ActivityResultLauncher<String[]> permissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                    boolean granted = true;
                    for (Boolean b : result.values()) if (!b) granted = false;
                    if (granted) audioService.startAudio();
                });

        intercomButton.setOnClickListener(v -> {
            if (hasPermissions()) {
                audioService.startAudio();
            } else {
                permissionLauncher.launch(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET});
            }
        });

        djButton.setOnClickListener(v -> audioService.startDJMode());
    }

    private boolean hasPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }
}