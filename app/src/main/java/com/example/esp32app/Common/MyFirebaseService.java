package com.example.esp32app.Common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.esp32app.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MyFirebaseService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        sendNotification(remoteMessage.getData());
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to save device token to Database.
    }

    // create Intent follow acction
    private Intent createIntent(String actionName, int notificationId, String mission) {
        Intent intent = new Intent(this, MissionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction(actionName);
        intent.putExtra("NOTIFICATION_ID", notificationId);
        intent.putExtra("MISSION", mission);

        return intent;
    }

    private void sendNotification(Map data) {
        try {
            String name = (String) data.get("name");
            String mission = (String) data.get("mission");
            int status = Integer.parseInt((String) data.get("status"));
            int notificationId = new Random().nextInt();

            String title = "Hi " + name + "! You have a new Mission!";
            String message = "Mission: " + mission;

            Intent acceptIntent = createIntent(MissionActivity.ACCEPT_ACTION, notificationId, mission);
            Intent rejectIntent = createIntent(MissionActivity.REJECT_ACTION, notificationId, mission);
            Intent intent = createIntent(MissionActivity.SHOW_ACTION, notificationId, mission);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            String channelId = getString(R.string.project_id);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                            .setContentTitle(title)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setPriority(NotificationManager.IMPORTANCE_HIGH)
                            .addAction(new NotificationCompat.Action(
                                    android.R.drawable.sym_call_missed,
                                    "Reject",
                                    PendingIntent.getActivity(this, 0, rejectIntent, PendingIntent.FLAG_CANCEL_CURRENT)))
                            .addAction(new NotificationCompat.Action(
                                    android.R.drawable.sym_call_outgoing,
                                    "Accept",
                                    PendingIntent.getActivity(this, 0, acceptIntent, PendingIntent.FLAG_CANCEL_CURRENT)));

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);

                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(notificationId, notificationBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
