package graduation.dsctimetable;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import graduation.dsctimetable.list.AnnoListItem;

/**
 * Created by leejs on 2016-10-22.
 */
public class AnnoActivity extends AppCompatActivity {

//    PhpInsert taskInsert;
    PhpGet taskGet;

    SharedPreferences login_setting;
    SharedPreferences.Editor editor;

    UserDBManager db = new UserDBManager(AnnoActivity.this);

    String gdept_name;
    String gyear_no;
    String gg_name;
    String gstu_no;

    ArrayList<AnnoListItem> annoInfo = new ArrayList<AnnoListItem>();

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announce_layout);

        login_setting = getSharedPreferences("login_home_layout", 0);
        editor = login_setting.edit();

        Intent it = getIntent();
        gdept_name = it.getStringExtra("dept_name");
        gyear_no = it.getStringExtra("year_no");
        gg_name = it.getStringExtra("g_name");
        gstu_no = login_setting.getString("stu_no", "");

        TextView t = (TextView)findViewById(R.id.grouptext);
        t.setText(gyear_no + "학년 " + gg_name + "반");

        taskGet = new PhpGet();
//        taskGet.execute("http://172.30.1.26:80/dscapp/dscappdata_anno.php"); //home
//        taskGet.execute("http://192.168.1.20/dscapp/dscappdata_anno.php"); //졸작전시회장
        taskGet.execute("http://172.30.62.146:80/dscapp/dscappdata_anno.php"); //올공 스벅


        Button send = (Button)findViewById(R.id.anno_send_button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(AnnoActivity.this);
//                builder.setMessage("공지기능은 준비중입니다.")
//                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//                if(false) {
                    EditText anno_text = (EditText) findViewById(R.id.inputanno);
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat fm = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
//                taskInsert = new PhpInsert(gdept_name, gyear_no, gg_name, gstu_no,
//                        anno_text.getText().toString(), fm.format(date).toString());
//                taskInsert.execute(gdept_name, gyear_no, gg_name, gstu_no,
//                        anno_text.getText().toString(), fm.format(date).toString());
                    try {
//                    PhpRequest request = new PhpRequest(new URL("http://172.30.1.26:80/dscapp/data_insert.php")); //home
//                        PhpRequest request = new PhpRequest(new URL("http://192.168.1.20/dscapp/data_insert.php")); //졸작전시회장
                        PhpRequest request = new PhpRequest(new URL("http://172.30.62.146:80/dscapp/data_insert.php")); //올공 스벅
                        String result = request.Phptest(gdept_name, gyear_no, gg_name, gstu_no,
                                anno_text.getText().toString(), fm.format(date).toString());
                        if (result.equals("1")) {
                            Log.e("insert php", "entered");
                        } else {
                            Log.e("insert php", "not entered");
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    //TODO Wifi check
                    taskGet = new PhpGet();
//                taskGet.execute("http://172.30.1.26:80/dscapp/dscappdata_anno.php");
//                    taskGet.execute("http://192.168.1.206:80/dscapp/dscappdata_anno.php"); //졸작전시회장
                    taskGet.execute("http://172.30.62.146:80/dscapp/dscappdata_anno.php"); //올공스벅

                    anno_text.setText("");
//                }
            }
        });
    }

    protected void showAnno() {
        annoInfo = db.getAnno(gdept_name, gyear_no, gg_name);
        LinearLayout linear = (LinearLayout)findViewById(R.id.linearanno);
        linear.removeAllViews();

        int count = annoInfo.size();
        if(count<=0) {
            TextView t1 = new TextView(AnnoActivity.this);
            t1.setGravity(Gravity.CENTER);
            t1.setText("자신의 반에 알릴 공지를 입력해주십시오.");
            t1.setTextSize(11);
            linear.addView(t1);
        } else {
            for (int i = 0; i < count; ++i) {
                TextView t1 = new TextView(AnnoActivity.this);
                String stu_name = db.getStu_name(annoInfo.get(i).getData(4));
                t1.setText(annoInfo.get(i).getData(5) + "\n" + annoInfo.get(i).getData(6) + " - " + stu_name);

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(1000, ActionBar.LayoutParams.WRAP_CONTENT);
                t1.setLayoutParams(param);
                t1.setTextSize(11);
                t1.setPadding(10, 15, 10, 15);

                linear.addView(t1);

                View space = new View(AnnoActivity.this);
                space.setMinimumHeight(10);
                space.setBackgroundColor(getResources().getColor(R.color.base));
                linear.addView(space);
            }
        }
    }
}
