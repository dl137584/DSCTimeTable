package graduation.dsctimetable.list;

/**
 * Created by leejs on 2016-10-22.
 */
public class AnnoListItem {
    private String[] mData;

    public AnnoListItem(String[] data) {
        mData = data;
    }

    public AnnoListItem(int index, String dept_name, String year_no, String g_name, String stu_no, String anno_info, String date) {
        mData = new String[7];
        mData[0] = Integer.toString(index);
        mData[1] = dept_name;
        mData[2] = year_no;
        mData[3] = g_name;
        mData[4] = stu_no;
        mData[5] = anno_info;
        mData[6] = date;
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
