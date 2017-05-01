package graduation.dsctimetable;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import graduation.dsctimetable.list.BaseListItem;
import graduation.dsctimetable.list.MemoListItem;
import graduation.dsctimetable.list.UserListItem;

/**
 * Created by leejs on 2016-10-10.
 */
public class MemoActivity extends AppCompatActivity {

    UserDBManager db = new UserDBManager(MemoActivity.this);

    String gstu_no;
    String gsub_name;
    int gindex;

    ArrayList<MemoListItem> mList = new ArrayList<MemoListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_layout);
        Log.e("start", "memo_layout");

        Intent it = getIntent();
        gstu_no = it.getStringExtra("stu_no");
        gsub_name = it.getStringExtra("sub_name");
        gindex = it.getIntExtra("index", 0);

        TextView t = (TextView)findViewById(R.id.subjecttext);
        t.setText(gsub_name);

        final EditText inputmemo = (EditText)findViewById(R.id.inputmemo);

        showMemo();

        Button send = (Button)findViewById(R.id.send_button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memo;
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat fm = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");

                memo = inputmemo.getText().toString();
                db.insert(memo, fm.format(date).toString(), gstu_no, gsub_name, gindex);
                showMemo();
                inputmemo.setText("");
            }
        });

    }

    private void showMemo() {
        mList = db.getMemo(gstu_no, gsub_name, gindex);
        LinearLayout relative = (LinearLayout)findViewById(R.id.relativememo);
        relative.removeAllViews();

        int count = mList.size();
        if(count<=0) {
            TextView b1 = new TextView(MemoActivity.this);
//            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(1000, ActionBar.LayoutParams.WRAP_CONTENT);
//            param.gravity(Gravity.CENTER);
//            b1.setLayoutParams(param);
            b1.setGravity(Gravity.CENTER);
            b1.setText("해당 과목에 작성할 메모를 입력해주십시오.");
            b1.setTextSize(11);
            relative.addView(b1);
        } else {
            for (int i = 0; i < count; ++i) {
                TextView b1 = new TextView(MemoActivity.this);
                b1.setText(mList.get(i).getData(1) + "\n" + mList.get(i).getData(2));

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(1000, ActionBar.LayoutParams.WRAP_CONTENT);
//            b1.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
//            b1.setWidth(950);
                b1.setLayoutParams(param);
                b1.setTextSize(11);
                b1.setPadding(10, 15, 10, 15);
//            b1.setGravity(Gravity.CENTER);

                relative.addView(b1);

                View space = new View(MemoActivity.this);
                space.setMinimumHeight(10);
                space.setBackgroundColor(getResources().getColor(R.color.base));
                relative.addView(space);
            }
        }
    }
}
