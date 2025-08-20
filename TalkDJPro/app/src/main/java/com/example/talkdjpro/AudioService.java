package com.example.talkdjpro;

import android.content.Context;
import android.util.Log;
import android.media.AudioRecord;
import android.media.AudioFormat;
import android.media.MediaRecorder;

public class AudioService {

    private Context context;
    private Thread audioThread;
    private boolean running = false;

    public AudioService(Context context) {
        this.context = context;
    }

    public void startAudio() {
        if (running) return;
        running = true;

        audioThread = new Thread(() -> {
            int bufferSize = AudioRecord.getMinBufferSize(44100,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT);

            AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    44100,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    bufferSize);

            recorder.startRecording();
            byte[] buffer = new byte[bufferSize];

            while (running) {
                int read = recorder.read(buffer, 0, buffer.length);
                if (read > 0) {
                    // Aqui você enviaria o áudio via UDP ou WebRTC
                }
            }
            recorder.stop();
            recorder.release();
        });
        audioThread.start();
    }

    public void startDJMode() {
        Log.d("AudioService", "DJ Mode ativado (aqui você pode tocar música)");
    }

    public void stop() {
        running = false;
    }
}