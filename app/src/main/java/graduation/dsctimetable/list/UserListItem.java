package graduation.dsctimetable.list;

/**
 * Created by leejs on 2016-10-01.
 */
public class UserListItem {

    private String[] mData;

    public UserListItem(String[] data) {
        mData = data;
    }

    public UserListItem(String stu_no, String stu_name, String ident_back, String dept_name, String year_no, String g_name) {
        mData = new String[6];
        mData[0] = stu_no;
        mData[1] = stu_name;
        mData[2] = ident_back;
        mData[3] = dept_name;
        mData[4] = year_no;
        mData[5] = g_name;
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
