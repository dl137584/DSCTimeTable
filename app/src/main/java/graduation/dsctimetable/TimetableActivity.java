package graduation.dsctimetable;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
//import android.support.v7.app.AlertDialog;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import graduation.dsctimetable.list.BaseListItem;
import graduation.dsctimetable.list.TimeListItem;

/**
 * Created by leejs on 2016-09-29.
 */
public class TimetableActivity extends AppCompatActivity  {

    SharedPreferences login_setting;
    SharedPreferences.Editor editor;

    UserDBManager db = new UserDBManager(TimetableActivity.this);

    private String gstu_no;
    private String gdept_name;
    private String gyear_no;
    private String gg_name;
    private Boolean gselect;
//    int which;
    ArrayList<TimeListItem> tList = new ArrayList<TimeListItem>();
    ArrayList<BaseListItem> bList = new ArrayList<BaseListItem>();

//    ArrayList items = new ArrayList();
    final int index = 0;
    String[] items = {"기본 시간표", "다른 시간표 보기"};
//    int item = 0;

    String[] alarm_items = {"알람취소", "3분전", "5분전", "10분전"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_layout);
        Log.e("start", "timetablelayout");

        login_setting = getSharedPreferences("login_home_layout", 0);
        editor = login_setting.edit();

//        Intent it = getIntent();
//        gstu_no = it.getStringExtra("stu_no");
//        which = it.getIntExtra("which", -1); //로그인했을 때는 0이 넘어오고, 비회원일때는 1이 넘어온다.

        gstu_no = login_setting.getString("stu_no", "").trim();
        gselect = login_setting.getBoolean("select", false);

        gdept_name = login_setting.getString("dept_name", "");
        gyear_no = login_setting.getString("year_no", "");
        gg_name = login_setting.getString("g_name", "");

        if(!gselect) {            bList = db.getBaseTimetable(gdept_name, gyear_no, gg_name);
        }



        //비회원과 회원 구분
        TextView name_textview = (TextView) findViewById(R.id.name);
        if(gstu_no.equals("nouser") || gstu_no.equals("")) {
            name_textview.setText("로그인");
            name_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TimetableActivity.this, MainActivity.class);
                    editor.remove("dept_name");
                    editor.remove("year_no");
                    editor.remove("g_name");
                    editor.remove("stu_no");
                    editor.remove("select");
                    editor.commit();
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            tList = db.getTimetable(gstu_no);
            name_textview.setText(db.getStu_name(gstu_no).toString());
        }

        if(gstu_no.equals("nouser")) { //비회원이라면
            items[0] = "비회원 시간표";
        }

        showTimetable();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //시간표 추가
        TextView semester = (TextView)findViewById(R.id.semester);
        semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("시간표를 선택해주십시오.")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0 :
                                        editor.putBoolean("select", true);
                                        editor.commit();
                                        showTimetable();
                                        break;
                                    case 1 :
                                        editor.remove("select");
                                        editor.commit();
                                        Intent intent = new Intent(TimetableActivity.this, TableConfigActivity.class);
                                        startActivity(intent);
                                        finish();
                                        break;
                                }
