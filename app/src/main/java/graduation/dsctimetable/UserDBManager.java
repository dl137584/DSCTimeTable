package graduation.dsctimetable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import graduation.dsctimetable.list.AnnoListItem;
import graduation.dsctimetable.list.BaseListItem;
import graduation.dsctimetable.list.MemoListItem;
import graduation.dsctimetable.list.TimeListItem;
import graduation.dsctimetable.list.UserListItem;

/**
 * Created by leejs on 2016-10-01.
 * http://mainia.tistory.com/670
 */
public class UserDBManager extends SQLiteOpenHelper {

    //timetable4.db는 ttable의 primarykey를 없앤 것
    private static final String DATABASE_NAME = "timetable13.db";
    private static final int DATABASE_VERSION = 1;

    //context : 현재화면, name : database name, factory : cursor factory, version : version number
    public UserDBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (" +
                "stu_no TEXT PRIMARY KEY, " +
                "stu_name TEXT, " +
                "join_date TEXT, " +
                "dept_name TEXT, " +
                "year_no TEXT, " +
                "g_name TEXT);");
        db.execSQL("CREATE TABLE ttable (" +
                "sub_no TEXT NOT NULL, " +
                "stu_no TEXT NOT NULL, " +
                "sub_name TEXT, " +
                "prof_name TEXT, " +
                "c_name TEXT, " +
                "day INTEGER, " +
                "part TEXT, " +
                "grade TEXT, " +
                "alarm INTEGER, " +
                "title TEXT, " +
                "PRIMARY KEY(sub_no, stu_no));");
        db.execSQL("CREATE TABLE basett (" +
                "dept_no TEXT NOT NULL, " +
                "year_no TEXT NOT NULL, " +
                "dept_name TEXT, " +
                "g_name TEXT, " +
                "sub_name TEXT, " +
                "prof_name TEXT, " +
                "c_name TEXT, " +
                "day INTEGER, " +
                "part TEXT, " +
                "title TEXT);");
        db.execSQL("CREATE TABLE tablelist (" +
                "t_index INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "stu_no TEXT);");
//        db.execSQL("CREATE TABLE sgroup (" +
//                "stu_no TEXT PRIMARY KEY, " +
//                "g_no TEXT, " +
//                "g_name TEXT);");
        db.execSQL("CREATE TABLE memo (" +
                "memo_no INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "memo TEXT, " +
                "m_date TEXT, " +
                "stu_no TEXT, " +
                "sub_name TEXT, " +
                "tt_index INTEGER);");
//        db.execSQL("CREATE TABLE alarm (" +
//                "alarm INTEGER, " +
//                "day INTEGER, " +
//                "time INTEGER, " +
//                "stu_name TEXT, " +
//                "sub_name TEXT);");
        db.execSQL("CREATE TABLE announce (" +
                "anno_index INTEGER, " +
                "dept_name TEXT, " +
                "year_no TEXT, " +
                "g_name TEXT, " +
                "stu_no TEXT, " +
                "anno_info TEXT, " +
                "anno_date TEXT, " +
                "PRIMARY KEY(anno_index));");
    }

    //user db
    public void insert(String stu_no, String stu_name, String date, String dept_name, String year_no, String g_name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO user VALUES('" +
                stu_no + "', '" + stu_name + "', '" + date + "', '" + dept_name + "', '" + year_no + "', '" + g_name + "');");
        db.close();
    }
    //ttable db
    public void insert(String sub_no, String stu_no, String sub_name, String prof_name, String c_name, int day,
                       String part, String grade, int alarm, String title) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO ttable VALUES('" +
                sub_no + "', '" + stu_no + "', '" + sub_name + "', '" + prof_name + "', '" +
                c_name + "', " + day + ", '" + part + "', '" + grade + "', " + alarm + ", '" + title + "');");
        db. close();
    }

    public void insert(String dept_no, String year_no, String dept_name, String g_name, String sub_name,
                       String prof_name, String c_name, int day, String part) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO basett VALUES('" +
                dept_no + "', '" + year_no + "', '" + dept_name + "', '" + g_name + "', '" + sub_name + "', '" +
                prof_name + "', '" + c_name + "', " + day + ", '" + part + "', null);");
        db. close();
    }
//
//    public void insert(String title, String stu_no) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("INSERT INTO tablelist VALUES(null, '" +
//                title + "', '" + stu_no + "');");
//        db. close();
//    }

    //sgroup db
