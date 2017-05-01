package graduation.dsctimetable.list;

/**
 * Created by leejs on 2016-10-07.
 */
public class BaseListItem {
    private String[] mData;

    public BaseListItem(String[] data) {
        mData = data;
    }

    public BaseListItem(String dept_no, String year_no, String dept_name, String g_name, String sub_name,
                        String prof_name, String c_name, int day, String part, String title) {
        mData = new String[10];
        mData[0] = dept_no;
        mData[1] = year_no;
        mData[2] = dept_name;
        mData[3] = g_name;
        mData[4] = sub_name;
        mData[5] = prof_name;
        mData[6] = c_name;
        mData[7] = Integer.toString(day);
        mData[8] = part;
        mData[9] = title;

    }

    public String[] getData() {
        return mData;
    }

    public String getData(int index) {
        return mData[index];
    }

    public void setData(String[] data) {
        mData = data;
    }
}
