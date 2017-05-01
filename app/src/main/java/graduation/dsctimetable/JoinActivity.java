package graduation.dsctimetable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by leejs on 2016-10-01.
 */
//public class JoinActivity extends AppCompatActivity {
public class JoinActivity extends MainActivity {

    //to check nick is duplicated
    private boolean d = false;
    private String s_nick;
    private String gstu_no;
    private String gstu_name;
    private String gdept_name;
    private String gyear_no;
    private String gg_name;

    UserDBManager db = new UserDBManager(JoinActivity.this);

    SharedPreferences login_setting;
    SharedPreferences.Editor editor;

//    ArrayList<TimeListItem> tListitem = new ArrayList<TimeListItem>();
//    ArrayList<GroupListItem> gListitem = new ArrayList<GroupListItem>();

//    final phpGet task = new phpGet();

//    int ch = 0;

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);

        login_setting = getSharedPreferences("login_home_layout", 0);
        editor = login_setting.edit();

//        MainActivity m = new MainActivity();
        TextView t = (TextView)findViewById(R.id.hello_join);
//        t.setText(m.getId());
        Intent it = getIntent();
        gstu_no = it.getStringExtra("stu_no");
        gstu_name = it.getStringExtra("stu_name");
        gdept_name = it.getStringExtra("dept_name");
        gyear_no = it.getStringExtra("year_no");
        gg_name = it.getStringExtra("g_name");
        t.setText(gstu_name+"님,");


        Button dupl_button = (Button)findViewById(R.id.dupl_button);
        Button join_button = (Button)findViewById(R.id.join_button);

        dupl_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText in_nick = (EditText)findViewById(R.id.nick);
                s_nick = in_nick.getText().toString().trim();
                //d가 true면 사용할 수 있는 이름이므로 insert하면서 timetbale로 move
                d = checkDupl(s_nick);

                if(!d) {
                    AlertDialog builder = new AlertDialog.Builder(JoinActivity.this)
                            .setMessage("이미 존재하는 이름입니다.")
                            .setPositiveButton("확인", null).show();
                } else {
                    AlertDialog builder = new AlertDialog.Builder(JoinActivity.this)
                            .setMessage("이 이름을 사용할 수 있습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }
            }
        });

        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if nickname is not duplicated.
                if (s_nick == "") {
                    AlertDialog builder = new AlertDialog.Builder(JoinActivity.this)
                            .setMessage("이름을 입력해 주십시오.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                } else if(d) {
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
                    Log.e("join", "inserting...");

//                    MainActivity m = new MainActivity();
//                    ArrayList<ListItem> list = new ArrayList<ListItem>();
//                    list = (ArrayList)m.uListitem.clone();

                    //have to insert user's info
                    db.insert(gstu_no, s_nick, fm.format(date).toString(), gdept_name, gyear_no, gg_name);
                    Log.e("check", "A insertion is completed successfully in the UserDB");
                    db.close();

//                    task.execute("http://172.30.63.115:80/tdsc/appdata_timetable.php", "http://172.30.63.115:80/tdsc/appdata_group.php");
//                    task.cancel(false);
//                    if(ch == 2) {
                    Log.e("join", "close this page");

                    editor.putString("stu_no", gstu_no);
                    editor.putBoolean("auto_login", true);
                    editor.putBoolean("select", true);
                    editor.putString("dept_name", gdept_name);
                    editor.putString("year_no", gyear_no);
                    editor.putString("g_name", gg_name);
                    editor.commit();

                    //Move TimetableActivity
                    Intent intent = new Intent(JoinActivity.this, TimetableActivity.class);
                    intent.putExtra("stu_no", gstu_no);
//                    intent.putExtra("which", 0);
                    startActivity(intent);
                    d = false;
                    finish();
//                    }
                } else {
                    Log.e("join", "nick is duplicated");
                    AlertDialog builder = new AlertDialog.Builder(JoinActivity.this)
                            .setMessage("중복체크를 해주십시오.")
                            .setPositiveButton("확인", null).show();
                }
            }
        });
    }

    private boolean checkDupl(String nick) {
        //check in app DB
        if(!db.isStu_nameExist(nick)) { //isStu_nameExist()'s false is that data is not exist in app db
            Log.e("join", "checking duplication");
            return true;
        }
        Log.e("join", "this nick name cannot be used.");
        return false;
    }

}
