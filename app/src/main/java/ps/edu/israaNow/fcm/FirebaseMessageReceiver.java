package ps.edu.israaNow.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import ps.edu.israaNow.DetailsEvent;
import ps.edu.israaNow.R;

public class FirebaseMessageReceiver extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("dataChat", remoteMessage.getData().toString());
        try {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            sendNotification(object);
        } catch (Exception e) {
            e.getMessage();
            Log.e("error", e.getMessage());
        }

    }

    private void sendNotification(JSONObject jsonObject) {
        try {
            String body = jsonObject.getString("body");
            String id = jsonObject.getString("id");
            Notification.Builder builder = new Notification.Builder(this);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setContentTitle(body)
                    .setStyle(new Notification.BigTextStyle().bigText(body))
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);
            final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(5);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    String channelID = "orderChannel";
                    NotificationChannel channel = new NotificationChannel(channelID, body,
                            NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setLightColor(Color.GREEN);
                    notificationManager.createNotificationChannel(channel);
                    builder.setChannelId(channelID);
                } catch (Exception e) {
                }
            }
            Notification notification = builder.build();
            notification.defaults = Notification.FLAG_ONLY_ALERT_ONCE;
            notification.defaults = Notification.FLAG_INSISTENT | Notification.FLAG_AUTO_CANCEL;
            notification.sound = alarmSound;
            Intent intent = new Intent(this, DetailsEvent.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("event_id", id);
            int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
            notification.contentIntent = PendingIntent.getActivity(getApplicationContext(), iUniqueId, intent, PendingIntent.FLAG_ONE_SHOT);
            notificationManager.notify(5, notification);
        } catch (JSONException e) {
            Log.e("error", e.getMessage());
        }
    }

}
