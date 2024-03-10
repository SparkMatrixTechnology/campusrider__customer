package com.campusrider.campusridercustomer.models;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

import com.campusrider.campusridercustomer.AfterLoginActivity;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessegingService extends FirebaseMessagingService {
    private static  final String ORDER_CHANNEL_ID="Order Update";
    private static  final int ORDER_NOTIFICATION_ID=100;
    private static  final int ORDER_REQUEST_CODE=100;
    SharedPrefManager sharedPrefManager;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        Drawable drawable= ResourcesCompat.getDrawable(getResources(), R.drawable.logo1,null);

        BitmapDrawable bitmapDrawable=(BitmapDrawable) drawable;
        Bitmap largeIcon=bitmapDrawable.getBitmap();
        Intent intent=new Intent(getApplicationContext(), AfterLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        RemoteMessage.Notification notification = message.getNotification();

        PendingIntent pendingIntent=PendingIntent.getActivity(this,ORDER_REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .build();

            NotificationChannel channel = new NotificationChannel(ORDER_CHANNEL_ID, "Order Update Channel", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 100});
            notificationManager.createNotificationChannel(channel);


        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ORDER_CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.logo1)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Notification ordernotification = notificationBuilder.build();
        notificationManager.notify(ORDER_NOTIFICATION_ID, ordernotification);
    }

}