//    public void insert(String g_no, String g_name, String stu_no) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("INSERT INTO sgroup VALUES('" +
//                g_no + "', '" + g_name + "', '" + stu_no + "');");
//        db.close();
//    }

    //memo db
    public void insert(String memo, String m_date, String stu_no, String sub_name, int tt_index) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO memo VALUES(null, " +
                "'" + memo + "', '" + m_date + "', '" + stu_no + "', '" + sub_name + "', " + tt_index + ");");
        db.close();
    }
//    public void insert(int alam_no, int time, String stu_no, String sub_name) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("INSERT INTO alam VALUES(null, " + "'" + time + ", '" + stu_no + "', '" + sub_name + "');");
//        db. close();
//    }
    public void insert(int anno_index, String dept_name, String year_no, String g_name, String stu_no, String anno_info, String anno_date) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO announce VALUES(" + anno_index + ", '" + dept_name + "', '" + year_no + "', " +
                "'" + g_name + "', '" + stu_no + "', '" + anno_info + "', '" + anno_date + "');");
        db.close();
    }


//    public void update(String stu_no, String new_name) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("UPDATE user SET stu_name='" + new_name + "' WHERE stu_no = '" + stu_no + "';");
//        db.close();
//    }
    public void updateAlarm(String stu_no, String sub_name, int alarm) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE ttable SET alarm = " + alarm + " WHERE stu_no = '" + stu_no + "'" +
                " and sub_name = '" + sub_name + "';");
        db.close();
    }

    //TODO 제목 바꾸기
    public void updateTTtitle(String stu_no, String title) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE ttable SET title = '" + title + "' WHERE stu_no = '" + stu_no + "';");
        db.close();
    }

    public void updateBTtitle(String dept_no, String year_no, String g_name, String title) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE basett SET title = '" + title + "' WHERE dept_no = '" + dept_no + "'" +
                " and year_no = '" + year_no + "' and g_name = '" + g_name + "';");
        db.close();
    }

//    public void deleteUser() {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("DROP TABLE user;");
//        db.close();
//    }
    public void deleteUser(String stu_no) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM user WHERE stu_no='" + stu_no + "';");
        db.close();
    }
    public void deleteTtable(String stu_no) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM ttable WHERE stu_no='" + stu_no + "';");
        db.close();
    }
//    public void deleteGroup(String stu_no) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("DELETE FROM sgroup WHERE stu_no=" + stu_no + ";");
//        db.close();
//    }
    public void deleteMemo(String stu_no) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM memo WHERE stu_no='" + stu_no + "';");
        db.close();
    }
    public void deleteBasett() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM basett;");
        db.close();
    }

    //return student's (nick)name if inputid exists already in app userdb.
    public String getStu_no(String stu_no) {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        if(stu_no != "") {
            Cursor cursor = db.rawQuery("SELECT * FROM user WHERE stu_no='" + stu_no + "';", null);
            while(cursor.moveToNext()) {
                result += cursor.getString(0) + "\n";
            }
        }
        db.close();
        return result;
    }

    public String getStu_name(String stu_no) {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        if(stu_no != "") {
            Cursor cursor = db.rawQuery("SELECT stu_name FROM user WHERE stu_no='" + stu_no + "';", null);
            while(cursor.moveToNext()) {
                result += cursor.getString(0);
            }
        }
        db.close();
        return result;
    }

