package graduation.dsctimetable;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by leejs on 2016-10-10.
 */
public class AlarmBroadcast extends BroadcastReceiver {
    //알람시간이 되었을 떄 호출됨

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("alarm", "broadcast start");
        Bundle bundle = intent.getExtras();
        String gstu_no = bundle.getString("stu_no");
        String gsub_name = bundle.getString("sub_name");
        String c_name = bundle.getString("c_name");
        String minute = bundle.getString("minute");

        NotificationManager notificationmanager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, TimetableActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.timetable_1)
                .setTicker("Alarm ringing")
                .setWhen(System.currentTimeMillis())
                .setNumber(1)
                .setContentText("과목 : " + gsub_name + "  강의실 : " + c_name)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationmanager.notify(1, builder.build());
    }
}
