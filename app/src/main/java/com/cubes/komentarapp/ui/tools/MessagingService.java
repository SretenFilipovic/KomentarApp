package com.cubes.komentarapp.ui.tools;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.ui.main.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        Log.e("NEW_TOKEN", token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

//        String url = remoteMessage.getData().get("url");
//
//        if (url == null) {
//            Log.d("TAG", "onMessageReceived: URL IS NULL");
//        } else {
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//            startActivity(i);
//        }

        }

}
