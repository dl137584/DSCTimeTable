package graduation.dsctimetable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by leejs on 2016-10-14.
 */
public class SettingActivity extends AppCompatActivity {

    SharedPreferences login_setting;
    SharedPreferences.Editor editor;

    private ListView setting_list;
    private ArrayAdapter<String> list_adapter;

    String gstu_no;

    ArrayList list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        Log.e("start", "settinglayout");

        login_setting = getSharedPreferences("login_home_layout", 0);
        editor = login_setting.edit();

        gstu_no = login_setting.getString("stu_no", "");
        if(gstu_no.equals("nouser")||gstu_no.equals("")) {
            list.add("로그인");
            list.add("DSC 시간표에 대해");
        } else {
            list.add("로그아웃");
            list.add("동기화");
            list.add("DSC 시간표에 대해");
        }

        list_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        setting_list = (ListView) findViewById(R.id.setting_list);
        setting_list.setAdapter(list_adapter);
        setting_list.setOnItemClickListener(onClickListItem);

//        list_adapter.add(list);
    }

    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String item = list_adapter.getItem(position);
            if (item.equals("로그인")) {
                editor.remove("select");
                editor.commit();
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if (item.equals("로그아웃")) {
                editor.remove("stu_no");
                editor.remove("dept_name");
                editor.remove("year_no");
                editor.remove("g_name");
                editor.remove("select");
                editor.commit();

                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if(item.equals("동기화")) {
                //TODO 동기화
//                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
//                builder.setMessage("동기화 기능은 준비중입니다.")
//                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog dialog = builder.create();
//                dialog.show();
            } else if(item.equals("DSC 시간표에 대해")) {
                LinearLayout main = (LinearLayout)findViewById(R.id.main);
                main.removeAllViews();
                TextView about = new TextView(SettingActivity.this);
                about.setText("'DSC 시간표'에 대해");
                main.addView(about);
            }
        }
    };
}
