package graduation.dsctimetable;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
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
        import java.util.ArrayList;

        import graduation.dsctimetable.list.BaseListItem;
        import graduation.dsctimetable.list.TimeListItem;
        import graduation.dsctimetable.list.UserListItem;

public class MainActivity extends AppCompatActivity {

    Button b;
    PhpGet task;

    private String id;
    private String pw;
//    private int index;

    SharedPreferences login_setting;
    SharedPreferences.Editor editor;

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_home_layout);

        login_setting = getSharedPreferences("login_home_layout", 0);
        editor = login_setting.edit();
        if(login_setting.getBoolean("auto_login", false)){
            Intent intent = new Intent(MainActivity.this, TimetableActivity.class);
            intent.putExtra("which", 0);
            startActivity(intent);
            finish();
        }
        Log.e("start", "login_home_layout");

        final Button login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText i_id = (EditText) findViewById(R.id.inputid);
                EditText i_pw = (EditText) findViewById(R.id.inputpw);
                Log.e("start", "onclick");

                id = i_id.getText().toString().trim();
                pw = i_pw.getText().toString().trim();

                //inputid나 inputpw가 text가 null이면
                if (id.getBytes().length <= 0 || pw.getBytes().length <= 0) {
//              if(i_id.getText().toString() == "" || i_pw.getText().toString() == "" || i_pw.getText().toString().compareTo("") == 1) { //안됨
                    Log.e("start", "in if_true status");
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("아이디와 패스워드를 입력해주십시오.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EditText i_id = (EditText) findViewById(R.id.inputid);
                                    EditText i_pw = (EditText) findViewById(R.id.inputpw);

                                    i_id.setText("");
                                    i_pw.setText("");

                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Log.e("start", "completed input status before idcheck");
                    task = new PhpGet(MainActivity.this, id, pw);
                    //getting student's indent_back
                    //TODO : wifi check
//                    task.execute("http://172.30.1.26:80/tdsc/appdata_stu.php",
//                            "http://172.30.1.26:80/tdsc/appdata_timetable.php",
//                            "http://172.30.1.26:80/tdsc/appdata_basett.php"); //home
//        task.execute("http://192.168.1.20:80/tdsc/appdata_stu.php",
//                "http://192.168.1.20:80/tdsc/appdata_timetable.php",
//                "http://192.168.1.20:80/tdsc/appdata_basett.php"); //졸작전시회장
                    task.execute("http://172.30.62.146:80/tdsc/appdata_stu.php",
                            "http://172.30.62.146:80/tdsc/appdata_timetable.php",
                            "http://172.30.62.146:80/tdsc/appdata_basett.php"); //올공역 starbuck wifi
    }
}
});

        //json test button
//        Button test_button = (Button) findViewById(R.id.test_button);
//        test_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(MainActivity.this, JsontestActivity.class);
//                startActivity(intent1);
        
//            }
//        });

        //appdb test
        Button test_button = (Button) findViewById(R.id.test_button);
        test_button.setText("dbtest");
                test_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, DBTest.class);
                startActivity(intent1);
            }
        });

        //비회원으로 들어가기
        TextView nouser_button = (TextView) findViewById(R.id.nouser_button);
        nouser_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("stu_no", "nouser");
                editor.putBoolean("auto_login", false);
                editor.putBoolean("select", false);
                editor.remove("dept_name");
                editor.remove("year_no");
                editor.remove("g_name");
                editor.commit();
                Intent intent1 = new Intent(MainActivity.this, TableConfigActivity.class);
//                intent1.putExtra("stu_no", "");
                startActivity(intent1);
                finish();
            }
        });

    }
}