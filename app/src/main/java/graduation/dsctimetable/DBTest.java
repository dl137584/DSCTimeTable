package graduation.dsctimetable;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import graduation.dsctimetable.list.TimeListItem;
import graduation.dsctimetable.list.UserListItem;


/**
 * Created by leejs on 2016-10-03.
 */
public class DBTest extends AppCompatActivity {

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsontest_layout);

        /*
        android.content.ActivityNotFoundException: Unable to find explicit activity class
        {graduation.dsctimetable/graduation.dsctimetable.DBTest}; have you declared this activity in your AndroidManifest.xml?
        */
        UserDBManager helper = new UserDBManager(getApplicationContext());
//        SQLiteDatabase db = helper.getWritableDatabase();
//        MainActivity m = new MainActivity();

//        Log.e("dbtest", "open db");
//        db.execSQL("INSERT INTO user VALUES('1406003', 'hu', '20161001', 'B', '컴퓨터 소프트웨어과');");

//        helper.update("1406003", "hu3_1");
//        helper.insert("1406002", "hu2", "20161001", "B", "컴퓨터 소프트웨어과");
        helper.deleteUser("1406001");
        helper.deleteTtable("1406001");
        helper.deleteMemo("1406001");
        helper.deleteUser("1406002");
        helper.deleteTtable("1406002");
        helper.deleteMemo("1406002");
        helper.deleteBasett();
//        helper.deleteUser("1406002");
//        helper.deleteUser("1406003");
//        helper.deleteUser("1406004");
        Log.e("test", "deleted from user_db");



//        t1.setText("1 --- " + helper.getStu_name("1406001") + "2 --- " + helper.getStu_name("1406002") +
//                "3 --- " + helper.getStu_no("1406001") + "4 --- " + helper.getStu_no("1406002"));
//        t.setText("1406001's nickname is " + helper.getStu_name("1406001"));

//        t1.setText("0 --- " + helper.getTimetable("1406002")[0] + "\n" +
//                        "1 --- " + helper.getTimetable("1406002")[1] + "\n" +
//                        "2 --- " + helper.getTimetable("1406002")[2] + "\n" +
//                        "3 --- " + helper.getTimetable("1406002")[3] + "\n" +
//                        "4 --- " + helper.getTimetable("1406002")[4] + "\n" +
//                        "5 --- " + helper.getTimetable("1406002")[5] + "\n" +
//                        "6 --- " + helper.getTimetable("1406002")[6] + "\n" +
//                        "7 --- " + helper.getTimetable("1406002")[7] + "\n"
//        );
//        t2.setText("0 --- " + helper.getTimetable("1406001")[0] + "\n" +
//                "1 --- " + helper.getTimetable("1406001")[1] + "\n" +
//                "2 --- " + helper.getTimetable("1406001")[2] + "\n" +
//                "3 --- " + helper.getTimetable("1406001")[3] + "\n" +
//                "4 --- " + helper.getTimetable("1406001")[4] + "\n" +
//                "5 --- " + helper.getTimetable("1406001")[5] + "\n" +
//                "6 --- " + helper.getTimetable("1406001")[6] + "\n" +
//                "7 --- " + helper.getTimetable("1406001")[7] + "\n"
//        );
//        t2.setText("full --- "+ helper.getResultTimetable("1406001").toString());

        ArrayList<TimeListItem> tList = new ArrayList<TimeListItem>();
        if(helper.isStu_noExistInTtable("1406001")) {
            LinearLayout test = (LinearLayout)findViewById(R.id.dbtest);
            tList = helper.getTimetable("1406001");
            Log.e("dbtest", "1406001 ttable sb");
            for(int i=0;i<tList.size();++i) {
                TextView tt = new TextView(DBTest.this);
                Log.e("dbtest", "1406001 ttable");
                tList = helper.getTimetable("1406001");
                tt.setText("0 --- " + tList.get(i).getData(0) + "\n" +
                                "1 --- " + tList.get(i).getData(1) + "\n" +
                                "2 --- " + tList.get(i).getData(2) + "\n" +
                                "3 --- " + tList.get(i).getData(3) + "\n" +
                                "4 --- " + tList.get(i).getData(4) + "\n" +
                                "5 --- " + tList.get(i).getData(5) + "\n" +
                                "6 --- " + tList.get(i).getData(6) + "\n" +
                                "7 --- " + tList.get(i).getData(7) + "\n"
                );
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                param.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                tt.setLayoutParams(param);
                tt.setTextSize(10);
                tt.setGravity(Gravity.CENTER);
                tt.setBackgroundColor(getResources().getColor(R.color.subject));
                test.addView(tt);

            }

        }

        TextView t1 = (TextView)findViewById(R.id.text1);
        TextView t2 = (TextView)findViewById(R.id.text2);

        ArrayList<UserListItem> uList = new ArrayList<UserListItem>();

        if(helper.isStu_noExist("1406001")) {
            Log.e("dbtest", "1406001 user");
            uList = helper.getUser("1406001");
            t1.setText("0 --- " + String.valueOf(uList.get(0).getData(0)) + "\n" +
                            "1 --- " + String.valueOf(uList.get(0).getData(1)) + "\n" +
                            "2 --- " + String.valueOf(uList.get(0).getData(2)) + "\n" +
                            "3 --- " + String.valueOf(uList.get(0).getData(3)) + "\n" +
                            "4 --- " + String.valueOf(uList.get(0).getData(4)) + "\n"
            );
        }

        if(helper.isStu_noExist("1406002")) {
            Log.e("dbtest", "1406002");
            tList = helper.getTimetable("1406002");
            t2.setText("0 --- " + tList.get(0).getData(0) + "\n" +
                            "1 --- " + tList.get(0).getData(1) + "\n" +
                            "2 --- " + tList.get(0).getData(2) + "\n" +
                            "3 --- " + tList.get(0).getData(3) + "\n" +
                            "4 --- " + tList.get(0).getData(4) + "\n" +
                            "5 --- " + tList.get(0).getData(5) + "\n" +
                            "6 --- " + tList.get(0).getData(6) + "\n" +
                            "7 --- " + tList.get(0).getData(7) + "\n"
            );
        }
    }
}