//                                showTimetable();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        //setting
        ImageView setting = (ImageView)findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimetableActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        //공지
        if(!(gstu_no.equals("") || gstu_no.equals("nouser"))) {
            ImageView announce = (ImageView) findViewById(R.id.announce);
            announce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(TimetableActivity.this);
//                    builder.setMessage("공지기능은 준비중입니다.")
//                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();ㅂ
//                    if(false) {
                        Intent intent = new Intent(TimetableActivity.this, AnnoActivity.class);
                        intent.putExtra("stu_no", gstu_no);
                        intent.putExtra("dept_name", gdept_name);
                        intent.putExtra("year_no", gyear_no);
                        intent.putExtra("g_name", gg_name);
                        startActivity(intent);
//                    }
                }
            });
        } else {
            ImageView announce = (ImageView) findViewById(R.id.announce);
            announce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TimetableActivity.this);
                    builder.setMessage("여기는 반의 공지를 확인하는 곳입니다.\n로그인 후 이용해 주십시오.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

    }

    public class SubjectOnClickListener implements View.OnClickListener {
        //memo
        @Override
        public void onClick(View v) {
            StringTokenizer st = new StringTokenizer(((TextView)v).getText().toString(), "\n");
            Intent intent = new Intent(TimetableActivity.this, MemoActivity.class);
            intent.putExtra("stu_no", gstu_no);
            intent.putExtra("sub_name", st.nextToken());
//            intent.putExtra("index", which);
            startActivity(intent);
        }
    }

    public class SubjectOnLongClickListener implements View.OnLongClickListener {
        //alam
        @Override
        public boolean onLongClick(View v) {
            StringTokenizer st = new StringTokenizer(((TextView) v).getText().toString(), "\n");
            final String v_sub_name = st.nextToken().toString();
            final int[] day = db.getSubDay(gstu_no, v_sub_name);

            final AlertDialog.Builder builder = new AlertDialog.Builder(TimetableActivity.this);
            builder.setTitle("알람시각을 설정해 주십시오.")
                    .setSingleChoiceItems(alarm_items, day[2], new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.updateAlarm(gstu_no, v_sub_name, which);
                            new AlarmTT(TimetableActivity.this).Alarm(v_sub_name, which);
                        }
                    })
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return false;
        }
    }

    public class AlarmTT {
        private Context context;
        public AlarmTT(Context context) {
            this.context = context;
        }
        public void Alarm(String sub_name, int which) {
            Log.e("alarm", "start");
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            String c_name = db.getCname(gstu_no, sub_name);
            int[] day = db.getSubDay(gstu_no, sub_name);

            Intent intent = new Intent(TimetableActivity.this, AlarmBroadcast.class);
            intent.putExtra("stu_no", gstu_no);
            intent.putExtra("sub_name", sub_name);
            intent.putExtra("c_name", c_name);
//            intent.putExtra("minute", alarm_items[day[2]]);
            intent.putExtra("minute", alarm_items[which]);

            //TODO reauestCode check
            PendingIntent sender = PendingIntent.getBroadcast(TimetableActivity.this, Integer.parseInt(gstu_no) + Integer.parseInt(c_name), intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if(which>0) {
                Calendar calendar = Calendar.getInstance();

                if(false) {
                    calendar.set(Calendar.DAY_OF_WEEK, day[0]);

                    //시간 : 1교시 = 9시
                    calendar.set(Calendar.HOUR_OF_DAY, day[1] + 7);
                    switch (which) {
                        case 1:
                            calendar.set(Calendar.MINUTE, 57);
                            break;
                        case 2:
                            calendar.set(Calendar.MINUTE, 55);
                            break;
                        case 3:
                            calendar.set(Calendar.MINUTE, 50);
                            break;
                    }
                }

                calendar.set(Calendar.SECOND, 1);

                //알람예약
                manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            } else {
                manager.cancel(sender);
            }
        }
    }

    public void showTimetable() {
//if(false) {
        if(gselect && tList.size()>0) {
//            editor.putBoolean("select", true);
//            editor.commit();

            TextView title = (TextView)findViewById(R.id.semester);
            title.setText(items[0]);
            Log.e("set", "title");

            GridLayout grid = (GridLayout)findViewById(R.id.timetable);
            grid.removeAllViews();

            int column = 6;
//            int row = 8; //TODO : getData해서 set
            int[] se = db.countStartEnd(gstu_no);
            int row = se[1] + se[0];
            int i=0, c=0, r=0;
            int day = Integer.parseInt(tList.get(0).getData(5));;
//        int start = -1, end = -1;
            int count = tList.size();

            grid.setColumnCount(column);
            grid.setRowCount(row);

            //gridlayout의 row 개수만큼 cycle
            for(i=0, c=0, r=0; r < row ; c++) {
//            day = Integer.parseInt(tList.get(i).getData(5));;

                if(c == column) {
//                    Log.e("set", "move column");
                    c = -1;
                    r++;
                }
                else if(r > 0) {
//                    Log.e("set", "inside r>0");
                    if (c == 0) {
//                        Log.e("set", "inside c==0");
                        TextView text = new TextView(TimetableActivity.this);
                        switch (r) {
                            case 1: text.setText("9"); break;
                            case 2: text.setText("10"); break;
                            case 3: text.setText("11"); break;
                            case 4: text.setText("12"); break;
                            case 5: text.setText("1"); break;
                            case 6: text.setText("2"); break;
                            case 7: text.setText("3"); break;
                            case 8: text.setText("4"); break;
                            case 9: text.setText("5"); break;
                            case 10: text.setText("6"); break;
                            case 11: text.setText("7"); break;
                        }
//                        Log.e("set", "row number");

                        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
//                        param.height = 200;
                        param.height=1460/(row-1);
                        param.width = 80;
                        param.setGravity(Gravity.CENTER);
                        param.columnSpec = GridLayout.spec(c);
                        param.rowSpec = GridLayout.spec(r);
                        text.setLayoutParams(param);
                        text.setTextSize(10);
                        text.setGravity(Gravity.CENTER);
                        text.setBackgroundColor(getResources().getColor(R.color.base));
                        grid.addView(text);
                    } else if (c == day && i < count) {
//                        Log.e("set", "inside  c==day");
                        int[] part = new int[3];
//                    int j = 0;
                        StringTokenizer st = new StringTokenizer(tList.get(i).getData(6), "-");
//                    TextView t1 = (TextView)findViewById(R.id.debug1); //
//                    t1.setText(st.nextToken().toString()); //1
//                    while(st.hasMoreTokens()) {
//                        part[j] = Integer.parseInt(st.nextToken());
//                    }
//                    TextView t1 = (TextView)findViewById(R.id.debug1);
//                    t1.setText(Integer.toString(part[0]));
//                    TextView t2 = (TextView)findViewById(R.id.debug2);//
//                    t2.setText(Integer.toString(part[1]));
//                    t2.setText(st.nextToken().toString()); //3
//                    start = Integer.parseInt(st.nextToken().toString());
//                    end = Integer.parseInt(st.nextToken().toString());
                        part[0] = Integer.parseInt(st.nextToken().toString());
                        part[1] = Integer.parseInt(st.nextToken().toString());

//                    if (r == part[0]) {
                        TextView b1 = new TextView(TimetableActivity.this);
                        if(tList.get(i).getData(7).toString().trim().length() < 1) {
                            Log.e("grade", "null");
                            b1.setText(tList.get(i).getData(2) + "\n" + tList.get(i).getData(3) + "\n" + tList.get(i).getData(4));
                        } else {
                            Log.e("grade", "not null");
                            b1.setText(tList.get(i).getData(2) + "\n" + tList.get(i).getData(3) + "\n" +
                                    tList.get(i).getData(4)+ "\n" + tList.get(i).getData(7));
                        }

//                        GridLayout.Spec row1 = GridLayout.spec(part[0], part[1] - part[0] + 1);
                        GridLayout.Spec row1 = GridLayout.spec(part[0], part[1] - part[0] + 1,  GridLayout.BASELINE);
                        GridLayout.Spec col1 = GridLayout.spec(day);
                        GridLayout.LayoutParams param = new GridLayout.LayoutParams(row1, col1);
//                        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
//                        param.columnSpec = GridLayout.spec(c);
//                        param.rowSpec = GridLayout.spec(r, part[1] - r + 1);
//                            param.height = 190;
//                            param.width = 190;
                        param.setGravity(Gravity.CENTER);
//                        b1.setHeight(190 * (part[1] - part[0] + 1) + 10 * (part[1] - part[0]));
                        b1.setHeight((1460/(row-1) - 10) * (part[1] - part[0] + 1) + 10 * (part[1] - part[0]));
                        b1.setWidth(190);
                        b1.setLayoutParams(param);
                        b1.setTextSize(12);
                        b1.setGravity(Gravity.CENTER);
                        b1.setBackgroundColor(getResources().getColor(R.color.subject));
//                        b1.setId(gstu_no + which + tList.get(i).getData(2).toString());

                        SubjectOnClickListener listener = new SubjectOnClickListener();
                        b1.setOnClickListener(listener);

                        SubjectOnLongClickListener longlistener = new SubjectOnLongClickListener();
                        b1.setOnLongClickListener(longlistener);

                        grid.addView(b1);
                        i++;
                        if (i < count) day = Integer.parseInt(tList.get(i).getData(5));
//                    }
                    }
//                    Log.e("set", "for statement end");
                } else if(r == 0  && c > 0) {
                    TextView text = new TextView(TimetableActivity.this);

                    switch(c) {
                        case 1: text.setText("월"); break;
                        case 2: text.setText("화"); break;
                        case 3: text.setText("수"); break;
                        case 4: text.setText("목"); break;
                        case 5: text.setText("금"); break;
                    }

                    GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                    param.height = 80;
                    param.width = 200;
//                    param.setGravity(Gravity.CENTER);
                    param.columnSpec = GridLayout.spec(c);
                    param.rowSpec = GridLayout.spec(r);
                    param.setGravity(Gravity.CENTER);
                    text.setLayoutParams(param);
                    text.setTextSize(12);
                    text.setGravity(Gravity.CENTER);
                    grid.addView(text);
//                        Log.e("set", "day");
                }
//                    Log.e("set", "for statement next");
            }
            WidgetProvider widgetProvider = new WidgetProvider();

            Intent intent = getIntent();
            Bundle extra = intent.getExtras();
            int mAppWidgetId=-1;
            if(extra != null) {
                mAppWidgetId = extra.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
            }

//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//
//            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_appwidget);
//            appWidgetManager.updateAppWidget(mAppWidgetId, views);
//
//
//            widgetProvider.onReceive(TimetableActivity.this, intent);
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(TimetableActivity.this);
//            int[] mAppWidgetId = new int[100];
//            int app=0;
//            Intent intent = getIntent();
//            Bundle extra = intent.getExtras();
//            if(extra != null) {
//                mAppWidgetId[app++] = extra.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
//                        AppWidgetManager.INVALID_APPWIDGET_ID);
//            }
//            new WidgetProvider().onUpdate(TimetableActivity.this, appWidgetManager, mAppWidgetId);
//            Intent resultValue = new Intent();
//            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
//            setResult(RESULT_OK, resultValue);
//            finish();
//            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
            Log.e("set", "end");
        } else if(bList.size()>0) { //다른 테이블을 보기위해 Basett이용
            TextView title = (TextView)findViewById(R.id.semester);
            title.setText(gdept_name + " / " + gyear_no + "학년 / " + gg_name + "반");
            Log.e("set2", "title");

            GridLayout grid = (GridLayout)findViewById(R.id.timetable);
            grid.removeAllViews();

            int column = 6;
            int[] se = db.countStartEndInBasett(gdept_name, gyear_no, gg_name);
            int row = se[1] + se[0];
            int i=0, c=0, r=0;
            int day = Integer.parseInt(bList.get(0).getData(7));;
            int count = bList.size();

            grid.setColumnCount(column);
            grid.setRowCount(row);

            //gridlayout의 row 개수만큼 cycle
            for(i=0, c=0, r=0; r < row ; c++) {
//            day = Integer.parseInt(tList.get(i).getData(5));;

                if(c == column) {
//                    Log.e("set2", "move column");
                    c = -1;
                    r++;
                }
                else if(r > 0) {
//                    Log.e("set2", "inside r>0");
                    if (c == 0) {
//                        Log.e("set2", "inside c==0");
                        TextView text = new TextView(TimetableActivity.this);
                        switch (r) {
                            case 1: text.setText("9"); break;
                            case 2: text.setText("10"); break;
                            case 3: text.setText("11"); break;
                            case 4: text.setText("12"); break;
                            case 5: text.setText("1"); break;
                            case 6: text.setText("2"); break;
                            case 7: text.setText("3"); break;
                            case 8: text.setText("4"); break;
                            case 9: text.setText("5"); break;
                            case 10: text.setText("6"); break;
                            case 11: text.setText("7"); break;
                        }
//                        Log.e("set", "row number");

                        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
//                        param.height = 200;
                        param.height=1460/(row-1);
                        param.width = 80;
                        param.setGravity(Gravity.CENTER);
                        param.columnSpec = GridLayout.spec(c);
                        param.rowSpec = GridLayout.spec(r);
                        text.setLayoutParams(param);
                        text.setTextSize(10);
                        text.setGravity(Gravity.CENTER);
                        text.setBackgroundColor(getResources().getColor(R.color.base));
                        grid.addView(text);
                    } else if (c == day && i < count) {
//                        Log.e("set", "inside  c==day");
                        int[] part = new int[3];
                        StringTokenizer st = new StringTokenizer(bList.get(i).getData(8), "-");
                        part[0] = Integer.parseInt(st.nextToken().toString());
                        part[1] = Integer.parseInt(st.nextToken().toString());

                        TextView b1 = new TextView(TimetableActivity.this);
                        b1.setText(bList.get(i).getData(4) + "\n" + bList.get(i).getData(5) + "\n" + bList.get(i).getData(6));

                        GridLayout.Spec row1 = GridLayout.spec(part[0], part[1] - part[0] + 1,  GridLayout.BASELINE);
                        GridLayout.Spec col1 = GridLayout.spec(day);
                        GridLayout.LayoutParams param = new GridLayout.LayoutParams(row1, col1);
                        param.setGravity(Gravity.CENTER);
                        b1.setHeight((1460 / (row - 1) - 10) * (part[1] - part[0] + 1) + 10 * (part[1] - part[0]));
                        b1.setWidth(190);
                        b1.setLayoutParams(param);
                        b1.setTextSize(12);
                        b1.setGravity(Gravity.CENTER);
                        b1.setBackgroundColor(getResources().getColor(R.color.subject));

                        grid.addView(b1);
                        i++;
                        if (i < count) day = Integer.parseInt(bList.get(i).getData(7));
                    }
//                    Log.e("set", "for statement end");
                } else if(r == 0  && c > 0) {
                    TextView text = new TextView(TimetableActivity.this);

                    switch(c) {
                        case 1: text.setText("월"); break;
                        case 2: text.setText("화"); break;
                        case 3: text.setText("수"); break;
                        case 4: text.setText("목"); break;
                        case 5: text.setText("금"); break;
                    }

                    GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                    param.height = 80;
                    param.width = 200;
//                    param.setGravity(Gravity.CENTER);
                    param.columnSpec = GridLayout.spec(c);
                    param.rowSpec = GridLayout.spec(r);
                    param.setGravity(Gravity.CENTER);
                    text.setLayoutParams(param);
                    text.setTextSize(12);
                    text.setGravity(Gravity.CENTER);
                    grid.addView(text);
//                        Log.e("set2", "day");
                }
//                    Log.e("set2", "for statement next");
            }
            Log.e("set2", "end");
            editor.putBoolean("select", true);
            editor.commit();
        }
    }


}

