package graduation.dsctimetable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;

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

import graduation.dsctimetable.list.AnnoListItem;
import graduation.dsctimetable.list.BaseListItem;
import graduation.dsctimetable.list.TimeListItem;
import graduation.dsctimetable.list.UserListItem;

/**
 * Created by leejs on 2016-10-21.
 */
public class PhpGet extends AsyncTask<String, Void, String[]> {

    ArrayList<UserListItem> uListitem = new ArrayList<UserListItem>();
    ArrayList<TimeListItem> tListitem = new ArrayList<TimeListItem>();
    ArrayList<BaseListItem> bListitem = new ArrayList<BaseListItem>();
    ArrayList<AnnoListItem> aListitem = new ArrayList<AnnoListItem>();

    SharedPreferences login_setting;
    SharedPreferences.Editor editor;

    private String[] result;

    private Context gContext;
    Activity activity;
    private String id;
    private String pw;

    //MainActivity Constructor
    public PhpGet(Context gContext, String id, String pw) {
        this.gContext=gContext;
        activity = (Activity)gContext;
        this.id=id;
        this.pw=pw;

        login_setting = gContext.getSharedPreferences("login_home_layout", 0);
        editor = login_setting.edit();
    }

    //TableConfigActivity Constructor
    public PhpGet(Context gContext, String id) {
        this.gContext=gContext;
        activity = (Activity)gContext;
        this.id=id;
//        this.pw=pw;

        login_setting = gContext.getSharedPreferences("login_home_layout", 0);
        editor = login_setting.edit();
    }

    //AnnoActivity Constructor
    public PhpGet() {

    }

    @Override
    protected void onCancelled() {
        Log.e("async", "cancelled");
//            onPost();
        super.onCancelled();
//            onPostExecute(result);
    }

    @Override
    public synchronized String[] doInBackground(String... urls) {
        Log.e("async", "start");

        int count = urls.length;
        result = new String[count];

        StringBuilder jsonHtml = new StringBuilder();

        for (int i = 0; i < count; ++i) {
            try {
                //set connected url
                URL url = new URL(urls[i]);
                //create connection object
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //when connected
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    //returned connected code
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));
                        for (; ; ) {
                            //restore text in web by line
//                                Log.e("json", "read line");
                            String line = br.readLine();
                            if (line == null) break;
                            //append restored text line in jsonHtml
//                                Log.e("json", "before append");
                            jsonHtml.append(line + "\n");

                        }
                        result[i] = jsonHtml.toString().trim();
                        jsonHtml.delete(0, jsonHtml.length());
                        Log.e("json", "after set result");
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Log.e("json", "return");
        return result;
    }