//    public String[] getTimetable(String stu_no) {
//        SQLiteDatabase db = getReadableDatabase();
////        String[] result = new String[8]; //result를 불러올 떄 맨앞에 null이 삽입되어버린다.
//        String[] result = {"", "", "", "", "", "", "", ""};
//
//        Cursor cursor = db.rawQuery("SELECT * FROM ttable WHERE stu_no='" + stu_no + "';", null);
//        while(cursor.moveToNext()) {
//            result[0] += cursor.getString(0) + "\n";
//            result[1] += cursor.getString(1) + "\n";
//            result[2] += cursor.getString(2) + "\n";
//            result[3] += cursor.getString(3) + "\n";
//            result[4] += cursor.getString(4) + "\n";
//            result[5] += cursor.getString(5) + "\n";
//            result[6] += cursor.getString(6) + "\n";
//            result[7] += cursor.getString(7) + "\n";
//        }
//        return result;
//    }

    public ArrayList<UserListItem> getUser(String stu_no) {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<UserListItem> tList = new ArrayList<UserListItem>();
        Cursor cursor = db.rawQuery("SELECT stu_no, sub_name, join_date, dept_name, year_no, g_name " +
                "FROM user WHERE stu_no='" + stu_no + "';", null);
        while(cursor.moveToNext()) {
            tList.add(new UserListItem(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5)));
        }
        db.close();
        return tList;
    }

    public ArrayList<TimeListItem> getTimetable(String stu_no) {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<TimeListItem> tList = new ArrayList<TimeListItem>();
        Cursor cursor = db.rawQuery("SELECT sub_no, stu_no, sub_name, prof_name, c_name, day, part, grade, alarm, title" +
                " FROM ttable WHERE stu_no='" + stu_no + "';", null);
        while(cursor.moveToNext()) {
            tList.add(new TimeListItem(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), Integer.parseInt(cursor.getString(5)),
                    cursor.getString(6), cursor.getString(7), Integer.parseInt(cursor.getString(8)), cursor.getString(9)));
        }
        db.close();
        return tList;
    }

    public int[] getSubDay(String stu_no, String sub_name) {
        SQLiteDatabase db = getReadableDatabase();

        int[] days = new int[3];
        Cursor cursor = db.rawQuery("SELECT day, part, alarm FROM ttable WHERE stu_no='" + stu_no
                + "' and sub_name='" + sub_name+"';", null);
        while(cursor.moveToNext()) {
            StringTokenizer st = new StringTokenizer(cursor.getString(1), "-");
            days[0] = cursor.getInt(0);
            days[1] = Integer.parseInt(st.nextToken().toString());
            days[2] = cursor.getInt(2);
        }
        db.close();
        return days;
    }

    public String getCname(String stu_no, String sub_name) {
        SQLiteDatabase db = getReadableDatabase();

        String c_name="";
        Cursor cursor = db.rawQuery("SELECT c_name FROM ttable WHERE stu_no='" + stu_no
                + "' and sub_name='" + sub_name+"';", null);
        while(cursor.moveToNext()) {
            c_name = cursor.getString(0);
        }
        db.close();
        return c_name;
    }

    public ArrayList<BaseListItem> getBaseTimetable(String dept_name, String year_no, String g_name) {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<BaseListItem> bList = new ArrayList<BaseListItem>();
        Cursor cursor = db.rawQuery("SELECT dept_no, year_no, dept_name, g_name, sub_name, prof_name," +
                "c_name, day, part, title FROM basett WHERE dept_name='" + dept_name
                + "' and year_no = '" + year_no + "' and g_name = '" + g_name + "';", null);
        while(cursor.moveToNext()) {
            bList.add(new BaseListItem(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), Integer.parseInt(cursor.getString(7)), cursor.getString(8),
                    cursor.getString(9)));
        }
        db.close();
        return bList;
    }

    public ArrayList<BaseListItem> getBaseTimetableAll() {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<BaseListItem> bList = new ArrayList<BaseListItem>();
        Cursor cursor = db.rawQuery("SELECT dept_no, year_no, dept_name, g_name, sub_name, prof_name, " +
            "c_name, day, part, title FROM basett;", null);
        while(cursor.moveToNext()) {
            bList.add(new BaseListItem(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), Integer.parseInt(cursor.getString(7)), cursor.getString(8), cursor.getString(9)));
        }
        db.close();
        return bList;
    }

    public ArrayList<MemoListItem> getMemo(String stu_no, String sub_name, int index) {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<MemoListItem> mList = new ArrayList<MemoListItem>();
        Cursor cursor = db.rawQuery("SELECT memo_no, memo, m_date FROM memo " +
                "WHERE stu_no = '" + stu_no + "' and sub_name = '" + sub_name + "' and tt_index = "+ index + ";", null);
        while(cursor.moveToNext()) {
            mList.add(new MemoListItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                    cursor.getString(2)));
        }
        db.close();
        return mList;
    }

    public ArrayList<AnnoListItem> getAnno(String dept_name, String year_no, String g_name) {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<AnnoListItem> anno = new ArrayList<AnnoListItem>();
        Cursor cursor = db.rawQuery("SELECT * FROM announce " +
                "WHERE dept_name = '" + dept_name + "' and year_no = '" + year_no + "' and g_name = '" + g_name + "';", null);
        while(cursor.moveToNext()) {
            anno.add(new AnnoListItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2)
                    , cursor.getString(3)
                    , cursor.getString(4), cursor.getString(5), cursor.getString(6)));
        }
        db.close();
        return anno;
    }

//    public void insertLabel(String label) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values  = new ContentValues();
//        values.put("dept_name", label);
//
//        db.insert("basett", null, values);
//        db.close();
//    }

