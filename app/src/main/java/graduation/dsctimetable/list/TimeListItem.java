package graduation.dsctimetable.list;

/**
 * Created by leejs on 2016-10-03.
 */
public class TimeListItem {
    private String[] mData;

    public TimeListItem(String[] data) {
        mData = data;
    }

    public TimeListItem(String sub_no, String stu_no, String sub_name, String prof_name, String c_name,
                        int day, String part, String grade, int alarm, String title) {
        mData = new String[10];
        mData[0] = sub_no;
        mData[1] = stu_no;
        mData[2] = sub_name;
        mData[3] = prof_name;
        mData[4] = c_name;
        mData[5] = Integer.toString(day);
        mData[6] = part;
        mData[7] = grade;
        mData[8] = Integer.toString(alarm);
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