    //jsonHtml이 onPostExecute의 매개변수 str로 들어간다.
    protected void onPostExecute(String[] str) {
        Log.e("json", "start postEx");
        if(gContext instanceof MainActivity) {
            String stu_no;
            String stu_name;
            String ident;
            String dept_name;
            String year_no;
            String g_name;

            try {
                JSONObject root = new JSONObject(result[0]);
                Log.e("json", "created json object");
                JSONArray stu_arr = root.getJSONArray("stu_rs");
                Log.e("json", "got json array");
                for (int i = 0; i < stu_arr.length(); i++) {
                    JSONObject stu = stu_arr.getJSONObject(i);
                    stu_no = stu.getString("stu_no");
                    stu_name = stu.getString("stu_name");
                    ident = stu.getString("ident_back");
                    dept_name = stu.getString("dept_name");
                    year_no = stu.getString("year_no");
                    g_name = stu.getString("group_name");
                    uListitem.add(new UserListItem(stu_no, stu_name, ident, dept_name, year_no, g_name));
//                    Log.e("json", "A listitem added");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //check id and password
            int size = uListitem.size();

            for (int i = 0; i < size; ++i) {
                if (uListitem.get(i).getData(0).equals(id) && uListitem.get(i).getData(2).equals(pw)) {
                    Log.e("check", "idpw is correct");
                    //prepared join
                    //move join_layout(JoinActivity) to get nickname etc
                    UserDBManager db = new UserDBManager(gContext);

                    if (db.isStu_noExist(id) && db.isStu_noExistInTtable(id)) {
                        //패스워드가 맞고 아이디가 앱 db에 존재한다면, 바로 로그인
                        editor.putString("stu_no", id);
                        editor.putBoolean("auto_login", true);
                        editor.putBoolean("select", true);
                        editor.putString("dept_name", uListitem.get(i).getData(3).toString());
                        editor.putString("year_no", uListitem.get(i).getData(4).toString());
                        editor.putString("g_name", uListitem.get(i).getData(5).toString());
                        editor.commit();

                        Intent intent = new Intent(gContext, TimetableActivity.class);
                        intent.putExtra("which", 0);
                        gContext.startActivity(intent);
                        //JoinActivity로 가서 뒤로가기를 누르면 로그인 화면으로 돌아오지 않도록 finish()해준다.
                        activity.finish();
                    } else {
                        //패스워드가 맞고 아이디가 존재하지 않는다면, JoinActivity로Ttable, Sgroup table을 만든 후
                        //Join_layout의 TextView에 표시할 name 정보를 가지고 JoinActivity로 이동
//                        TtableDBManager table_db = new TtableDBManager(MainActivity.this);

                        Log.e("join_json", "insert basett");
                        if(!db.isStu_noExistInTtable(id)) {
                            String sub_no;
                            String sub_name;
                            String prof_name;
                            String c_name;
                            int day;
                            String part;
                            String grade;

                            try {
//                            JSONObject root = new JSONObject(str[1]);
                                JSONObject root = new JSONObject(result[1]);
                                Log.e("timedb", "created json object");
                                JSONArray time_arr = root.getJSONArray("time_rs");
                                Log.e("timedb", "got json array");
                                for (int j = 0; j < time_arr.length(); j++) {
                                    JSONObject time = time_arr.getJSONObject(j);
                                    sub_no = time.getString("sub_no");
                                    stu_no = time.getString("stu_no");
                                    sub_name = time.getString("sub_name");
                                    prof_name = time.getString("prof_name");
                                    c_name = time.getString("c_name");
                                    day = time.getInt("day");
                                    part = time.getString("part");
                                    grade = time.getString("grade");
                                    tListitem.add(new TimeListItem(sub_no, stu_no, sub_name, prof_name, c_name, day, part, grade, 0, ""));
                                    Log.e("timedb", "A listitem added");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            size = tListitem.size();

                            for (int k = 0; k < size; ++k) {
                                Log.e("check", "A insertion nextmove");
                                if (tListitem.get(k).getData(1).equals(id)) {
                                    db.insert(tListitem.get(k).getData(0).toString(), tListitem.get(k).getData(1).toString(),
                                            tListitem.get(k).getData(2).toString(), tListitem.get(k).getData(3).toString(),
                                            tListitem.get(k).getData(4).toString(), Integer.parseInt(tListitem.get(k).getData(5).toString()),
                                            tListitem.get(k).getData(6).toString(), tListitem.get(k).getData(7).toString(),
                                            Integer.parseInt(tListitem.get(k).getData(8)), tListitem.get(k).getData(9));
                                    Log.e("check", "A insertion is completed successfully in the TtableDB");
                                    db.close();
                                }
                            }
                        }

                        Intent intent = new Intent(gContext, JoinActivity.class);
                        intent.putExtra("stu_no", id);
                        intent.putExtra("stu_name", uListitem.get(i).getData(1).toString());
                        intent.putExtra("dept_name", uListitem.get(i).getData(3).toString());
                        intent.putExtra("year_no", uListitem.get(i).getData(4).toString());
                        intent.putExtra("g_name", uListitem.get(i).getData(5).toString());
                        gContext.startActivity(intent);
                        activity.finish();
                    }
                    break;
                } else if (i == size - 1) {
                    Log.e("check", "start idpw checking");
                    //!= 기호 쓰면 실행안됨.
                    editor.clear();
                    editor.commit();
                    final AlertDialog builder = new AlertDialog.Builder(gContext)
                            .setMessage("아이디와 패스워드를 확인해주십시오.\n\n" +
                                    "동서울대 인트라넷 아이디(학번)과 " +
                                    "비밀번호(주민번호 뒷자리)로 로그인 할 수 있습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EditText i_id = (EditText) activity.findViewById(R.id.inputid);
                                    EditText i_pw = (EditText) activity.findViewById(R.id.inputpw);

                                    i_id.setText("");
                                    i_pw.setText("");

                                    dialog.cancel();
                                }
                            }).show();
                }
            }
        } else if(gContext instanceof TableConfigActivity) {
            UserDBManager db = new UserDBManager(gContext);

            String dept_no;
            String year_no;
            String dept_name;
            String g_name;
            String sub_name;
            String prof_name;
            String c_name;
            int day;
            String part;

            Log.e("tableconifg", "before insert");

            try {
                JSONObject root = new JSONObject(result[0]);
                Log.e("basedb", "created json object");
                JSONArray base_arr = root.getJSONArray("base_rs");
                Log.e("basedb", "got json array");
                for (int j = 0; j < base_arr.length(); j++) {
                    JSONObject base = base_arr.getJSONObject(j);
                    dept_no = base.getString("dept_no");
                    year_no = base.getString("year_no");
                    dept_name = base.getString("dept_name");
                    g_name = base.getString("g_name");
                    sub_name = base.getString("sub_name");
                    prof_name = base.getString("prof_name");
                    c_name = base.getString("c_name");
                    day = base.getInt("day");
                    part = base.getString("part");
                    bListitem.add(new BaseListItem(dept_no, year_no, dept_name, g_name, sub_name, prof_name, c_name, day, part, null));
//                    listitem.add(new ListItem(stu_no, stu_name, ident));
                    Log.e("basedb", "A listitem added");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int size = bListitem.size();

            for (int k = 0; k < size; ++k) {
                Log.e("check", "A insertion nextmove");
                db.insert(bListitem.get(k).getData(0).toString(), bListitem.get(k).getData(1).toString(),
                        bListitem.get(k).getData(2).toString(), bListitem.get(k).getData(3).toString(),
                        bListitem.get(k).getData(4).toString(), bListitem.get(k).getData(5).toString(),
                        bListitem.get(k).getData(6).toString(),
                        Integer.parseInt(bListitem.get(k).getData(7).toString()), bListitem.get(k).getData(8).toString());
                db.close();
            }

            ((TableConfigActivity)gContext).loadSpinnerDataDept();
        } else if(gContext instanceof AnnoActivity) {
            UserDBManager db = new UserDBManager(gContext);

            int anno_index;
            String dept_name;
            String year_no;
            String g_name;
            String stu_no;
            String anno_info;
            String anno_date;

            try {
                JSONObject root = new JSONObject(result[0]);
                Log.e("annodb", "created json object");
                JSONArray anno_arr = root.getJSONArray("anno_rs");
                Log.e("annodb", "got json array");
                for (int j = 0; j < anno_arr.length(); j++) {
                    JSONObject anno = anno_arr.getJSONObject(j);
                    anno_index = anno.getInt("anno_index");
                    if(!db.isExistIndexInAnno(anno_index)) {
                        dept_name = anno.getString("dept_name");
                        year_no = anno.getString("year_no");
                        g_name = anno.getString("g_name");
                        stu_no = anno.getString("stu_no");
                        anno_info = anno.getString("anno_info");
                        anno_date = anno.getString("anno_date");
                        aListitem.add(new AnnoListItem(anno_index, dept_name, year_no,  g_name, stu_no, anno_info, anno_date));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int size = aListitem.size();

            for (int k = 0; k < size; ++k) {
                Log.e("check", "A insertion nextmove");
                db.insert(Integer.parseInt(aListitem.get(k).getData(0)),
                        aListitem.get(k).getData(1).toString(),
                        aListitem.get(k).getData(2).toString(), aListitem.get(k).getData(3).toString(),
                        aListitem.get(k).getData(4).toString(), aListitem.get(k).getData(5).toString(),
                        aListitem.get(k).getData(6).toString());
                db.close();
            }

            ((AnnoActivity)gContext).showAnno();
        }
    }
}