//    public ArrayList<String>[] getAllLabels() {
//        SQLiteDatabase db = getReadableDatabase();
//        ArrayList<String>[] labels = new ArrayList[3];
//
//        Cursor cursor = db.rawQuery("SELECT * FROM basett;", null);
//        if(cursor.moveToFirst()) {
//            do {
//                labels[0].add(cursor.getString(2)); //dept_name
//                labels[1].add(cursor.getString(1)); //year_no
//                labels[2].add(cursor.getString(3)); //g_name
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//
//        return labels;
//    }

    public ArrayList<String> getBasettDeptname() {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<String> dept_list = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT DISTINCT dept_name FROM basett;", null);
        while(cursor.moveToNext()) {
            dept_list.add(cursor.getString(0));
        }
        db.close();
        return dept_list;
    }

    public ArrayList getBasettYearno(String dept_name) {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList bList = new ArrayList();
        Cursor cursor = db.rawQuery("SELECT DISTINCT year_no FROM basett WHERE dept_name = '" + dept_name + "';", null);
        while(cursor.moveToNext()) {
            bList.add(cursor.getString(0));
        }
        db.close();
        return bList;
    }

    public ArrayList getBasettGname(String dept_name, String year_no) {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList bList = new ArrayList();
        Cursor cursor = db.rawQuery("SELECT DISTINCT g_name FROM basett WHERE dept_name = '" + dept_name + "' and year_no = '" + year_no + "';", null);
        while(cursor.moveToNext()) {
            bList.add(cursor.getString(0));
        }
        db.close();
        return bList;
    }

    public int[] countStartEnd(String stu_no) {
        int[] se = {10, 1};
        int[] temp = new int[2];
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT part FROM ttable WHERE stu_no = '"+ stu_no + "';", null);
        while(cursor.moveToNext()) {
            StringTokenizer st = new StringTokenizer(cursor.getString(0), "-");
            if(se[0] > (temp[0] = Integer.parseInt(st.nextToken()))) { se[0] = temp[0]; }
            if(se[1] < (temp[1] = Integer.parseInt(st.nextToken()))) { se[1] = temp[1]; }
        }
        db.close();
        return se;
    }

    public int[] countStartEndInBasett(String dept_name, String year_no, String g_name) {
        int[] se = {10, 1};
        int[] temp = new int[2];
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT part FROM basett WHERE dept_name = '"+ dept_name + "' and " +
                "year_no = '" + year_no + "' and g_name = '" + g_name + "';", null);
        while(cursor.moveToNext()) {
            StringTokenizer st = new StringTokenizer(cursor.getString(0), "-");
            if(se[0] > (temp[0] = Integer.parseInt(st.nextToken()))) { se[0] = temp[0]; }
            if(se[1] < (temp[1] = Integer.parseInt(st.nextToken()))) { se[1] = temp[1]; }
        }
        db.close();
        return se;
    }

    //if id already exist
    public boolean isStu_noExist(String stu_no) { //TODO : stu_no를 int로 해야하는지
        SQLiteDatabase db = getReadableDatabase();
        String Query = "SELECT * FROM user WHERE stu_no='" + stu_no + "';";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0) {
            Log.e("exist","is not stu_no exist");
            cursor.close();
            return false;
        }
        Log.e("exist","is stu_no exist");
        cursor.close();
        db.close();
        return true;
    }

    //if nickname already exist
    public boolean isStu_nameExist(String stu_name) {
        SQLiteDatabase db = getReadableDatabase();
        String Query = "SELECT * FROM user WHERE stu_name='" + stu_name + "';";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    public boolean isStu_noExistInTtable(String stu_no) {
        SQLiteDatabase db = getReadableDatabase();
        String Query = "SELECT * FROM ttable WHERE stu_no='" + stu_no + "';";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0) {
            Log.e("not exist","is not stu_no exist in ttable");
            cursor.close();
            return false;
        }
        Log.e("exist","is stu_no exist in ttable");
        cursor.close();
        db.close();
        return true;
    }

    public boolean isBasettExist() { //TODO : stu_no를 int로 해야하는지
        SQLiteDatabase db = getReadableDatabase();
        String Query = "SELECT dept_name FROM basett;";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0) {
            Log.e("exist","is not stu_no exist in ttable");
            cursor.close();
            return false;
        }
        Log.e("exist","is stu_no exist in ttable");
        cursor.close();
        db.close();
        return true;
    }

    public boolean isExistIndexInAnno(int index) {
        SQLiteDatabase db = getReadableDatabase();

        String Query = "SELECT index FROM announce WHERE anno_index = " + index + ";";
        Cursor cursor = db.rawQuery(Query, null);

        if(cursor.getCount() <= 0) {
            Log.e("exist","is not anno index exist in announce");
            cursor.close();
            return false;
        }

        Log.e("exist","is anno index exist in announce");
        cursor.close();
        db.close();
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
