package graduation.dsctimetable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import graduation.dsctimetable.list.BaseListItem;

/**
 * Created by leejs on 2016-10-07.
 */
public class TableConfigActivity extends AppCompatActivity {

    SharedPreferences login_setting;
    SharedPreferences.Editor editor;

    private String gstu_no;

//    ArrayList<String> listItems = new ArrayList<String>();
//    ArrayAdapter<ArrayList<String>> adapter;

    UserDBManager db = new UserDBManager(TableConfigActivity.this);

    //json parsing
    PhpGet task;
    ArrayList<BaseListItem> bListitem = new ArrayList<BaseListItem>();
    private static String result = new String();

    Spinner spdept;
    Spinner spyear;
    Spinner spgroup;

    String dept="";
    String year="";
    String group="";

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tableconfig_layout);

        login_setting = getSharedPreferences("login_home_layout", 0);
        editor = login_setting.edit();

        spdept = (Spinner)findViewById(R.id.spdept);
        spyear = (Spinner)findViewById(R.id.spyear);
        spgroup = (Spinner)findViewById(R.id.spgroup);

//        Intent it = getIntent();
//        gstu_no = it.getStringExtra("stu_no");
        gstu_no = login_setting.getString("stu_no", "nouser");

        if(!db.isBasettExist()) {
            Log.e("tableconifg", "task execute");
            task = new PhpGet(TableConfigActivity.this, "nouser");

            //TODO : wifi check
            task.execute("http://172.30.62.146:80/tdsc/appdata_basett.php"); //올공역 starbuck wifi
//            task.execute("http://172.30.1.26:80/tdsc/appdata_basett.php"); //home wifi
//            task.execute("http://192.168.1.20:80/tdsc/appdata_basett.php"); //졸작전시회장
        }

        loadSpinnerDataDept();

        spdept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept = parent.getItemAtPosition(position).toString();

                loadSpinnerDataYear(dept);

                spyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        year = parent.getItemAtPosition(position).toString();

                        loadSpinnerDataGroup(dept, year);

                        spgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                group = parent.getItemAtPosition(position).toString();

                                Button config_button = (Button) findViewById(R.id.config_button);
                                config_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(TableConfigActivity.this, TimetableActivity.class);
                                        editor.putBoolean("select", false);
                                        editor.putString("dept_name", dept);
                                        editor.putString("year_no", year);
                                        editor.putString("g_name", group);
                                        editor.commit();
//                                        intent.putExtra("which", 1);
//                                        intent.putExtra("dept_name", dept);
//                                        intent.putExtra("year_no", year);
//                                        intent.putExtra("g_name", group);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    protected void loadSpinnerDataDept() {
        Log.e("tableconfig", "loaddept");

        ArrayList<String> list = db.getBasettDeptname();

        if(list.size() > 0) {
            Log.e("tableconfig", "list is not empty");
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(TableConfigActivity.this, android.R.layout.simple_spinner_item, list);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spdept.setAdapter(dataAdapter1);
        } else {
            Log.e("tableconfig", "list is empty");
        }
    }

    private void loadSpinnerDataYear(String dept) {
        Log.e("tableconfig", "loadyear");

//        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(TableConfigActivity.this, android.R.layout.simple_spinner_item, labels[1]);
//        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spyear.setAdapter(dataAdapter2);

        ArrayList<String> list = db.getBasettYearno(dept);

        if(list.size() > 0) {
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(TableConfigActivity.this, android.R.layout.simple_spinner_item, list);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spyear.setAdapter(dataAdapter1);
        } else {
            Log.e("tableconfig", "list is empty");
        }
    }

    private void loadSpinnerDataGroup(String dept, String year) {
        Log.e("tableconfig", "loadgroup");

//        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(TableConfigActivity.this, android.R.layout.simple_spinner_item, labels[2]);
//        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spgroup.setAdapter(dataAdapter3);

        ArrayList<String> list = db.getBasettGname(dept, year);

        if(list.size() > 0) {
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(TableConfigActivity.this, android.R.layout.simple_spinner_item, list);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spgroup.setAdapter(dataAdapter1);
        } else {
            Log.e("tableconfig", "list is empty");
        }
    }

}
