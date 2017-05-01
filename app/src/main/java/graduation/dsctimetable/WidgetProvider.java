package graduation.dsctimetable;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import graduation.dsctimetable.list.TimeListItem;

/**
 * Created by leejs on 2016-10-24.
 */
public class WidgetProvider extends AppWidgetProvider {

    private static final String CLICK_ACTION = "graduation.dsctimetable.WidgetProvider.CLICK";

    SharedPreferences login_setting;
    SharedPreferences.Editor editor;

    ArrayList<TimeListItem> tList = new ArrayList<TimeListItem>();

    private static final int WIDGET_UPDATE_INTERVAL = 5000;
    private static PendingIntent mSender;

    String gstu_no;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_appwidget);
//        appWidgetManager.updateAppWidget(mAppWidgetId, views);

//        String action = intent.getAction();
        // 위젯 업데이트 인텐트를 수신했을 때
//        if(action.equals("android.appwidget.action.APPWIDGET_UPDATE")) {
//            Log.w("widget", "android.appwidget.action.APPWIDGET_UPDATE");
//
//            long firstTime = System.currentTimeMillis() + WIDGET_UPDATE_INTERVAL;
//            mSender = PendingIntent.getBroadcast(context, 0, intent, 0);
//        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int N = appWidgetIds.length;
        for(int i = 0 ; i < N ; i++) {
//        for(int appWidgetId : appWidgetIds) {
            int appWidgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetId);

//            Toast.makeText(context, "onUpdate(): [" + String.valueOf(i) + "] " + String.valueOf(appWidgetId), Toast.LENGTH_SHORT).show();
//            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.timetable_layout);
//            login_setting = context.getSharedPreferences("login_home_layout", 0);
//            editor = login_setting.edit();
//
//            Intent intent = new Intent(context, TimetableActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//            remoteViews.setOnClickPendingIntent(R.id.asdasda, pendingIntent);
//
//            remoteViews.setTextViewText();
        }
    }

    /**
     * 위젯의 형태를 업데이트
     * @param appWidgetId 업데이트할 위젯 아이디
     */
    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {
        Date now = new Date();
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.timetable_layout);
        updateViews.setTextViewText(R.id.name, "[" + String.valueOf(appWidgetId) + "]" + now.toLocaleString());
        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
//
//        UserDBManager db = new UserDBManager(context);
//
//        login_setting = context.getSharedPreferences("login_home_layout", 0);
//        editor = login_setting.edit();
//
//        gstu_no = login_setting.getString("stu_no", "").trim();
//
//        if(!(gstu_no.equals("") && gstu_no.equals("nouser"))) {
//
//        }
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }
}
