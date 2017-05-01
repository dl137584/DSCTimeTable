package graduation.dsctimetable;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import graduation.dsctimetable.list.ListItem;


/**
 * Created by leejs on 2016-09-30.
 */
public class JsontestActivity extends AppCompatActivity {
    TextView textview;
//    String imgUrl = "http://172.30.63.190:80/tdsc_image/";
//    Bitmap bm;
    phpDown task;
    ArrayList<ListItem> listitem = new ArrayList<ListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsontest_layout);
        task = new phpDown();
        textview = (TextView)findViewById(R.id.text1);

        //TODO : wifi check
//        task.execute("http://172.30.52.238:80/tdsc/appdata.php"); //서현역 starbuck wifi
        task.execute("http://172.30.1.26:80/tdsc/appdata.php"); //home
//        task.execute("http://172.30.63.115:80/tdsc/appdata.php"); //방이역 starbuck wifi
//        task.execute("http://192.168.0.23:80/tdsc/appdata.php"); //study cafe
    }

    private class phpDown extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                //set connected url
                URL url = new URL(urls[0]);
                //create connection object
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                //when connected
                if(conn!=null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    //returned connected code
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));
                        for(;;) {
                            //restore text in web by line
                            String line = br.readLine();
                            if(line == null) break;
                            //append restored text line in jsonHtml
                            jsonHtml.append(line+"\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return jsonHtml.toString();
        }

        //jsonHtml이 onPostExecute의 매개변수 str로 들어간다.
        protected void onPostExecute(String str) {
            String prof_no;
            String prof_name;
            String password;
            try {
                JSONObject root = new JSONObject(str);
                JSONArray prof_arr = root.getJSONArray("prof_rs");
                Log.e("json", "get json array");
                for (int i = 0; i<prof_arr.length(); i++) {
                    JSONObject prof = prof_arr.getJSONObject(i);
                    prof_no = prof.getString("prof_no");
                    prof_name = prof.getString("prof_name");
                    password = prof.getString("password");
                    listitem.add(new ListItem(prof_no, prof_name, password));
                    Log.e("json", "listitem added");
                }
            } catch(JSONException e) {
                    e.printStackTrace();
            }
            textview.setText(listitem.get(0).getData(1));
//            textview.setText(str);
            Log.e("json", "set text successfully");
        }
    }
}
